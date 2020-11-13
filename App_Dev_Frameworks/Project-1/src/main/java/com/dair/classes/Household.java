package com.dair.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Household {
    private int houseID;
    private String houseEircode;
    private String houseAddress;

    public Household(String eircode, String address) {
        houseID = -1;
        houseEircode = eircode;
        houseAddress = address;

    }

    public String toString(){
        return "\tHousehold Database ID: " + houseID + "\n\tEircode: " + houseEircode + "\n\tAddress: " + houseAddress;
    }
}


