package edu.university.smartcampus.resource;

import edu.university.smartcampus.dto.request.CreateSensorReadingRequest;
import edu.university.smartcampus.model.SensorReading;
import edu.university.smartcampus.service.SensorService;
import java.net.URI;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;
    private final SensorService sensorService;

    public SensorReadingResource(String sensorId, SensorService sensorService) {
        this.sensorId = sensorId;
        this.sensorService = sensorService;
    }

    @GET
    public Response getReadings() {
        List<SensorReading> readings = sensorService.findReadings(sensorId);
        return Response.ok(readings).build();
    }

    @GET
    @Path("/{readingId}")
    public Response getReading(@PathParam("readingId") String readingId) {
        SensorReading reading = sensorService.findReading(sensorId, readingId);
        return Response.ok(reading).build();
    }

    @POST
    public Response addReading(CreateSensorReadingRequest request, @Context UriInfo uriInfo) {
        SensorReading reading = sensorService.addReading(sensorId, request);
        URI location = uriInfo.getAbsolutePathBuilder().path(reading.getId()).build();
        return Response.created(location).entity(reading).build();
    }
}

