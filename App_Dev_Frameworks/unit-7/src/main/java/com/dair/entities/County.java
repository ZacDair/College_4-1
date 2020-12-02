package com.dair.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
// To override standard table naming use: @Entity(name= "Counties")
public class County {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "countyID")
    private int countyID;

    @Column(name= "countyName", unique= true, nullable = false)
    private String countyName;

    @OneToMany(mappedBy = "townCounty", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Town> countyTowns = new ArrayList<>();

    // Custom Constructor
    public County(String countyName) {
        this.countyName = countyName;
    }

    @Override
    public String toString(){
        return "\nCounty Name: " + this.countyName + "\nCounty ID: " + this.countyID;
    }

}
