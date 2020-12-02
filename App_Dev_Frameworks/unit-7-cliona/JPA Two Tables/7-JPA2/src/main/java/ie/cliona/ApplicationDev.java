package ie.cliona;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ie.cliona.entities.County;
import ie.cliona.entities.Town;
import ie.cliona.service.CountyService;
import ie.cliona.service.TownService;

@Profile("dev")
@Component
public class ApplicationDev implements CommandLineRunner {

	@Autowired
	CountyService countyService;
	
	@Autowired
	TownService townService;
	
	@Override
	public void run(String... args) throws Exception {
		// Save these counties to the database
		County cork = countyService.save("Cork");
		County kerry =countyService.save("Kerry");
		County dublin =countyService.save("Dublin");
		County waterford =countyService.save("Waterford");
		
		// Save these towns to the database
		townService.save("Fermoy", cork );
		townService.save("Mallow",cork );
		townService.save("Tralee",kerry );
		townService.save("Tramore", waterford);
		townService.save("Ballsbridge", dublin);
		townService.save("Ballsbridge", dublin);
		
		System.out.println("\n===========================================================\n");
		System.out.println("There are " + countyService.numberOfCounties() + " counties.");
		
		// Using the method which Spring creates based upon the signature in the interface. 
		System.out.println("\n===========================================================\nThe counties are:");
		List<County> counties = countyService.getAllCountiesAlphabetically();
		for(County county: counties)
			System.out.println(county.getCountyName());
		
		System.out.println("\n===========================================================");
		County county = countyService.getCountyById(3);	
		System.out.println("The county with ID = 1 is " + county.getCountyName());
		
		System.out.println("The name of the county with ID = 2 is " + countyService.getCountyNameWithId(2));
	
		System.out.println("\n===========================================================");
		List<Town> towns = townService.getAllTowns();
		for(Town t: towns)
			System.out.println(t.getTownName() + " in " + t.getTownCounty().getCountyName());

		int countyId = 3;
		county = countyService.getCountyById(countyId);
		System.out.println("\n===========================================");
		System.out.println("Towns in " + county.getCountyName());
		System.out.println("===========================================");
		
		
		List<Town> townsInCork = townService.getAllTownsInACounty(countyId);
		for(Town t: townsInCork)
			System.out.println(t.getTownName());
		
		
		county = countyService.getCountyAndTownsByCountyId(countyId);
		
		System.out.println("\n===========================================");
		System.out.println("Towns in " + county.getCountyName());
		System.out.println("===========================================");
		
		towns = county.getCountyTowns();
		
		for(Town t: towns)
			System.out.println(t.getTownName());	
		
		System.out.println("\n===========================================");
		
		List<County> allCountiesAndTowns = countyService.getAllCountiesAndTheirTowns();
		for(County c: allCountiesAndTowns)
		{
			System.out.println(c.getCountyName());
			towns = c.getCountyTowns();
			for(Town t: towns)
				System.out.println("\t" + t.getTownName());	
		}
		
		System.out.println("\n\nDelete Dublin (id = 3) and all its towns are also removed, leaving only");
		
		countyService.deleteCounty(3);
		
		towns = townService.getAllTowns();
		for(Town t: towns)
				System.out.println(">>> " + t.getTownName() + " in " + t.getTownCounty().getCountyName());	
		
	}
}
