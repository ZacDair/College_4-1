package com.warner_dair.controllers;

import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import com.warner_dair.forms.NewFilmForm;
import com.warner_dair.service.DirectorService;
import com.warner_dair.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class NewFilmController {

    @Autowired
    DirectorService directorService;

    @Autowired
    FilmService filmService;

    @GetMapping("/newfilm")
    public String addFilm(Model model){
        model.addAttribute("newFilmForm", new NewFilmForm());
        model.addAttribute("directors", directorService.getAllDirectorsAlphabetically());
        return "newfilm";
    }

    @PostMapping("/newfilm")
    public String addFilmPost(Model model, @Valid NewFilmForm newFilmForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("directors", directorService.getAllDirectorsAlphabetically());
            return "newfilm";
        }

        Director director = directorService.getDirectorById(newFilmForm.getDirectorId());
        Film newFilm = filmService.save(newFilmForm.getNewFilmName(), newFilmForm.getNewFilmReleaseYear(), director);

        if(newFilm == null){
            redirectAttributes.addFlashAttribute("releaseYearError", true);
            return "redirect:newfilm";
        }

        return "redirect:film/"+newFilm.getFilmId();
    }
}
