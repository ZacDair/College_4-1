package com.warner_dair.service;

import com.warner_dair.entities.Director;

import java.util.List;

public interface DirectorService {

    Director save(String directorFirstName, String directorLastName);

    List<Director> getAllDirectorsAlphabetically();

    Director getDirectorById(int directorId);

    boolean deleteDirectorAndFilms(int directorId);
}
