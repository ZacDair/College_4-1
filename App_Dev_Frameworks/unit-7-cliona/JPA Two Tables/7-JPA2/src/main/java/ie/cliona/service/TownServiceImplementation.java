package ie.cliona.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.cliona.dao.TownDao;
import ie.cliona.entities.County;
import ie.cliona.entities.Town;

@Service
public class TownServiceImplementation implements TownService{

	@Autowired
	TownDao townDao;
	
	@Override
	public Town save(String townName, County townCounty) {
		if (townDao.existsByTownNameAndTownCounty(townName, townCounty))
		{
			return null;
		}
		Town newTown = new Town(townName, townCounty);
		return townDao.save(newTown);	
	}

	@Override
	public List<Town> getAllTowns() {
		return townDao.findAll();
	}

	@Override
	public List<Town> getAllTownsInACounty(int countyId) {
		return townDao.findAllByTownCounty_CountyId(countyId);
	}
}
