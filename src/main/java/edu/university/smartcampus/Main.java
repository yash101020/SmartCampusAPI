package edu.university.smartcampus;

import edu.university.smartcampus.config.ApplicationConfig;
import java.net.URI;
import java.util.Set;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String DEFAULT_BASE_URI = "http://0.0.0.0:8080/api/v1/";

    private Main() {
    }

    public static HttpServer startServer() {
        ResourceConfig resourceConfig = new ResourceConfig();
        Set<Class<?>> applicationClasses = new ApplicationConfig().getClasses();
        applicationClasses.forEach(resourceConfig::register);

        String baseUri = System.getProperty("server.baseUri", DEFAULT_BASE_URI);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), resourceConfig);
    }

    public static void main(String[] args) throws InterruptedException {
        HttpServer server = startServer();
        String baseUri = System.getProperty("server.baseUri", DEFAULT_BASE_URI);

        LOGGER.info(() -> "Smart Campus Sensor & Room Management API started at " + baseUri);
        LOGGER.info("Press Ctrl+C to stop the server.");

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        Thread.currentThread().join();
    }
}

