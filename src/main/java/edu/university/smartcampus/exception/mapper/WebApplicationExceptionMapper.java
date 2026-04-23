package edu.university.smartcampus.exception.mapper;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper extends AbstractExceptionMapper
        implements ExceptionMapper<WebApplicationException> {

    @Override
    public jakarta.ws.rs.core.Response toResponse(WebApplicationException exception) {
        int statusCode = exception.getResponse() == null ? 500 : exception.getResponse().getStatus();
        String message = exception.getResponse() == null
                ? "Request could not be processed."
                : exception.getResponse().getStatusInfo().getReasonPhrase();

        return buildResponse(statusCode, message);
    }
}

