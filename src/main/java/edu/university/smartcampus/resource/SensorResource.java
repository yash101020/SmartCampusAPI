package edu.university.smartcampus.resource;

import edu.university.smartcampus.dto.request.CreateSensorRequest;
import edu.university.smartcampus.model.Sensor;
import edu.university.smartcampus.service.SensorService;
import java.net.URI;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    private final SensorService sensorService = SensorService.getInstance();

    @POST
    public Response createSensor(CreateSensorRequest request, @Context UriInfo uriInfo) {
        Sensor sensor = sensorService.create(request);
        URI location = uriInfo.getAbsolutePathBuilder().path(sensor.getId()).build();
        return Response.created(location).entity(sensor).build();
    }

    @GET
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> sensors = sensorService.findAll(type);
        return Response.ok(sensors).build();
    }

    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") String id) {
        Sensor sensor = sensorService.findById(id);
        return Response.ok(sensor).build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingSubResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId, sensorService);
    }
}

