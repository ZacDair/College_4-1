package com.dair.rowmappers;


import com.dair.classes.Hero;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HeroRowMapper implements RowMapper<Hero> {
    public Hero mapRow(ResultSet rs, int rowNumber) throws SQLException{
        return new Hero(rs.getString("heroName"), rs.getInt("heroID"));
    }
}
