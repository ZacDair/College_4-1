package com.dair.service;

import com.dair.classes.Household;
import com.dair.dao.HouseholdDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseholdServiceImplementation implements HouseholdService {

    @Autowired
    HouseholdDao householdDao;

    public Household findHouseholdByID(int householdID) {
        Household returnedHousehold = householdDao.findHouseholdByID(householdID);
        if (returnedHousehold == null){
            System.out.println("Error - No Household found with ID: "+ householdID +"\n");
        }
        return returnedHousehold;
    }

    //Validates the search household by Eircode
    public Household findHouseholdByEircode(String eircode){
        Household returnedHousehold = householdDao.findHouseholdByEircode(eircode);
        if (returnedHousehold == null){
            System.out.println("Error - No Household found with Eircode: "+ eircode +"\n");
        }
        return returnedHousehold;
    }

    public ArrayList<Integer> findAllOccupantsByHouseID(int houseID) {
        ArrayList<Integer> returnedOccupantIDs = householdDao.findAllOccupantsByHouseID(houseID);
        if (returnedOccupantIDs == null){
            System.out.println("Error - No Occupants found in the Household with ID: "+ houseID +"\n");
        }
        return returnedOccupantIDs;
    }

    public Household addHousehold(String eircode, String address) {
        Household returnedHousehold = householdDao.addHousehold(eircode, address);
        if (returnedHousehold == null){
            System.out.println("Error - No Household added\n");
        }
        return returnedHousehold;
    }

    public int addPersonToHouseholdByID(int houseID, int personID) {
        int returnedOccupantRecordIndex = householdDao.addPersonToHouseholdByID(houseID, personID);
        if (returnedOccupantRecordIndex == 0){
            System.out.println("Error - Occupant record for PersonID: " + personID + " and HouseID: " + houseID + " was not added\n");
        }
        return returnedOccupantRecordIndex;
    }

    public List<Household> findAllHouseholds() {
        return householdDao.findAllHouseholds();
    }

    public int updateOccupancyByID(int newHouseID, int personID) {
        int returnedOccupantRecordIndex = householdDao.updateOccupancyByID(newHouseID, personID);
        if (returnedOccupantRecordIndex == 0){
            System.out.println("Error - Occupant record for PersonID: " + personID +" was not updated\n");
        }
        return returnedOccupantRecordIndex;
    }

    public int deleteRecordByID(int personID) {
        int numberDeleted = householdDao.deleteRecordByID(personID);
        if(numberDeleted == 0)
            System.out.println("No occupantRecords with person (ID:"+personID+") removed\n");
        return numberDeleted;
    }

    public int deleteHouseByID(int houseID) {
        int numberDeleted = householdDao.deleteHouseByID(houseID);
        if(numberDeleted == 0)
            System.out.println("No Households with house (ID:"+houseID+") removed\n");
        return numberDeleted;
    }
}
