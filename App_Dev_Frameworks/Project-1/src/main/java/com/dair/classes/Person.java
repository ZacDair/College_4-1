package com.dair.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

    private int personID;
    private String personName;
    private int personAge;
    private String personOccupation;

    public Person(String name, int age, String occupation) {
        personID = -1;
        personName = name;
        personAge = age;
        personOccupation = occupation;
    }

    public String toString(){
        return "\tPerson Database ID: " + personID + "\n\tName: " + personName + "\n\tAge: " + personAge + "\n\tOccupation: "+ personOccupation;

    }
}
