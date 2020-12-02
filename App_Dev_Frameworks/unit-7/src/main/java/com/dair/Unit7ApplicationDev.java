package com.dair;


import com.dair.entities.County;
import com.dair.dao.CountyDao;
import com.dair.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Profile("dev")
@Component
public class Unit7ApplicationDev implements CommandLineRunner {

    @Autowired
    CountyDao countyDao;

    @Autowired
    TownService townService;

    @Override
    public void run(String... args) throws Exception {
        County dublin = countyDao.save(new County("Dublin"));
        County cork = countyDao.save(new County("Cork"));
        County kerry = countyDao.save(new County("Kerry"));

        townService.save("Fermoy", cork);
        townService.save("Mallow", cork);
        townService.save("Cobh", cork);
        townService.save("Dublin City", dublin);
        townService.save("Tralee", kerry);


        System.out.println(cork);

        // Returns 'Optional' return of object
        System.out.println(countyDao.findById(1));

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

        // Usage of custom update query
        System.out.println("\nUpdating a county name by ID: 1");
        countyDao.changeCountyName("Galway", 1);

        // Prints to show county name changes
        counties = countyDao.findAllByOrderByCountyName();
        for(County c: counties){
            System.out.println(c);
        }
    }
}
