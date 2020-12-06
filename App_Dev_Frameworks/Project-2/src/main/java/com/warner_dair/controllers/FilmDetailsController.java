package com.warner_dair.controllers;

import com.warner_dair.entities.Film;
import com.warner_dair.service.DirectorService;
import com.warner_dair.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FilmDetailsController {

    @Autowired
    DirectorService directorService;

    @Autowired
    FilmService filmService;

    @GetMapping("film/{filmId}")
    public String getFilmByFilmId(@PathVariable("filmId") int filmId, Model model){
        Film film = filmService.findFilmById(filmId);
        if (film == null){
            model.addAttribute("filmId", filmId);
            return "notfounderror";
        }
        model.addAttribute("film", film);
        model.addAttribute("director", film.getFilmDirector());
        return "film";
    }
}
