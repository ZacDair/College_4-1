package com.warner_dair.configurations;

import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RestRepoConfig implements RepositoryRestConfigurer {
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfiguration){
        restConfiguration.exposeIdsFor(Director.class);
        restConfiguration.exposeIdsFor(Film.class);
    }
}
