package com.dair;

import com.dair.classes.Household;
import com.dair.classes.Person;
import com.dair.service.HouseholdService;
import com.dair.service.PersonService;
import lombok.Data;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public class Controller {

    //Attributes, our app context, object services, and input scanner
    private ApplicationContext appContext;
    private HouseholdService householdService;
    private PersonService personService;
    private Scanner in = new Scanner(System.in);

    //Constructor (takes in our application context from main where this contructor is called)
    public Controller(ApplicationContext context) {
        appContext = context;
        householdService = (HouseholdService) appContext.getBean("householdServiceImplementation");
        personService = (PersonService) appContext.getBean("personServiceImplementation");
    }

    //Finds all household entries in the database
    public List<Household> findAllHouseholds(){
        return householdService.findAllHouseholds();
    }

    //Additional function that prints all of our households, and returns the highest ID
    private int displayAllHouseholds(List<Household> households){
        System.out.println("Households from the Database:");
        int highestID = -1;
        for (Household h : households){

            //Print each household's details
            System.out.println(h + "\n");
            //Find the highest ID, for input checking ranges
            if (h.getHouseID() > highestID){
                highestID = h.getHouseID();
            }
        }
        return highestID;
    }

    //Finds all person entries in the database
    public List<Person> findAllPersons(){
        return personService.findAllPersons();
    }

    private int displayAllPersons(List<Person> personList){
        System.out.println("Persons from the Database:");
        int highestID = -1;
        for (Person p : personList){

            //Print each person's details
            System.out.println(p + "\n");
            //Find the highest ID, for input checking ranges
            if (p.getPersonID() > highestID){
                highestID = p.getPersonID();
            }
        }
        return highestID;
    }

    //Gets next integer from input scanner and validates
    private int getIntInput(int minInt, int maxInt){
        int res = -1;
        while (res == -1) {
            try {
                System.out.print("(Enter a value ["+minInt+"-"+maxInt+"]): ");
                res = in.nextInt();
                if (minInt > res || res > maxInt){
                    res = -1;
                    System.out.println("Please enter an integer between " + minInt + " and " + maxInt);
                }
                in.nextLine();
            }
            catch (Exception e){
                System.out.println("Please enter an integer");
                in.nextLine();
            }
        }
        return res;
    }

    //Creates a household object based on the user inputs (ID -1 as it's not created in DB)
    public Household createHousehold(){
        // Gets user inputs for a household creates and returns the object
        System.out.print("Please enter the household details: \nEircode: ");
        String eircode = in.nextLine();
        System.out.print("Address: ");
        String address = in.nextLine();
        return new Household(eircode, address);
    }

    //Creates a person object based on the user inputs (ID -1 as it's not created in DB)
    public Person createPerson(){
        // Get user input for name, age (error check to prevent crashing), occupation
        System.out.print("Name: ");
        String name = in.nextLine();
        System.out.print("Age ");
        int age = getIntInput(0, 200);
        String occupation;
        if(age <= 5){
            occupation = "Pre-School";
        }
        else if (age <= 18){
            occupation = "Scholar";
        }
        else {
            System.out.println("\nAre you studying or working ?\n1.Working\n2.Studying");
            if (getIntInput(1, 2) == 1) {
                System.out.print("Please enter your job title: ");
                occupation = in.nextLine();
            } else {
                occupation = "Scholar";
            }
        }
        System.out.println();
        return new Person(name, age, occupation);
    }

    //Full Functionality for searching for a household by eircode and displaying household and occupant details
    public void searchByEircode(String eircode){
        Household foundHousehold = householdService.findHouseholdByEircode(eircode);
        if (foundHousehold != null) {
            ArrayList<Integer> foundPersonIds = householdService.findAllOccupantsByHouseID(foundHousehold.getHouseID());
            ArrayList<Person> occupants = new ArrayList<>();
            for (Integer id : foundPersonIds) {
                occupants.add(personService.findPersonByPersonID(id));
            }
            System.out.println("\nHousehold Details:");
            System.out.println(foundHousehold + "\n");
            System.out.println("Occupant Details:");
            if (occupants.isEmpty()){
                System.out.println("\t"+"No Occupants\n");
            }
            else {
                for (Person p : occupants) {
                    System.out.println(p + "\n");
                }
            }
        }
    }

    //Full Functionality for creating a new household and adding new occupants
    public void createHouseholdAndOccupants(){
        // Get user input for Household details (returns Household object with ID-1)
        Household h = createHousehold();

        // Add the household to the database (exit function if it fails)
        Household addedHouse = householdService.addHousehold(h.getHouseEircode(), h.getHouseAddress());
        if (addedHouse != null){
            System.out.println("\nAdded Household:\n" + addedHouse);
            System.out.println("\nPlease enter the person's details:");

            // Loop until the user has added all occupants
            boolean isDone = false;
            while(!isDone) {

                // Get user input for Person details (returns Person object with ID-1)
                Person p = createPerson();

                // Add the person to the database (returns the added person using the ID it returns when added)
                Person addedPerson = personService.addPerson(p.getPersonName(), p.getPersonAge(), p.getPersonOccupation());

                // Add the person's ID and the houseID to the occupantRecords table
                int occupantRecordIndex = householdService.addPersonToHouseholdByID(addedHouse.getHouseID(), addedPerson.getPersonID());
                if (occupantRecordIndex != 0) {
                    System.out.println("\nAdded Person:\n" + addedPerson);
                } else {
                    System.out.println("The person was not added to the occupancy records\n");
                }

                // Looping input to allow multiple occupants (error checking for yes and no inputs)
                String inputString = "";
                boolean isValid = false;
                while (!isValid) {
                    System.out.print("\nWould you like to add another occupant (yes/no):");
                    inputString = in.nextLine();
                    if (inputString.toLowerCase().equals("no") || inputString.toLowerCase().equals("yes")) {
                        isValid = true;
                    } else {
                        System.out.println("Sorry, Please enter yes or no...");
                    }
                }
                if (inputString.toLowerCase().equals("no")) {
                    isDone = true;
                }
            }
            //Display our added results (using our search by eircode function)
            searchByEircode(addedHouse.getHouseEircode());
        }
        else {
            System.out.println("Sorry, the Household was not added\n");
        }
    }

    //Full Functionality for creating a new person and adding it to a household
    public void createPersonAndAssignHousehold(){
        int houseID = -1;
        //Get user inputs to create a person object
        Person p = createPerson();
        Person addedPerson = personService.addPerson(p.getPersonName(), p.getPersonAge(), p.getPersonOccupation());
        //Ask the user if they are adding to an existing household or creating a new one
        System.out.println("Please select an option:\n1.Assign to existing Household\n2.Create a new Household");
        int option = getIntInput(1, 2);
        if (option == 1){
            List<Household> households = findAllHouseholds();
            int highestID = displayAllHouseholds(households);
            System.out.println("Please enter the houseID from the List above:");
            houseID = getIntInput(1, highestID);
        }
        else if(option ==2){
            Household h = createHousehold();
            Household addedHousehold = householdService.addHousehold(h.getHouseEircode(), h.getHouseAddress());
            houseID = addedHousehold.getHouseID();
        }
        if (houseID != -1) {
            int occupantRecordIndex = householdService.addPersonToHouseholdByID(houseID, addedPerson.getPersonID());
            if (occupantRecordIndex != 0) {
                System.out.println("Added Person:\n" + addedPerson);
                System.out.println("Assigned to Household (ID:" + houseID + ")\n");
            } else {
                System.out.println("The person was not added to the occupancy records");
            }
        }
        else{
            System.out.println("Sorry, No household was selected for the person to be assigned into");
        }
    }


    public void movePersonBetweenHouseholds() {
        List<Person> personList = findAllPersons();
        int highestID = displayAllPersons(personList);
        System.out.println("Please enter the personID from the List above:");
        int personID = getIntInput(1, highestID);
        System.out.println("You selected the person (ID:"+personID+")\n");
        List<Household> householdList = findAllHouseholds();
        highestID = displayAllHouseholds(householdList);
        System.out.println("Please enter the houseID from the List above:");
        int houseID = getIntInput(1, highestID);
        System.out.println("You selected to move the person (ID:"+personID+") to the household (ID:"+houseID+")\n");
        int numUpdated = householdService.updateOccupancyByID(houseID, personID);
        if (numUpdated != 0) {
            System.out.println(numUpdated + " Occupancy records for person (ID: " + personID + ") updated\n");
            System.out.println("Would you like to see the updated destination house: \n1.View Updated Household\n2.Return to Menu");
            int option = getIntInput(1, 2);
            if (option == 1) {
                System.out.println("Destination House Details:");
                System.out.println(householdService.findHouseholdByID(houseID)+"\n");
            }
        }
        else{
            System.out.println("Sorry, The person (ID:"+personID+") was not moved\n");
        }

    }

    public void deleteHouseholdAndOccupants(){
        List<Household> householdList = findAllHouseholds();
        int highestID = displayAllHouseholds(householdList);
        System.out.println("Please enter the houseID from the List above:");
        int houseID = getIntInput(1, highestID);
        List<Integer> personIDList = householdService.findAllOccupantsByHouseID(houseID);
        for (int personID : personIDList) {
            int status = householdService.deleteRecordByID(personID);
            if (status != 0){
                System.out.println("Person (ID:"+personID+") was removed from the OccupantRecords table");
                status = personService.deletePersonByID(personID);
                if (status == 1){
                    System.out.println("Person (ID:"+personID+") was removed from the Person table");
                }
            }
        }
        if (householdService.deleteHouseByID(houseID) == 1){
            System.out.println("House (ID:"+houseID+") was removed from the Household table\n");
        }
    }

    public void removePersonByID() {
        List<Person> personList = findAllPersons();
        int highestID = displayAllPersons(personList);
        System.out.println("Please enter the personID from the List above:");
        int personID = getIntInput(0, highestID);
        int status = householdService.deleteRecordByID(personID);
        if (status != 0){
            System.out.println("Person (ID:"+personID+") was removed from the OccupantRecords table");
            status = personService.deletePersonByID(personID);
            if (status == 1){
                System.out.println("Person (ID:"+personID+") was removed from the Person table\n");
            }
        }
    }

    public void displayDatabaseStatistics(){
        List<Person> personList = findAllPersons();
        List<Household> householdList = findAllHouseholds();
        int avgAge = personService.getAverageAge();
        int preSchoolCount = personService.countByOccupation("Pre-School");
        int scholarCount = personService.countByOccupation("Scholar");
        int OAPCount = personService.countByAge(65);
        System.out.println("\n\t----Database Statistics----");
        System.out.println("\tAverage Age: " + avgAge);
        System.out.println("\tPre-School Student Count: " + preSchoolCount);
        System.out.println("\tScholar Count: " + scholarCount);
        System.out.println("\tTotal Student Count: " + preSchoolCount+scholarCount);
        System.out.println("\tOAP (65+) Count: " + OAPCount);
        System.out.println("\n\t----Additional Statistics----");
        System.out.println("\tOur Database Contains:\n\tA total of " + personList.size() + " people and " + householdList.size() + " households.\n");
    }
}