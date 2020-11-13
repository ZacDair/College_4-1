package com.dair.service;

import com.dair.classes.Person;
import com.dair.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonServiceImplementation implements PersonService {

    @Autowired
    PersonDao personDao;

    public Person findPersonByPersonID(int personID) {
        Person returnedPerson = personDao.findPersonByPersonID(personID);
        if (returnedPerson == null){
            System.out.println("Error - No Person found with ID: "+ personID+"\n");
        }
        return returnedPerson;
    }

    public Person addPerson(String name, int age, String occupation) {
        Person returnedPerson = personDao.addPerson(name, age, occupation);
        if (returnedPerson == null){
            System.out.println("Error - No Person added\n");
        }
        return returnedPerson;
    }

    public List<Person> findAllPersons() {
        List<Person> foundPeople = personDao.findAllPersons();
        if(foundPeople.isEmpty()){
            System.out.println("Error - No Persons found in the database\n");
        }
        return foundPeople;
    }

    public int deletePersonByID(int personID) {
        int numberDeleted = personDao.deletePersonByID(personID);
        if(numberDeleted == 0)
            System.out.println("No Person with person (ID:"+personID+") removed\n");
        return numberDeleted;
    }

    public int getAverageAge() {
        Integer ageAvg = personDao.getAverageAge();
        if(ageAvg == null) {
            System.out.println("No Persons in the Database, returning 0 as average age\n");
            return 0;
        }
        else {
            return ageAvg;
        }
    }

    public int countByOccupation(String occupation) {
        return personDao.countByOccupation(occupation);
    } //TEST ME

    public int countByAge(int age) {
        return personDao.countByAge(age);
    }
}
