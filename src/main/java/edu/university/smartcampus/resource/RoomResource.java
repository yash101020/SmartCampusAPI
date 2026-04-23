package edu.university.smartcampus.resource;

import edu.university.smartcampus.dto.request.CreateRoomRequest;
import edu.university.smartcampus.model.Room;
import edu.university.smartcampus.service.RoomService;
import java.net.URI;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Context;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    private final RoomService roomService = RoomService.getInstance();

    @GET
    public Response getRooms() {
        List<Room> rooms = roomService.findAll();
        return Response.ok(rooms).build();
    }

    @POST
    public Response createRoom(CreateRoomRequest request, @Context UriInfo uriInfo) {
        Room room = roomService.create(request);
        URI location = uriInfo.getAbsolutePathBuilder().path(room.getId()).build();
        return Response.created(location).entity(room).build();
    }

    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {
        Room room = roomService.findById(id);
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {
        roomService.delete(id);
        return Response.noContent().build();
    }
}

