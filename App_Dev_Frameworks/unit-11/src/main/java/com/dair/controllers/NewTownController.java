package com.dair.controllers;

import com.dair.entities.County;
import com.dair.entities.Town;
import com.dair.forms.NewCountyForm;
import com.dair.forms.NewTownForm;
import com.dair.service.CountyService;
import com.dair.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class NewTownController {

    @Autowired
    CountyService countyService;

    @Autowired
    TownService townService;

    @GetMapping("newtown")
    public String addTown(Model model){
        model.addAttribute("newTownForm", new NewTownForm());
        model.addAttribute("counties", countyService.getAllCountiesAlphabetically());
        return "newtown";
    }

    @PostMapping("newtown")
    public String addTownPost(Model model, @Valid NewTownForm newTownForm, BindingResult bindingResult, RedirectAttributes redirectAttribute){
        if(bindingResult.hasErrors()){
            model.addAttribute("counties", countyService.getAllCountiesAlphabetically());
            return "newtown";
        }

        County county = countyService.findCounty(newTownForm.getCountyId());
        Town newTown = townService.save(newTownForm.getNewTownName(), county);

        if(newTown == null){
            redirectAttribute.addFlashAttribute("duplicateTown", newTownForm.getNewTownName());
            redirectAttribute.addFlashAttribute("duplicateCounty", county.getCountyName());
            return "redirect:newtown";
        }

        return "redirect:town/"+newTown.getTownId();
    }

}
