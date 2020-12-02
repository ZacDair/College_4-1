package com.dair.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int townID;

    @Column(nullable = false)
    private String townName;

    //This is the owning side of the relationship
    @ManyToOne
    @JoinColumn(name = "countyID", nullable = false)
    private County townCounty;

    public Town(String townName, County townCounty){
        this.townName = townName;
        this.townCounty = townCounty;
    }


}
