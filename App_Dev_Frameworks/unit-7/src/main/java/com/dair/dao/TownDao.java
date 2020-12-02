package com.dair.dao;

import com.dair.entities.County;
import com.dair.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownDao extends JpaRepository<Town, Integer> {

    boolean existsByTownNameAndTownCounty(String townName, County townCounty);

    List<Town> findAllByTownCounty_CountyID(int countyID);
}
