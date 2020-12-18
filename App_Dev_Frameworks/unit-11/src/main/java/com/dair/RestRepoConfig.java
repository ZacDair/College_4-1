package com.dair;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import com.dair.entities.County;
import com.dair.entities.Town;

@Configuration
public class RestRepoConfig implements RepositoryRestConfigurer {
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfiguration){
        restConfiguration.exposeIdsFor(County.class);
        restConfiguration.exposeIdsFor(Town.class);
    }
}
