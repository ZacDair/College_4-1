package com.warner_dair.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name= "Directors")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int directorId;

    // Doesn't cover real life cases where they have the same name, or go by a single stage name
    @Column(nullable = false)
    private String directorFirstName;

    @Column(nullable = false, unique=true)
    private String directorLastName;

    // One director can have one or more films (One to many relationship)
    @JsonIgnore
    @OneToMany(mappedBy= "filmDirector", fetch= FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Film> directorFilms = new ArrayList<>();

    public Director(String directorFirstName, String directorLastName){
        this.directorFirstName = directorFirstName;
        this.directorLastName = directorLastName;
    }
}
