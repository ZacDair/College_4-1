package com.warner_dair.service;

import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;

import java.util.List;

public interface FilmService {
    List<Film> getAllFilmsChronologically();

    Film save(String filmName, int releaseYear, Director director);

    boolean changeFilmName(String newFilmName, int filmId);

    Film findFilmById(int filmId);

    boolean deleteFilm(int filmId);

    List<Film> findFilmsByFilmReleaseYear(int releaseYear);
}
