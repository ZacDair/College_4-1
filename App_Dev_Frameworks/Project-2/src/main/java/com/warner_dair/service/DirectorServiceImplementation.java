package com.warner_dair.service;

import com.warner_dair.dao.DirectorDao;
import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DirectorServiceImplementation implements DirectorService{

    @Autowired
    DirectorDao directorDao;

    @Autowired
    FilmService filmService;

    @Override
    public Director save(String directorFirstName, String directorLastName){
        if(directorDao.existsByDirectorLastName(directorLastName)){
            log.error("ERROR: Duplicate Directory Addition Attempt");
            return null;
        }
        return directorDao.save(new Director(directorFirstName, directorLastName));
    }

    @Override
    public List<Director> getAllDirectorsAlphabetically() {
        return directorDao.findAllByOrderByDirectorLastNameAsc();
    }

    @Override
    public Director getDirectorById(int directorId) {
        return directorDao.findDirectorAndFilmsByDirectorId(directorId);
    }

    @Override
    public boolean deleteDirectorAndFilms(int directorId) {
        Director foundDirector = getDirectorById(directorId);
        if(foundDirector != null){
            List<Film> directorFilms = foundDirector.getDirectorFilms();
            for (Film film : directorFilms){
                filmService.deleteFilm(film.getFilmId());
            }
            foundDirector.setDirectorFilms(null);
            directorDao.delete(foundDirector);
        }
        if (getDirectorById(directorId) == null){
            return true;
        }
        return false;
    }
}
