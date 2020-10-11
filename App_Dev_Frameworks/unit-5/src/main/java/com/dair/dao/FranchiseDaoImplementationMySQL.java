package com.dair.dao;

import com.dair.classes.Franchise;
import com.dair.classes.Hero;
import com.dair.rowmappers.FranchiseRowMapper;
import com.dair.rowmappers.HeroRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FranchiseDaoImplementationMySQL implements FranchiseDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Returns an integer value based on how many franchises are in the DB
    public int getFranchiseCount(){
        return jdbcTemplate.queryForObject("Select count(*) from Franchise", Integer.class);
    }

    // Returns and maps a franchise from the DB by ID
    public Franchise findFranchiseByID(int franchiseID){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Franchise WHERE Franchise.franchiseID = ?", new FranchiseRowMapper(), franchiseID);
        } catch (Exception e) {
            return null;
        }
    }

    //Returns a boolean if the franchise with that name exists
    public boolean franchiseExists(String franchiseName){
        return 1 == jdbcTemplate.queryForObject("SELECT COUNT(1) FROM Franchise WHERE Franchise.franchiseName = ?", new Object[]{franchiseName}, Integer.class);
    }

    // Delete franchise by ID
    public int deleteFranchiseByFranchiseID(int franchiseID){
        String query = "DELETE FROM Franchise WHERE Franchise.franchiseID = ?";
        return jdbcTemplate.update(query, franchiseID);
    }
}