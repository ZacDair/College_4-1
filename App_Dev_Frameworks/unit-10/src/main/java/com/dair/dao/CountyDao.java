package com.dair.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.dair.entities.County;

public interface CountyDao extends JpaRepository<County, Integer>{
	County findCountyByCountyName(String countyName);
	County findByCountyName(String countyName);			// same as above
	County findByCountyId(int countyId);			
	int findCountyIdByCountyName(String countyName);
	boolean existsByCountyName(String countyName);
	
	List<County> findAllByOrderByCountyNameAsc();
	List<County> findAllByOrderByCountyNameDesc();
	List<County> findByCountyNameContainsAllIgnoreCase(String pattern);
	
	// Serves as an example of JPQL because could be written using names: findCountyNameByCountyId()
	// Find name of a particular county by its id
	@Query("SELECT c.countyName FROM County c where c.countyId = :id") 
	String findNameOfCountyById(@Param("id") int id);
	
	// Serves as an example of JPQL
	@Query(value = "SELECT * FROM county WHERE county.countyName LIKE :letter%", nativeQuery = true)
	List<County> findAllStartingWith(@Param("letter") char letter);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE County c SET c.countyName = :newName WHERE c.countyId = :countyId")
	void changeCountyName(@Param("newName") String newName, @Param("countyId") int countyId);
	
	// JOIN FETCH is a JPQL operator
	// Find all the county and its towns given a countyId 
	@Query("SELECT c FROM County c LEFT JOIN FETCH c.countyTowns t WHERE c.countyId = :countyId")
	County findCountyAndTownsByCountyId(int countyId);
	
	// Find all distinct counties and their towns. 
	@Query("SELECT DISTINCT c FROM County c JOIN FETCH c.countyTowns ")
	List<County> findAllCountiesAndTowns();
}
