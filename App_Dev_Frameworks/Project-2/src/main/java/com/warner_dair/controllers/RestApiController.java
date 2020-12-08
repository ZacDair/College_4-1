package com.warner_dair.controllers;

import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import com.warner_dair.service.DirectorService;
import com.warner_dair.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("myapi")
public class RestApiController {
    @Autowired
    DirectorService directorService;

    @Autowired
    FilmService filmService;

    // Return a list of films for a specific year or 404
    @GetMapping("/film/by/year/{releaseYear}")
    public ResponseEntity<List<Film>> getFilmsByYear(@PathVariable(name = "releaseYear") int releaseYear) {
        List<Film> films = filmService.findFilmsByFilmReleaseYear(releaseYear);
        if (films == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Film>>(films, HttpStatus.FOUND);
    }

    // Return a list of directors or 404
    @GetMapping("/directors")
    public ResponseEntity<List<Director>> getDirectors() {
        List<Director> directors = directorService.getAllDirectorsAlphabetically();
        if (directors == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Director>>(directors, HttpStatus.FOUND);
    }


    // Deletes a director entity and it's films using a delete mapping
    @DeleteMapping("/director/delete/{directorId}")
    public ResponseEntity<Director> deleteDirector(@PathVariable(name="directorId") int directorId) {
        boolean deleted = directorService.deleteDirectorAndFilms(directorId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}