package edu.university.smartcampus.exception.mapper;

import edu.university.smartcampus.exception.DuplicateResourceException;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DuplicateResourceExceptionMapper extends AbstractExceptionMapper
        implements ExceptionMapper<DuplicateResourceException> {

    @Override
    public jakarta.ws.rs.core.Response toResponse(DuplicateResourceException exception) {
        return buildResponse(409, exception.getMessage());
    }
}

