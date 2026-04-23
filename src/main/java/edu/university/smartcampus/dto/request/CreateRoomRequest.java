package edu.university.smartcampus.dto.request;

public class CreateRoomRequest {

    private String id;
    private String name;
    private Integer capacity;

    public CreateRoomRequest() {
    }

    public CreateRoomRequest(String id, String name, Integer capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}

