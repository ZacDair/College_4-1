package com.dair.rowmappers;


import com.dair.classes.Franchise;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FranchiseRowMapper implements RowMapper<Franchise> {
    public Franchise mapRow(ResultSet rs, int rowNumber) throws SQLException{
        return new Franchise(rs.getString("franchiseName"), rs.getInt("franchiseID"));
    }
}
