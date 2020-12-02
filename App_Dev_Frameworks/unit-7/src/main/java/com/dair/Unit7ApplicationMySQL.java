package com.dair;

import com.dair.entities.County;
import com.dair.dao.CountyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Profile("mysql")
@Component
public class Unit7ApplicationMySQL implements CommandLineRunner {

    @Autowired
    CountyDao countyDao;

    @Override
    public void run(String... args) throws Exception {
        // Correct usage of get on 'Optional' returns
        System.out.println("\nFinding county by ID: 1");
        Optional<County> optionalCounty = countyDao.findById(1);
        if (optionalCounty.isPresent()){
            County county = optionalCounty.get();
            System.out.println(county);
        }
        else{
            System.out.println("There is no county with that ID");
        }

        // Usage of custom queries
        System.out.println("\nFind county by name: Cork");
        System.out.println(countyDao.findByCountyName("Cork"));

        System.out.println("\nFind counties by alphabetical order");
        List<County> counties = countyDao.findAllByOrderByCountyName();
        for(County c: counties){
            System.out.println(c);
        }

    }
}
