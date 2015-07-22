package com.technicalrex.springsecurityjwt.vendor;

import com.technicalrex.springsecurityjwt.auth.XsrfUtils;
import com.technicalrex.springsecurityjwt.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

@Configuration
@Order(1)
@PropertySources({
        @PropertySource("classpath:/com/technicalrex/springsecurityjwt/environment.properties"),
        @PropertySource("classpath:/com/technicalrex/springsecurityjwt/my.properties")
})
public class SpringConfig {
    @Autowired
    Environment env;

    @Bean
    public AppConfig appConfig() {
        return new AppConfig(env);
    }

    @Bean
    public XsrfUtils xsrfUtils() {
        return new XsrfUtils();
    }
}
