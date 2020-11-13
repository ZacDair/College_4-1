package com.dair.service;


import com.dair.classes.Person;

import java.util.List;

public interface PersonService {

    Person findPersonByPersonID(int personID);

    Person addPerson(String name, int age, String occupation);

    List<Person> findAllPersons();

    int deletePersonByID(int personID);

    int getAverageAge();

    int countByOccupation(String occupation);

    int countByAge(int age);
}
