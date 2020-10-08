package com.dair.dao;

import com.dair.classes.Hero;
import com.dair.rowmappers.HeroRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HeroDaoImplementationMySQL implements HeroDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Returns an integer value based on how many heroes are in the DB
    public int getHeroCount(){
        return jdbcTemplate.queryForObject("Select count(*) from Hero", Integer.class);
    }

    // Returns and maps a hero from the DB by ID
    public Hero findHeroByHeroID(int heroID){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Hero WHERE Hero.heroID = ?", new HeroRowMapper(), heroID);
        } catch (Exception e) {
            return null;
        }
    }
}