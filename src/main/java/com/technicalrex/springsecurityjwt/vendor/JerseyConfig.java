package com.technicalrex.springsecurityjwt.vendor;

import com.technicalrex.springsecurityjwt.api.jars.JarsResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        // Enable Spring and Jackson support
        register(RequestContextFilter.class);
        register(JacksonObjectMapperConfig.class);

        // Application resources
        register(JarsResource.class);
    }
}
