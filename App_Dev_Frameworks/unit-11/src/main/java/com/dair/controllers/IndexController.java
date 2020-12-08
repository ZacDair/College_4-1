package com.dair.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    /* Single Variable assignment works by request contents
    @GetMapping("/")
    // localhost:8080?myName=Zac
    public String loadIndexGet(@RequestParam(name = "myName", required = false, defaultValue = "") String name, Model model){
        model.addAttribute("name", name);
        return "index";
    }
     */

    //Multiple Variable assignment works by request contents
    @GetMapping("/")
    // localhost:8080?myName=Zac&myEmail=email@email.com
    public String loadIndexGet(@RequestParam(name = "myName", required = false, defaultValue = "") String name,
                               @RequestParam(name = "myEmail", required = false, defaultValue = "") String email,Model model){
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return "index";
    }


    /* Single Variable assignment works by path contents
    @GetMapping("/{myName}")
    // localhost:8080/Zac
    public String loadIndexGetPath(@PathVariable("myName") String name, Model model){
        model.addAttribute("name", name);
        return "index";
    }
     */

    // Multiple Variable assignment works by path contents
    @GetMapping("/details/{myName}/{myEmail}")
    // localhost:8080/details/Zac/email@email.com
    public String loadIndexGetPath(@PathVariable("myName") String name, @PathVariable("myEmail") String email, Model model){
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return "index";
    }

    // Post request loading content
    @PostMapping("/")
    public String loadIndexPost(){
        return "index";
    }
}
