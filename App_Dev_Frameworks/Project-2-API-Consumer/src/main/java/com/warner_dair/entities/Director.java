package com.warner_dair.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
