package com.dair.controllers;

import com.dair.entities.County;
import com.dair.forms.NewCountyForm;
import com.dair.service.CountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class NewCountyController {
    @Autowired
    CountyService countyService;

    @GetMapping("/newcounty")
    public String addCounty(Model model){
        model.addAttribute("newCountyForm", new NewCountyForm());
        return "newcounty";
    }
    /* Handling Adding a new county, no duplicate error checking
    @PostMapping("/newcounty")
    public String addCountyPost(NewCountyForm newCountyForm){
        County county = countyService.save(newCountyForm.getNewCountyName());
        return "redirect:county/" + county.getCountyId();
    }
     */

    @PostMapping("/newcounty")
    public String addCountyPost(@Valid NewCountyForm newCountyForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "newcounty";
        }
        County county = countyService.save(newCountyForm.getNewCountyName());
        if (county == null){
            redirectAttributes.addFlashAttribute("duplicateCountyName", newCountyForm.getNewCountyName());
            return "redirect:newcounty";
        }
        return "redirect:county/" + county.getCountyId();
    }




}
