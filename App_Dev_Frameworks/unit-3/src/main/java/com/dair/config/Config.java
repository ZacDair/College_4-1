package com.dair.config;

import com.dair.classes.Franchise;
import com.dair.classes.Hero;
import com.dair.classes.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    // Create a publisher bean
    @Bean
    public Publisher marvel(){
        return new Publisher("Marvel Comics");
    }

    // Create franchise beans injecting marvel
    @Bean
    public Franchise avengers(){
        return new Franchise("Avengers");
    }
    @Bean
    public Franchise spiderverse(){
        return new Franchise("Spiderverse");
    }

    // Create Hero beans for Avengers
    @Bean
    public Hero ironman(){
        return new Hero("Ironman", avengers());
    }
    @Bean
    public Hero captainAmerica(){
        return new Hero("Captain America", avengers());
    }
    @Bean
    public Hero blackWidow(){
        return new Hero("Black Widow", avengers());
    }

    // Create Hero beans for Spiderverse
    @Bean
    public Hero spiderPig(){
        return new Hero("Spider Pig", spiderverse());
    }
    @Bean
    public Hero spiderGwen(){
        return new Hero("Spider Gwen", spiderverse());
    }
    @Bean
    public Hero spiderman(){
        return new Hero("Spiderman", spiderverse());
    }

}
