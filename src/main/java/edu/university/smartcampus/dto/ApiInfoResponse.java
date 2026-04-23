package edu.university.smartcampus.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApiInfoResponse {

    private String version;
    private String contact;
    private Map<String, String> resources;

    public ApiInfoResponse() {
        this.resources = new LinkedHashMap<>();
    }

    public ApiInfoResponse(String version, String contact, Map<String, String> resources) {
        this.version = version;
        this.contact = contact;
        setResources(resources);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Map<String, String> getResources() {
        return resources;
    }

    public void setResources(Map<String, String> resources) {
        this.resources = resources == null ? new LinkedHashMap<>() : new LinkedHashMap<>(resources);
    }
}

