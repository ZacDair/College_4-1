package com.dair.dao;

import com.dair.classes.Household;
import com.dair.classes.Person;

import java.util.List;

public interface PersonDao {

    Person findPersonByPersonID(int personID);

    Person addPerson(String name, int age, String occupation);

    List<Person> findAllPersons();

    int deletePersonByID(int personID);

    Integer getAverageAge();

    Integer countByOccupation(String occupation);

    Integer countByAge(int age);
}
