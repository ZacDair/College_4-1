package classTests;

import com.dair.classes.Household;
import com.dair.classes.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BaseClassTests{

    @Test
    public void testHouseholdSetters(){
        Household h = new Household();
        h.setHouseID(10);
        h.setHouseEircode("CK1 93EP");
        h.setHouseAddress("128, Withrop Street, Cork");
        assertTrue(h.toString().contains("128, Withrop Street, Cork") && h.getHouseEircode().equals("CK1 93EP")
                && h.getHouseID() == 10);
    }
    @Test
    public void testPersonSetters(){
        Person p = new Person();
        p.setPersonID(98);
        p.setPersonName("John Smith");
        p.setPersonAge(53);
        p.setPersonOccupation("Carpenter");
        assertTrue(p.getPersonID() == 98 && p.toString().contains("John Smith") && p.toString().contains("53")
                && p.getPersonOccupation().equalsIgnoreCase("Carpenter"));
    }

    @Test
    public void testHouseholdConstructor(){
        //Our constructors do not require ID as ID is created in the database, a placeholder ID of -1 is used
        Household h = new Household("CK1 PX89", "Rubicon Campus, Cork");
        assertEquals("CK1 PX89", h.getHouseEircode());
        assertEquals("Rubicon Campus, Cork", h.getHouseAddress());
        assertEquals(-1, h.getHouseID());
    }

    @Test
    public void testPersonConstructor(){
        //Our constructors do not require ID as ID is created in the database, a placeholder ID of -1 is used
        //However we can add an ID field as we have all args constructors as well
        Person p = new Person(98, "John Smith", 23, "Data Scientist");
        assertEquals(98, p.getPersonID());
        assertEquals("John Smith", p.getPersonName());
        assertEquals(23, p.getPersonAge());
        assertEquals("Data Scientist", p.getPersonOccupation());
    }



}