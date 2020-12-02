package ie.cliona.service;

import java.util.List;

import ie.cliona.entities.County;
import ie.cliona.entities.Town;

public interface TownService {
	List<Town> getAllTowns();
	List<Town> getAllTownsInACounty(int countyId);
	Town save(String townName, County townCounty);
	
}
