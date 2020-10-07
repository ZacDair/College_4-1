package com.dair.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HeroDaoImplementationMySQL implements HeroDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int getHeroCount(){
        return jdbcTemplate.queryForObject("Select count(*) from Hero", Integer.class);
    }
}