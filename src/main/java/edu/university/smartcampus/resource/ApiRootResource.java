package edu.university.smartcampus.resource;

import edu.university.smartcampus.dto.ApiInfoResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class ApiRootResource {

    @GET
    public Response getApiInfo() {
        Map<String, String> resources = new LinkedHashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");

        ApiInfoResponse response = new ApiInfoResponse("v1", "admin@university.edu", resources);
        return Response.ok(response).build();
    }
}

