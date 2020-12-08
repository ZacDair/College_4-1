package com.dair.controllers;


import com.dair.entities.County;
import com.dair.forms.NewCountyForm;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
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

    // Get all Counties
    @GetMapping("/showcounties")
    public String showCounties(Model model){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<County>> response = restTemplate.exchange("http://localhost:7070/myapi/counties", HttpMethod.GET, null, new ParameterizedTypeReference<List<County>>(){});
        model.addAttribute("counties", response.getBody());
        return "counties";
    }

    // Delete a county
    @GetMapping("/deletecounty/{countyId}")
    public String deleteCounty(@PathVariable(name="countyId") int countyId, Model model){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL = "http://localhost:7070/myapi/county/{countyId}";
            ResponseEntity<County> responseEntity = restTemplate.exchange(
                    URL,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<County>(){},
                    countyId
            );
            return "redirect:/showcounties";
        }
        catch (Exception exception){
            return "error";
        }
    }

    // Adding a county
    @GetMapping("/newcounty")
    public String addCounty(Model model){
        model.addAttribute("newCountyForm", new NewCountyForm());
        return "newcounty";
    }

    @PostMapping("/newcounty")
    public String addCounty(@Valid NewCountyForm newCountyForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "newcounty";
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            String URL = "http://localhost:7070/myapi/county";
            HttpEntity<String> request = new HttpEntity<>(newCountyForm.getNewCountyName());
            ResponseEntity<County> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, request, County.class);
            return "redirect:showcounties";
        }
        catch (HttpClientErrorException exception){
            redirectAttributes.addFlashAttribute("countyName", newCountyForm.getNewCountyName());
            redirectAttributes.addFlashAttribute("problem", true);
            return "redirect:newcounty";
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return "error";
        }
    }

}
