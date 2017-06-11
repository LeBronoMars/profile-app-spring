package com.sanmateo.profile.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    final private String originDelimiter = ",";

    @Value("${cors.allowedOrigins}")
    private String allowedOrigins;

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        if (allowedOrigins.contains(originDelimiter)) {
            String[] multipleOrigins = allowedOrigins.split(originDelimiter);
            for (String origin : multipleOrigins) {
                config.addAllowedOrigin(origin);
            }
        } else {
            config.addAllowedOrigin(allowedOrigins);
        }
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
