package com.warner_dair.controllers;

import com.warner_dair.entities.Director;
import com.warner_dair.forms.NewDirectorForm;
import com.warner_dair.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class NewDirectorController {

    @Autowired
    DirectorService directorService;

    @GetMapping("/newdirector")
    public String addDirector(Model model){
        model.addAttribute("newDirectorForm", new NewDirectorForm());
        return "newdirector";
    }

    @PostMapping("/newdirector")
    public String addDirectorPost(@Valid NewDirectorForm newDirectorForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "newdirector";
        }
        Director director = directorService.save(newDirectorForm.getNewDirectorFirstName(), newDirectorForm.getNewDirectorLastName());
        if (director == null){
            redirectAttributes.addFlashAttribute("duplicateDirectorName", (newDirectorForm.getNewDirectorFirstName()+" "+ newDirectorForm.getNewDirectorLastName()));
            return "redirect:newdirector";
        }
        return "redirect:director/" + director.getDirectorId();
    }
}
