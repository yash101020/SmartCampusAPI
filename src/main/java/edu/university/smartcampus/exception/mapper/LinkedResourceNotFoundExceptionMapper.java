package edu.university.smartcampus.exception.mapper;

import edu.university.smartcampus.exception.LinkedResourceNotFoundException;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LinkedResourceNotFoundExceptionMapper extends AbstractExceptionMapper
        implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public jakarta.ws.rs.core.Response toResponse(LinkedResourceNotFoundException exception) {
        return buildResponse(422, exception.getMessage());
    }
}

