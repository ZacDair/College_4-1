package com.warner_dair.service;

import com.warner_dair.dao.FilmDao;
import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
