package edu.university.smartcampus.dto.request;

public class CreateSensorReadingRequest {

    private String id;
    private Long timestamp;
    private Double value;

    public CreateSensorReadingRequest() {
    }

    public CreateSensorReadingRequest(String id, Long timestamp, Double value) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

