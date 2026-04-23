package edu.university.smartcampus.config;

import edu.university.smartcampus.exception.mapper.DuplicateResourceExceptionMapper;
import edu.university.smartcampus.exception.mapper.InvalidPayloadExceptionMapper;
import edu.university.smartcampus.exception.mapper.LinkedResourceNotFoundExceptionMapper;
import edu.university.smartcampus.exception.mapper.ResourceNotFoundExceptionMapper;
import edu.university.smartcampus.exception.mapper.RoomNotEmptyExceptionMapper;
import edu.university.smartcampus.exception.mapper.SensorUnavailableExceptionMapper;
import edu.university.smartcampus.exception.mapper.ThrowableExceptionMapper;
import edu.university.smartcampus.exception.mapper.WebApplicationExceptionMapper;
import edu.university.smartcampus.filter.RequestResponseLoggingFilter;
import edu.university.smartcampus.resource.ApiRootResource;
import edu.university.smartcampus.resource.RoomResource;
import edu.university.smartcampus.resource.SensorResource;
import java.util.LinkedHashSet;
import java.util.Set;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;

@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new LinkedHashSet<>();

        classes.add(JacksonFeature.class);

        classes.add(ApiRootResource.class);
        classes.add(RoomResource.class);
        classes.add(SensorResource.class);

        classes.add(RequestResponseLoggingFilter.class);

        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        classes.add(ResourceNotFoundExceptionMapper.class);
        classes.add(DuplicateResourceExceptionMapper.class);
        classes.add(InvalidPayloadExceptionMapper.class);
        classes.add(WebApplicationExceptionMapper.class);
        classes.add(ThrowableExceptionMapper.class);

        return classes;
    }
}

