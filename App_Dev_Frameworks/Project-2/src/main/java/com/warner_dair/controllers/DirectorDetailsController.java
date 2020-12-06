package com.warner_dair.controllers;

import com.warner_dair.entities.Director;
import com.warner_dair.service.DirectorService;
import com.warner_dair.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DirectorDetailsController {

    @Autowired
    DirectorService directorService;

    @Autowired
    FilmService filmService;

    @GetMapping("director/{directorId}")
    public String getFilmByFilmId(@PathVariable(name="directorId") int directorId, Model model){
        Director director = directorService.getDirectorById(directorId);
        if(director == null){
            model.addAttribute("directorId", directorId);
            return "notfounderror";
        }
        model.addAttribute("director", director);
        return "director";
    }
}
