package com.dair.dao;

import com.dair.classes.Hero;
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
public class HeroDaoImplementationMySQL implements HeroDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Returns an integer value based on how many heroes are in the DB
    public int getHeroCount(){
        return jdbcTemplate.queryForObject("Select count(*) from Hero", Integer.class);
    }

    //Returns a boolean if the hero with that name exists
    public boolean heroExists(String heroName){
        return 1 == jdbcTemplate.queryForObject("SELECT COUNT(1) FROM Hero WHERE Hero.heroName = ?", new Object[]{heroName}, Integer.class);
    }

    // Returns and maps a hero from the DB by ID
    public Hero findHeroByHeroID(int heroID){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Hero WHERE Hero.heroID = ?", new HeroRowMapper(), heroID);
        } catch (Exception e) {
            return null;
        }
    }

    // Returns and maps all heroes from the DB
    public List<Hero> findAllHeroes(){
        try {
            return jdbcTemplate.query("SELECT * FROM Hero", new HeroRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    // Delete hero by ID
    public int deleteHeroByHeroID(int heroID){
        String query = "DELETE FROM Hero WHERE Hero.heroID = ?";
        return jdbcTemplate.update(query, heroID);
    }

    // Change the name of a hero
    public int changeHeroName(String oldName, String newName){
        String query = "UPDATE Hero SET Hero.heroName = ? WHERE Hero.heroName = ?";
        int numberChanged = jdbcTemplate.update(query, newName, oldName);
        if (numberChanged == 0){
            System.out.println("ERROR: No Hero changed, check the database for a hero with heroName: "+oldName);
        }
        return numberChanged;
    }

    // Add a new hero to the database
    public int addHero(String heroName){
        String query = "INSERT INTO Hero(heroName) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(query, new String[]{"heroID"});
                        ps.setString(1, heroName);
                        return ps;
                    }
                }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}