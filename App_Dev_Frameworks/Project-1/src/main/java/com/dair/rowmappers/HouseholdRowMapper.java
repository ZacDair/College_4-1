package com.dair.rowmappers;

import com.dair.classes.Household;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseholdRowMapper implements RowMapper<Household> {
    public Household mapRow(ResultSet rs, int rowNumber) throws SQLException{
        return new Household(rs.getInt("houseID"), rs.getString("houseEircode"), rs.getString("houseAddress"));
    }
}