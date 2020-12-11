package com.warner_dair.service;

import com.warner_dair.dao.FilmDao;
import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FilmServiceImplementation implements FilmService{
    @Autowired
    FilmDao filmDao;

    @Override
    public List<Film> getAllFilmsChronologically() {
        return filmDao.findAllByOrderByFilmReleaseYearAsc();
    }

    @Override
    public Film save(String filmName, int releaseYear, Director director) {
        // Create a calendar option to get the current year
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        // If the inputted year was greater than the current year return null and display an error message
        if (releaseYear > currentYear){
            log.error("ERROR: Release Year should be between 1888 and " + currentYear);
            return null;
        }
        return filmDao.save(new Film(filmName, releaseYear, director));
    }

    @Override
    public boolean changeFilmName(String newFilmName, int filmId) {
        Optional<Film> optionalFilm = filmDao.findById(filmId);
        if(optionalFilm.isPresent()){
            filmDao.changeFilmName(newFilmName, filmId);
            return true;
        }
        return false;
    }

    @Override
    public Film findFilmById(int filmId) {
        Optional<Film> optionalFilm = filmDao.findById(filmId);
        if (optionalFilm.isPresent()){
            return optionalFilm.get();
        }
        return null;
    }

    @Override
    public boolean deleteFilm(int filmId) {
        filmDao.deleteById(filmId);
        return findFilmById(filmId) == null;
    }

    @Override
    public List<Film> findFilmsByFilmReleaseYear(int releaseYear) {
        return filmDao.findAllByFilmReleaseYear(releaseYear);
    }
}
