package databaseTests;

import com.dair.classes.Household;
import com.dair.dao.HouseholdDao;
import com.dair.dao.PersonDao;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class) //Add Spring to Junit
@ContextConfiguration("classpath:beans.xml") //Allows this class to find context (beans, etc...)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Allows ordering the tests in this class
public class HouseholdDaoTests {

    @Autowired
    HouseholdDao householdDao;

    @Autowired
    PersonDao personDao;

    @Test
    @Order(1)
    public void testInitialHouseholdCount(){
        //Find all households from the DB (initially 6 are created)
        List<Household> households = householdDao.findAllHouseholds();
        assertEquals(6, households.size());
    }

    @Test
    @Order(2)
    public void testFindingAHouseholdByID(){
        //Find a household by an ID (houseID: 3 also has eircode: C90 KE10)
        Household foundHousehold = householdDao.findHouseholdByID(3);
        assertEquals("C90 KE10", foundHousehold.getHouseEircode());
    }

    @Test
    @Order(3)
    public void testRemovingHouseByID(){
        //Find all occupants for the household with ID 3
        //Remove them from the occupant records and the person tables (due to FKey references)
        List<Integer> personIDList = householdDao.findAllOccupantsByHouseID(3);
        for (int personID : personIDList) {
            householdDao.deleteRecordByID(personID);
            personDao.deletePersonByID(personID);
        }
        //Delete the house with ID 3, and then look for houseID 3 find house should return null
        int numberDeleted = householdDao.deleteHouseByID(3);
        Household foundHousehold = householdDao.findHouseholdByID(3);
        assertEquals(1, numberDeleted);
        assertNull(foundHousehold);
    }
}
