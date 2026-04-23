package edu.university.smartcampus.exception.mapper;

import edu.university.smartcampus.exception.RoomNotEmptyException;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RoomNotEmptyExceptionMapper extends AbstractExceptionMapper
        implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public jakarta.ws.rs.core.Response toResponse(RoomNotEmptyException exception) {
        return buildResponse(409, exception.getMessage());
    }
}

