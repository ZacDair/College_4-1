package com.dair.controllers;


import com.dair.entities.Director;
import com.dair.entities.Film;
import com.dair.forms.FindFilmForm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class MyController {

    HttpHeaders createHeaders(String username, String password)
    {
        /*
         * A HttpHeader is a data structure representing a HTTP request or response header.
         * It maps String header names to a list of String values (Map).
         */
        return new HttpHeaders() {
            {
                // create a string from the given username and password
                String auth = username + ":" + password;
                // convert the string to a byte array
                byte[] encodeStringIntoBytes = auth.getBytes(StandardCharsets.UTF_8);
                // encode the binary data to ASCII text.
                byte[] encodedAuth = Base64.encodeBase64(encodeStringIntoBytes);
                // convert the encoded byte array to a string with "Basic" at the start
                String authHeader = "Basic " + new String( encodedAuth );
                // set --> sets the value of "Authorization" to authHeader
                set(HttpHeaders.AUTHORIZATION, authHeader);
                // Thus the authorisation data can be sent to the REST API for authorisation/authentication
            }};
    }

    private String USERNAME = "api@api.ie";
    private String PASSWORD = "password";
    private HttpHeaders headers = createHeaders(USERNAME, PASSWORD);

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
            ResponseEntity<List<Film>> response = restTemplate.exchange(URL,
                    HttpMethod.GET,
                    new HttpEntity<>(this.headers),
                    new ParameterizedTypeReference<>() {
                    });

            model.addAttribute("films", response.getBody());
            return "films";
        }
        catch (HttpClientErrorException exception){
            redirectAttributes.addFlashAttribute("filmError", findFilmForm.getFilmReleaseYear());
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
        ResponseEntity<List<Director>> response = restTemplate.exchange("http://localhost:8080/myapi/directors",
                HttpMethod.GET,
                new HttpEntity<>(this.headers),
                new ParameterizedTypeReference<List<Director>>(){});
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
                    new HttpEntity<>(this.headers),
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
