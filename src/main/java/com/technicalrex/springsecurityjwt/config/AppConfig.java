package com.technicalrex.springsecurityjwt.config;

import org.springframework.core.env.Environment;

/**
 * Make sure to define "google.client.id" and "google.client.secret" in either
 * environment.properties or my.properties.
 */
public class AppConfig {
    private final String googleClientId;
    private final String googleClientSecret;

    public AppConfig(Environment env) {
        this.googleClientId = env.getProperty("google.client.id");
        this.googleClientSecret = env.getProperty("google.client.secret");

    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }
}
