package com.dair.dao;

import com.dair.classes.Household;
import com.dair.rowmappers.HouseholdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class HouseholdDaoImplementationMySQL implements HouseholdDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Household findHouseholdByID(int householdID) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Household WHERE Household.houseID = ?", new HouseholdRowMapper(), householdID);
        } catch (Exception e) {
            return null;
        }
    }

    public Household findHouseholdByEircode(String eircode) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Household WHERE Household.HouseEircode = ?", new HouseholdRowMapper(), eircode);
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Integer> findAllOccupantsByHouseID(int houseID) {
        String query = "SELECT personID FROM OccupantRecords WHERE OccupantRecords.HouseID = "+houseID;
        try{
            return (ArrayList<Integer>) jdbcTemplate.queryForList(query, Integer.class);
        }
        catch (Exception e){
            return null;
        }
    }

    public Household addHousehold(String eircode, String address) {
        String query = "INSERT INTO Household(houseEircode, houseAddress) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(query, new String[]{"houseID"});
                        ps.setString(1, eircode);
                        ps.setString(2, address);
                        return ps;
                    }
                }, keyHolder);
        return findHouseholdByID(keyHolder.getKey().intValue());
    }

    public int addPersonToHouseholdByID(int houseID, int personID) {
        String query = "INSERT INTO OccupantRecords(houseID, personID) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(query, new String[]{"OccupantID"});
                        ps.setInt(1, houseID);
                        ps.setInt(2, personID);
                        return ps;
                    }
                }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public List<Household> findAllHouseholds() {
        try{
            return jdbcTemplate.query("SELECT * FROM Household", new HouseholdRowMapper());
        }
        catch (Exception e){
            return null;
        }
    }

    public int updateOccupancyByID(int newHouseID, int personID) {
        String query = "UPDATE occupantRecords SET occupantRecords.houseID = ? WHERE occupantRecords.personID = ?";
        int numberChanged = jdbcTemplate.update(query, newHouseID, personID);
        if (numberChanged == 0){
            System.out.println("ERROR: No record changed, check the occupantRecords table for database for personID: "+personID);
        }
        return numberChanged;
    }

    public int deleteRecordByID(int personID) {
        String query = "DELETE FROM occupantRecords WHERE occupantRecords.personID = ?";
        return jdbcTemplate.update(query,personID);
    }

    public int deleteHouseByID(int houseID) {
        String query = "DELETE FROM Household WHERE Household.houseID = ?";
        return jdbcTemplate.update(query, houseID);
    }
}
