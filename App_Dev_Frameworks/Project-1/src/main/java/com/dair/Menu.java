package com.dair;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    //Attributes
    private final Scanner in = new Scanner(System.in);

    //Display our menu using prints
    private void displayMenu() {
        System.out.println("+----------------Menu----------------+");
        System.out.println("| 1.Search by Eircode                |");
        System.out.println("| 2.Add a House and it's Occupants   |");
        System.out.println("| 3.Add a Person and assign a House  |");
        System.out.println("| 4.Move a Person between Houses     |");
        System.out.println("| 5.Delete a House and it's Occupants|");
        System.out.println("| 6.Delete a Person                  |");
        System.out.println("| 7.Display Database Statistics      |");
        System.out.println("| 8.Exit the application             |");
        System.out.println("+------------------------------------+");
        System.out.print("Please enter your menu option: ");
    }

    //Validate the inputted value is between our min and max option values
    private boolean validateMenuSelection(int input){
        return input > 0 && input <= 8;

    }

    //Get user input using nextInt
    private int getMenuSelection(){
        int input = -1;
        try {
            input = in.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("Sorry, Please only enter an Integer value.\n");
        }
        in.nextLine();
        return input;
    }

    public int runMenu(){
        displayMenu();
        int input = getMenuSelection();
        boolean isValid = validateMenuSelection(input);
        if (isValid){
            System.out.println();
            return input;
        }
        else {
            System.out.println("Sorry, Please enter an Integer value between 1-5.\n");
            return -1;
        }
    }
}


