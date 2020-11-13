package com.dair.rowmappers;

import com.dair.classes.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    public Person mapRow(ResultSet rs, int rowNumber) throws SQLException{
        return new Person(rs.getInt("personID"), rs.getString("personName"),rs.getInt("personAge"), rs.getString("personOccupation"));
    }
}