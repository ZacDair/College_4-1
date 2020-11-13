package databaseTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class) //Add Spring to Junit
@ContextConfiguration("classpath:beans.xml") //Allows this class to find context (beans, etc...)
public class CoreDatabaseTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void testDatabaseStructure(){
        //Query our Schema to check that we have the correct three tables
        String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = SCHEMA()";
        assertTrue(jdbcTemplate.queryForList(query).toString().contains("[{TABLE_NAME=HOUSEHOLD}, {TABLE_NAME=OCCUPANTRECORDS}, {TABLE_NAME=PERSON}]"));
    }

    @Test
    public void testDatabaseEntries(){
        //Query each table counting the entries to ensure it matches our data.sql file
        String query = "SELECT COUNT (personID) FROM Person";
        assertEquals(16, jdbcTemplate.queryForObject(query, int.class));
        query = "SELECT COUNT (houseID) FROM Household";
        assertEquals(6, jdbcTemplate.queryForObject(query, int.class));
        query = "SELECT COUNT (occupantID) FROM OccupantRecords";
        assertEquals(16, jdbcTemplate.queryForObject(query, int.class));
    }
}
