package com.dair.service;

import com.dair.classes.Household;

import java.util.ArrayList;
import java.util.List;

public interface HouseholdService {

    Household findHouseholdByID(int householdID);

    Household findHouseholdByEircode(String eircode);

    ArrayList<Integer> findAllOccupantsByHouseID(int houseID);

    Household addHousehold(String eircode, String address);

    int addPersonToHouseholdByID(int houseID, int personID);

    List<Household> findAllHouseholds();

    int updateOccupancyByID(int newHouseID, int personID);

    int deleteRecordByID(int personID);

    int deleteHouseByID(int houseID);
}
