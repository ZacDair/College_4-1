package com.dair.dao;

import com.dair.classes.Household;
import com.dair.classes.Person;
import com.dair.rowmappers.HouseholdRowMapper;
import com.dair.rowmappers.PersonRowMapper;
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
public class PersonDaoImplementationMySQL implements PersonDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Person findPersonByPersonID(int personID) {
        String query = "SELECT * FROM Person WHERE Person.personID = ?";
        try {
            return jdbcTemplate.queryForObject(query, new PersonRowMapper(), personID);
        } catch (Exception e) {
            return null;
        }
    }

    public Person addPerson(String name, int age, String occupation) {
        String query = "INSERT INTO Person(personName, personAge, personOccupation) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(query, new String[]{"personID"});
                        ps.setString(1, name);
                        ps.setInt(2, age);
                        ps.setString(3, occupation);
                        return ps;
                    }
                }, keyHolder);
        return findPersonByPersonID(keyHolder.getKey().intValue());
    }

    public List<Person> findAllPersons() {
        try{
            return jdbcTemplate.query("SELECT * FROM Person", new PersonRowMapper());
        }
        catch (Exception e){
            return null;
        }
    }

    public int deletePersonByID(int personID) {
        String query = "DELETE FROM Person WHERE Person.personID = ?";
        return jdbcTemplate.update(query, personID);
    }

    public Integer getAverageAge() {
        String query = "SELECT AVG(personAge) FROM Person";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public Integer countByOccupation(String occupation) {
        String query = "SELECT COUNT(personID) FROM Person WHERE Person.personOccupation = ?";
        return jdbcTemplate.queryForObject(query, int.class, occupation);
    }

    public Integer countByAge(int age) {
        String query = "SELECT COUNT(personID) FROM Person WHERE Person.personAge >= ?";
        return jdbcTemplate.queryForObject(query, int.class, age);
    }
}
