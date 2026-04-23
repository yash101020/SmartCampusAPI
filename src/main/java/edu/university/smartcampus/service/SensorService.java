package edu.university.smartcampus.service;

import edu.university.smartcampus.dto.request.CreateSensorReadingRequest;
import edu.university.smartcampus.dto.request.CreateSensorRequest;
import edu.university.smartcampus.exception.DuplicateResourceException;
import edu.university.smartcampus.exception.InvalidPayloadException;
import edu.university.smartcampus.exception.LinkedResourceNotFoundException;
import edu.university.smartcampus.exception.ResourceNotFoundException;
import edu.university.smartcampus.exception.SensorUnavailableException;
import edu.university.smartcampus.model.Sensor;
import edu.university.smartcampus.model.SensorReading;
import edu.university.smartcampus.model.SensorStatus;
import edu.university.smartcampus.store.InMemoryDataStore;
import edu.university.smartcampus.util.IdGenerator;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SensorService {

    private static final SensorService INSTANCE = new SensorService();

    private final InMemoryDataStore dataStore = InMemoryDataStore.getInstance();
    private final RoomService roomService = RoomService.getInstance();

    private SensorService() {
    }

    public static SensorService getInstance() {
        return INSTANCE;
    }

    public List<Sensor> findAll(String type) {
        return dataStore.getSensors()
                .values()
                .stream()
                .filter(sensor -> !hasText(type) || sensor.getType().equalsIgnoreCase(type.trim()))
                .map(Sensor::new)
                .sorted(Comparator.comparing(Sensor::getId))
                .toList();
    }

    public Sensor findById(String id) {
        Sensor sensor = dataStore.getSensors().get(id);
        if (sensor == null) {
            throw new ResourceNotFoundException("Sensor with id " + id + " was not found.");
        }
        return new Sensor(sensor);
    }

    public Sensor create(CreateSensorRequest request) {
        validateCreateRequest(request);

        synchronized (dataStore.getMutationLock()) {
            String roomId = request.getRoomId().trim();

            if (!roomService.exists(roomId)) {
                throw new LinkedResourceNotFoundException(
                        "Room with id " + roomId + " does not exist.");
            }

            String sensorId = hasText(request.getId()) ? request.getId().trim() : IdGenerator.newId();
            SensorStatus status = request.getStatus() == null ? SensorStatus.ACTIVE : request.getStatus();
            double currentValue = request.getCurrentValue() == null ? 0.0 : request.getCurrentValue();

            Sensor sensor = new Sensor(sensorId, request.getType().trim(), status, currentValue, roomId);
            Sensor existing = dataStore.getSensors().putIfAbsent(sensorId, sensor);
            if (existing != null) {
                throw new DuplicateResourceException("Sensor with id " + sensorId + " already exists.");
            }

            dataStore.getSensorReadings().putIfAbsent(sensorId, new CopyOnWriteArrayList<>());
            roomService.attachSensor(roomId, sensorId);

            return new Sensor(sensor);
        }
    }

    public List<SensorReading> findReadings(String sensorId) {
        ensureSensorExists(sensorId);
        return dataStore.getSensorReadings()
                .getOrDefault(sensorId, List.of())
                .stream()
                .map(SensorReading::new)
                .sorted(Comparator.comparingLong(SensorReading::getTimestamp)
                        .thenComparing(SensorReading::getId))
                .toList();
    }

    public SensorReading findReading(String sensorId, String readingId) {
        ensureSensorExists(sensorId);
        return dataStore.getSensorReadings()
                .getOrDefault(sensorId, List.of())
                .stream()
                .filter(reading -> reading.getId().equals(readingId))
                .findFirst()
                .map(SensorReading::new)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reading with id " + readingId + " was not found for sensor " + sensorId + "."));
    }

    public SensorReading addReading(String sensorId, CreateSensorReadingRequest request) {
        validateReadingRequest(request);

        synchronized (dataStore.getMutationLock()) {
            Sensor sensor = dataStore.getSensors().get(sensorId);
            if (sensor == null) {
                throw new ResourceNotFoundException("Sensor with id " + sensorId + " was not found.");
            }

            if (sensor.getStatus() == SensorStatus.MAINTENANCE) {
                throw new SensorUnavailableException(
                        "Sensor with id " + sensorId + " is unavailable because it is in maintenance.");
            }

            String readingId = hasText(request.getId()) ? request.getId().trim() : IdGenerator.newId();
            long timestamp = request.getTimestamp() == null ? System.currentTimeMillis() : request.getTimestamp();
            SensorReading reading = new SensorReading(readingId, timestamp, request.getValue());

            List<SensorReading> readings = dataStore.getSensorReadings()
                    .computeIfAbsent(sensorId, key -> new CopyOnWriteArrayList<>());

            boolean duplicateReading = readings.stream().anyMatch(existing -> existing.getId().equals(readingId));
            if (duplicateReading) {
                throw new DuplicateResourceException(
                        "Reading with id " + readingId + " already exists for sensor " + sensorId + ".");
            }

            readings.add(reading);
            sensor.setCurrentValue(request.getValue());

            return new SensorReading(reading);
        }
    }

    private void ensureSensorExists(String sensorId) {
        if (!dataStore.getSensors().containsKey(sensorId)) {
            throw new ResourceNotFoundException("Sensor with id " + sensorId + " was not found.");
        }
    }

    private void validateCreateRequest(CreateSensorRequest request) {
        if (request == null) {
            throw new InvalidPayloadException("Sensor payload is required.");
        }
        if (!hasText(request.getType())) {
            throw new InvalidPayloadException("Sensor type is required.");
        }
        if (!hasText(request.getRoomId())) {
            throw new InvalidPayloadException("Sensor roomId is required.");
        }
    }

    private void validateReadingRequest(CreateSensorReadingRequest request) {
        if (request == null) {
            throw new InvalidPayloadException("Sensor reading payload is required.");
        }
        if (request.getValue() == null) {
            throw new InvalidPayloadException("Sensor reading value is required.");
        }
        if (request.getTimestamp() != null && request.getTimestamp() < 0) {
            throw new InvalidPayloadException("Sensor reading timestamp must be zero or greater.");
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
