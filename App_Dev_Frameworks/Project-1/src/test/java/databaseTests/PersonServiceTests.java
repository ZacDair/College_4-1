package databaseTests;

import com.dair.classes.Person;
import com.dair.service.HouseholdService;
import com.dair.service.PersonService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class) //Add Spring to Junit
@ContextConfiguration("classpath:beans.xml") //Allows this class to find context (beans, etc...)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Allows ordering the tests in this class
public class PersonServiceTests {

    @Autowired
    PersonService personService;

    @Autowired
    HouseholdService householdService;

    @Test
    @Order(1)
    public void testDeletingAllPersons(){
        //Find all person entities in
        List<Person> foundPersons = personService.findAllPersons();

        //For each remove the person from the occupantRecords and persons table (FKey references)
        for (Person p : foundPersons){
            householdService.deleteRecordByID(p.getPersonID());
            personService.deletePersonByID(p.getPersonID());
        }

        //Find all persons should now return an empty list
        foundPersons = personService.findAllPersons();
        assertTrue(foundPersons.isEmpty());
    }

    @Test
    @Order(2)
    public void testFindingAgeAvgWhenEmpty(){
        System.out.println("Testing Non-existing Average Calculation:");
        //Firstly check that the persons table is actually empty (cleared in test 1)
        List<Person> foundPersons = personService.findAllPersons();
        assertTrue(foundPersons.isEmpty());
        //Assert that it returns 0, instead of a null pointer exception
        assertEquals(0, personService.getAverageAge());
    }

    @Test
    @Order(3)
    public void testCountingByOccupationWhenEmpty(){
        System.out.println("Testing Non-existing Occupation Search:");
        //Firstly check that the persons table is actually empty (cleared in test 1)
        List<Person> foundPersons = personService.findAllPersons();
        assertTrue(foundPersons.isEmpty());
        //Check that when searching an empty table for a job that doesn't exist we return 0
        assertEquals(0, personService.countByOccupation("TestOccupation"));
    }
}
