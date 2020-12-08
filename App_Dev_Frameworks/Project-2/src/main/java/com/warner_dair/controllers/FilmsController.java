package com.warner_dair.controllers;

import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import com.warner_dair.forms.EditFilmForm;
import com.warner_dair.service.DirectorService;
import com.warner_dair.service.FilmService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    @GetMapping("/editfilm")
    public String editFilmName(Model model){
        model.addAttribute("editFilmForm", new EditFilmForm());
        model.addAttribute("films", filmService.getAllFilmsChronologically());
        return "editfilm";
    }

    @PostMapping("/editfilm")
    public String addFilmPost(Model model, @Valid EditFilmForm editFilmForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("films", filmService.getAllFilmsChronologically());
            return "editfilm";
        }

        if (filmService.changeFilmName(editFilmForm.getNewFilmName(), editFilmForm.getFilmId())) {
            return "redirect:film/"+editFilmForm.getFilmId();
        }

        //redirect attrbiutes might be needed here if something fails
        return "redirect:editfilm";
    }
    @GetMapping("/delete/film")
    public String deleteFilm(Model model){
        List<Film> films = filmService.getAllFilmsChronologically();
        model.addAttribute("films", films);
        return "deletefilm";
    }

    @GetMapping("/delete/film/{filmId}")
    public String deleteFilm(@PathVariable(name="filmId") int filmId, Model model){
        if (filmService.deleteFilm(filmId)){
            return "redirect:/delete/film";
        }
        model.addAttribute("filmId", filmId);
        return "notfounderror";
    }

    @DeleteMapping("/delete/film/{filmId}")
    public String deleteMappingFilm(@PathVariable(name="filmId") int filmId, Model model){
        if (filmService.deleteFilm(filmId)){
            return "redirect:/delete/film";
        }
        model.addAttribute("filmId", filmId);
        return "notfounderror";
    }
}
