package com.warner_dair.controllers;

import com.warner_dair.entities.Director;
import com.warner_dair.service.DirectorService;
import com.warner_dair.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DirectorsController {
    @Autowired
    DirectorService directorService;

    @Autowired
    FilmService filmService;

    @GetMapping("directors")
    public String showDirectors(Model model){
        List<Director> directors = directorService.getAllDirectorsAlphabetically();
        model.addAttribute("directors", directors);
        return "directors";
    }
}
