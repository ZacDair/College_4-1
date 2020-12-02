package ie.cliona.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.cliona.entities.County;
import ie.cliona.entities.Town;

public interface TownDao extends JpaRepository<Town, Integer>{
	// Get all the towns from the town side of the relationship
	List<Town> findAllByTownCounty_CountyId(int countyId);
	
	boolean existsByTownNameAndTownCounty(String townName, County county);
	
}
