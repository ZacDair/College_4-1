package com.dair.service;

import java.util.List;

import com.dair.entities.County;

public interface CountyService {
	County save(String countyName);
	List<County> getAllCounties();
	List<County> getAllCountiesAndTheirTowns();
	List<County> getAllCountiesAlphabetically();
	boolean deleteCounty(int countyId);
	long numberOfCounties();
	County findCounty(int countyId);
	boolean changeCountyName(String newName, int countyId);
	List<County> getAllCountiesWithLetters(String letters);
	String getCountyNameWithId(int id);
	County getCountyById(int id);
	boolean exists(String countyName);
	County getCountyAndTownsByCountyId(int countyId);
}
