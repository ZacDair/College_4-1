package com.warner_dair.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // Get request loading the index.html page
    @GetMapping("/")
    public String loadIndex(){
        return "index";
    }
}
