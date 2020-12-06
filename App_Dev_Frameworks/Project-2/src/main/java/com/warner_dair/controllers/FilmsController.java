package com.warner_dair.controllers;

import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import com.warner_dair.service.DirectorService;
import com.warner_dair.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FilmsController {
    @Autowired
    DirectorService directorService;

    @Autowired
    FilmService filmService;

    @GetMapping("/films")
    public String showFilms(Model model){
        List<Film> films = filmService.getAllFilmsChronologically();
        model.addAttribute("films", films);
        return "films";
    }
}
