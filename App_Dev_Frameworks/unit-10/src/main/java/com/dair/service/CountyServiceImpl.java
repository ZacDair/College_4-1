package com.dair.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dair.dao.CountyDao;
import com.dair.entities.County;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CountyServiceImpl implements CountyService {

	@Autowired
	CountyDao countyDao;
	
	@Override
	public County save(String countyName) {
		if (countyDao.existsByCountyName(countyName))
		{
			log.error("Attempt to add a county which already exists in the database");
			return null;
		}
		return countyDao.save(new County(countyName));
	}

	@Override
    public County findCounty(int countyId) {
		Optional<County> optional = countyDao.findById(countyId);

		if (optional.isPresent())
			return optional.get(); // note the use of .get()

		return null;
	}
	
	@Override
	public boolean changeCountyName(String newName, int countyId) {
		Optional<County> optional = countyDao.findById(countyId);
		if (countyDao.findByCountyName(newName) == null && optional.isPresent())
		{
			countyDao.changeCountyName(newName, countyId);
			return true;
		}
		return false;
	}

	
	@Override
	public List<County> getAllCounties() {
		return countyDao.findAll();
	}

	@Override
	public long numberOfCounties() {
		return countyDao.count();
	}

	

	@Override
	public List<County> getAllCountiesAlphabetically() {
		return countyDao.findAllByOrderByCountyNameAsc();
	}

	
	@Override
	public List<County> getAllCountiesWithLetters(String letters) {
		return countyDao.findByCountyNameContainsAllIgnoreCase(letters);
	}

	@Override
	public String getCountyNameWithId(int id) {
		return countyDao.findNameOfCountyById(id);
	}

	@Override
	public County getCountyById(int id) {
		Optional<County> optional = countyDao.findById(id);
		if (optional.isPresent())
			return optional.get();
		return null;
	}

	@Override
	public boolean exists(String countyName) {
		return countyDao.existsByCountyName(countyName);
	}

	@Override
	public County getCountyAndTownsByCountyId(int countyId) {
		return countyDao.findCountyAndTownsByCountyId(countyId);
	}

	@Override
	public List<County> getAllCountiesAndTheirTowns() {
		return countyDao.findAllCountiesAndTowns();
	}

	@Override
	public void deleteCounty(int countyId) {
		countyDao.deleteById(countyId);
    }

	
}
