package com.dair;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {

        //Retrieve the application context from our beans.xml file
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // Scanner input object
        Scanner in = new Scanner(System.in);

        // Create a controller object to process the queries
        Controller controller = new Controller(context);

        //Create a menu object for displaying options and getting input
        Menu menu = new Menu();

        // Loop our application
        while (true) {
            int input = menu.runMenu();
            switch (input) {
                case 1: {
                    System.out.println("Searching households by Eircode");
                    System.out.print("Please enter the Eircode to search for: ");
                    String eircode = in.nextLine();
                    controller.searchByEircode(eircode);
                    break;
                }
                case 2: {
                    System.out.println("Adding a household and occupants");
                    controller.createHouseholdAndOccupants();
                    break;
                }
                case 3: {
                    System.out.println("Adding a new person and assign household");
                    controller.createPersonAndAssignHousehold();
                    break;
                }
                case 4: {
                    System.out.println("Moving a person to a different household");
                    controller.movePersonBetweenHouseholds();
                    break;
                }
                case 5: {
                    System.out.println("Deleting a household and it's occupants");
                    controller.deleteHouseholdAndOccupants();
                    break;
                }
                case 6: {
                    System.out.println("Deleting a person");
                    controller.removePersonByID();
                    break;
                }
                case 7: {
                    System.out.println("Displaying database statistics");
                    controller.displayDatabaseStatistics();
                    break;
                }
                case 8: {
                    System.out.println("Exiting the application");
                    System.exit(0);
                    break;
                }
            }
        }
    }
}
