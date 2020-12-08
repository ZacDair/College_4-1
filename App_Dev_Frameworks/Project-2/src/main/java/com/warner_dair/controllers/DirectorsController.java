package com.warner_dair.controllers;

import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import com.warner_dair.service.DirectorService;
import com.warner_dair.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DirectorsController {
    @Autowired
    DirectorService directorService;

    @Autowired
    FilmService filmService;

    @GetMapping("/directors")
    public String showDirectors(Model model){
        List<Director> directors = directorService.getAllDirectorsAlphabetically();
        model.addAttribute("directors", directors);
        return "directors";
    }

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

    @GetMapping("/delete/director")
    public String deleteDirector(Model model){
        List<Director> directors = directorService.getAllDirectorsAlphabetically();
        model.addAttribute("directors", directors);
        return "deletedirector";
    }

    @GetMapping("/delete/director/{directorId}")
    public String deleteDirector(@PathVariable(name="directorId") int directorId, Model model){
        if (directorService.deleteDirectorAndFilms(directorId)){
            return "redirect:/delete/director";
        }
        model.addAttribute("directorId", directorId);
        return "notfounderror";
    }
}
