package edu.university.smartcampus.exception.mapper;

import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class ThrowableExceptionMapper extends AbstractExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(ThrowableExceptionMapper.class.getName());

    @Override
    public jakarta.ws.rs.core.Response toResponse(Throwable exception) {
        LOGGER.log(Level.SEVERE, "Unhandled exception while processing request.", exception);
        return buildResponse(500, "An unexpected internal error occurred.");
    }
}

