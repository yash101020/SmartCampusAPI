package edu.university.smartcampus.exception.mapper;

import edu.university.smartcampus.dto.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public abstract class AbstractExceptionMapper {

    protected Response buildResponse(int statusCode, String message) {
        return Response.status(statusCode)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorResponse(message, statusCode))
                .build();
    }
}

