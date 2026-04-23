package edu.university.smartcampus.dto.request;

import edu.university.smartcampus.model.SensorStatus;

public class CreateSensorRequest {

    private String id;
    private String type;
    private SensorStatus status;
    private Double currentValue;
    private String roomId;

    public CreateSensorRequest() {
    }

    public CreateSensorRequest(String id, String type, SensorStatus status, Double currentValue, String roomId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SensorStatus getStatus() {
        return status;
    }

    public void setStatus(SensorStatus status) {
        this.status = status;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}

