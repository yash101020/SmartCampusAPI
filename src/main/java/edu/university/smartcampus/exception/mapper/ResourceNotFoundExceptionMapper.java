package edu.university.smartcampus.exception.mapper;

import edu.university.smartcampus.exception.ResourceNotFoundException;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionMapper extends AbstractExceptionMapper
        implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public jakarta.ws.rs.core.Response toResponse(ResourceNotFoundException exception) {
        return buildResponse(404, exception.getMessage());
    }
}

