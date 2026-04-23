package edu.university.smartcampus.store;

import edu.university.smartcampus.model.Room;
import edu.university.smartcampus.model.Sensor;
import edu.university.smartcampus.model.SensorReading;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryDataStore {

    private static final InMemoryDataStore INSTANCE = new InMemoryDataStore();

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    private final Map<String, List<SensorReading>> sensorReadings = new ConcurrentHashMap<>();
    private final Object mutationLock = new Object();

    private InMemoryDataStore() {
    }

    public static InMemoryDataStore getInstance() {
        return INSTANCE;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public Map<String, Sensor> getSensors() {
        return sensors;
    }

    public Map<String, List<SensorReading>> getSensorReadings() {
        return sensorReadings;
    }

    public Object getMutationLock() {
        return mutationLock;
    }
}

