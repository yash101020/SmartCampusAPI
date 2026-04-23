package edu.university.smartcampus.exception.mapper;

import edu.university.smartcampus.exception.SensorUnavailableException;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SensorUnavailableExceptionMapper extends AbstractExceptionMapper
        implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public jakarta.ws.rs.core.Response toResponse(SensorUnavailableException exception) {
        return buildResponse(403, exception.getMessage());
    }
}

