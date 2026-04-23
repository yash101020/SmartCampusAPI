package edu.university.smartcampus.service;

import edu.university.smartcampus.dto.request.CreateRoomRequest;
import edu.university.smartcampus.exception.DuplicateResourceException;
import edu.university.smartcampus.exception.InvalidPayloadException;
import edu.university.smartcampus.exception.ResourceNotFoundException;
import edu.university.smartcampus.exception.RoomNotEmptyException;
import edu.university.smartcampus.model.Room;
import edu.university.smartcampus.store.InMemoryDataStore;
import edu.university.smartcampus.util.IdGenerator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class RoomService {

    private static final RoomService INSTANCE = new RoomService();

    private final InMemoryDataStore dataStore = InMemoryDataStore.getInstance();

    private RoomService() {
    }

    public static RoomService getInstance() {
        return INSTANCE;
    }

    public List<Room> findAll() {
        return dataStore.getRooms()
                .values()
                .stream()
                .map(Room::new)
                .sorted(Comparator.comparing(Room::getId))
                .toList();
    }

    public Room findById(String id) {
        Room room = dataStore.getRooms().get(id);
        if (room == null) {
            throw new ResourceNotFoundException("Room with id " + id + " was not found.");
        }
        return new Room(room);
    }

    public boolean exists(String id) {
        return dataStore.getRooms().containsKey(id);
    }

    public Room create(CreateRoomRequest request) {
        validateCreateRequest(request);

        String roomId = hasText(request.getId()) ? request.getId().trim() : IdGenerator.newId();
        Room room = new Room(roomId, request.getName().trim(), request.getCapacity(), List.of());

        Room existing = dataStore.getRooms().putIfAbsent(roomId, room);
        if (existing != null) {
            throw new DuplicateResourceException("Room with id " + roomId + " already exists.");
        }

        return new Room(room);
    }

    public void delete(String id) {
        synchronized (dataStore.getMutationLock()) {
            Room room = dataStore.getRooms().get(id);
            if (room == null) {
                throw new ResourceNotFoundException("Room with id " + id + " was not found.");
            }

            // Keep room-sensor relationships consistent across concurrent requests.
            if (!room.getSensorIds().isEmpty()) {
                throw new RoomNotEmptyException(
                        "Room with id " + id + " still has linked sensors and cannot be deleted.");
            }

            dataStore.getRooms().remove(id);
        }
    }

    public void attachSensor(String roomId, String sensorId) {
        synchronized (dataStore.getMutationLock()) {
            Room room = dataStore.getRooms().get(roomId);
            if (room == null) {
                throw new ResourceNotFoundException("Room with id " + roomId + " was not found.");
            }

            List<String> sensorIds = new ArrayList<>(room.getSensorIds());
            if (!sensorIds.contains(sensorId)) {
                sensorIds.add(sensorId);
                room.setSensorIds(sensorIds);
            }
        }
    }

    private void validateCreateRequest(CreateRoomRequest request) {
        if (request == null) {
            throw new InvalidPayloadException("Room payload is required.");
        }
        if (!hasText(request.getName())) {
            throw new InvalidPayloadException("Room name is required.");
        }
        if (request.getCapacity() == null) {
            throw new InvalidPayloadException("Room capacity is required.");
        }
        if (request.getCapacity() < 0) {
            throw new InvalidPayloadException("Room capacity must be zero or greater.");
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}

