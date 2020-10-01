package com.dair.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Franchise {
    private String franchiseName;
    private Publisher publisher;

    //Constructor to allow for auto-wiring
    public Franchise(String franchiseName){
        this.franchiseName = franchiseName;
    }
}
