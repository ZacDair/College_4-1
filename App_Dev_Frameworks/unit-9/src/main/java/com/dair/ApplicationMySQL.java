package com.dair;

import java.util.List;

import com.dair.entities.Town;
import com.dair.service.CountyService;
import com.dair.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.dair.entities.County;

// You can only test this code if you create a MySQL database with the appropriate tables and fields. 
@Profile("mysql")
@Component
public class ApplicationMySQL implements CommandLineRunner {

	@Autowired
    CountyService countyService;
	
	@Autowired
    TownService townService;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("\n===========================================================\n");
		System.out.println("There are " + countyService.numberOfCounties() + " counties.");
		
		// Using the method which Spring creates based upon the signature in the interface. 
		System.out.println("\n===========================================================\nThe counties are:");
		List<County> counties = countyService.getAllCountiesAlphabetically();
		for(County county: counties)
			System.out.println(county.getCountyName());
		
		System.out.println("\n===========================================================");
		List<Town> towns = townService.getAllTowns();
		for(Town t: towns)
			System.out.println(t.getTownName() + " in " + t.getTownCounty().getCountyName());
	
		List<County> allCountiesAndTowns = countyService.getAllCountiesAndTheirTowns();
		for(County c: allCountiesAndTowns)
		{
			System.out.println(c.getCountyName());
			towns = c.getCountyTowns();
			for(Town t: towns)
				System.out.println("\t" + t.getTownName());	
		}
	}
}
