package com.warner_dair.controllers;

import com.warner_dair.entities.CustomUser;
import com.warner_dair.entities.Director;
import com.warner_dair.entities.OurRole;
import com.warner_dair.forms.NewDirectorForm;
import com.warner_dair.forms.NewUserForm;
import com.warner_dair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // Creates and returns a list of OurRole objects
    public List<OurRole> getRoles(){
        // Creates a list of roles to populate the selection option, normally would come from the DB
        List<OurRole> roles = new ArrayList<>();
        roles.add(new OurRole("ADMIN"));
        roles.add(new OurRole("API"));
        roles.add(new OurRole("USER"));
        return roles;
    }

    // Get request to load the login page
    @GetMapping("/login")
    public String loadLogin(){
        return "login";
    }

    // load the access denied error page
    @GetMapping("/403")
    public String loadAccessDenied(){
        return "403";
    }

    // Get request to load the register page
    @GetMapping("/register")
    public String loadRegister(Model model){
        model.addAttribute("roles", getRoles());
        model.addAttribute("newUserForm", new NewUserForm());
        return "register";
    }

    // Post mapping for when the user sends their registration details to be added
    @PostMapping("/register")
    public String addDirectorPost(@Valid NewUserForm newUserForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        model.addAttribute("roles", getRoles());
        if(bindingResult.hasErrors()){
            return "register";
        }
        CustomUser user = userService.save(new CustomUser(newUserForm.getNewUsername(), passwordEncoder.encode(newUserForm.getNewPassword()), newUserForm.getNewRole()));
        if (user == null){
            redirectAttributes.addFlashAttribute("duplicateUser", (newUserForm.getNewUsername()));
            return "redirect:register";
        }
        return "redirect:login";
    }
}
