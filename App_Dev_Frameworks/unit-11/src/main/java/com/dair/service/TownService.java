package com.dair.service;

import java.util.List;

import com.dair.entities.County;
import com.dair.entities.Town;

public interface TownService {
	List<Town> getAllTowns();
	List<Town> getAllTownsInACounty(int countyId);
	Town save(String townName, County townCounty);
	Town findTown(int townId);
	
}
