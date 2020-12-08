package com.dair.controllers;


import com.dair.entities.Director;
import com.dair.entities.Film;
import com.dair.forms.FindFilmForm;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MyController {

    // Find all films by release year
    @GetMapping("/findfilm")
    public String findFilms(Model model){
        model.addAttribute("findFilmForm", new FindFilmForm());
        return "findfilm";
    }

    @PostMapping("/findfilm")
    public String findFilmsPost(@Valid FindFilmForm findFilmForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if (bindingResult.hasErrors()){
            return "findfilm";
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            String URL = "http://localhost:8080/myapi/film/by/year/"+findFilmForm.getFilmReleaseYear();
            System.out.println(URL);
            ResponseEntity<List<Film>> response = restTemplate.exchange(URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

            model.addAttribute("films", response.getBody());
            return "films";
        }
        catch (HttpClientErrorException exception){
            redirectAttributes.addFlashAttribute("countyName", findFilmForm.getFilmReleaseYear());
            redirectAttributes.addFlashAttribute("problem", true);
            return "redirect:/findfilm";
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return "error";
        }
    }

    @GetMapping("/directors")
    public String viewDirectors(Model model){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Director>> response = restTemplate.exchange("http://localhost:8080/myapi/directors", HttpMethod.GET, null, new ParameterizedTypeReference<List<Director>>(){});
        model.addAttribute("directors", response.getBody());
        return "directors";
    }

    // Delete a director
    @GetMapping("/director/delete/{directorId}")
    public String deleteDirector(@PathVariable(name="directorId") int directorId, Model model){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL = "http://localhost:8080/myapi/director/delete/{directorId}";
            ResponseEntity<Director> responseEntity = restTemplate.exchange(
                    URL,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<Director>(){},
                    directorId
            );
            return "redirect:/directors";
        }
        catch (Exception exception){
            return "error";
        }
    }
}
