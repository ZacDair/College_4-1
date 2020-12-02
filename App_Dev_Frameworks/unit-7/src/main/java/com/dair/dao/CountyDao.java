package com.dair.dao;

import com.dair.entities.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CountyDao extends JpaRepository<County, Integer> {
    // Custom Standard Queries
    County findByCountyName(String countyName);
    List<County> findAllByOrderByCountyName();

    // Custom Update Queries
    @Modifying
    @Transactional
    @Query("UPDATE County c SET c.countyName = :newName WHERE c.countyID = :countyId")
    void changeCountyName(@Param("newName") String newName, @Param("countyId") int countyId);
}
