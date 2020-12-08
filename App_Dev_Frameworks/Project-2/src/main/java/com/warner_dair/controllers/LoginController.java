package com.warner_dair.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Get request loading the login.html page
    @GetMapping("/login")
    public String loadLogin(){
        return "login";
    }

    // load the access denied error page
    @GetMapping("/403")
    public String loadAccessDenied(){
        return "403";
    }
}
