package edu.university.smartcampus.exception.mapper;

import edu.university.smartcampus.exception.InvalidPayloadException;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidPayloadExceptionMapper extends AbstractExceptionMapper
        implements ExceptionMapper<InvalidPayloadException> {

    @Override
    public jakarta.ws.rs.core.Response toResponse(InvalidPayloadException exception) {
        return buildResponse(400, exception.getMessage());
    }
}

