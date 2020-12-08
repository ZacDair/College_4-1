package com.dair.entities;

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

public class Director {
    private int directorId;
    private String directorFirstName;
    private String directorLastName;
    private List<Film> directorFilms = new ArrayList<>();
    
}
