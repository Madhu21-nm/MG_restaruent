package com.madhu.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping({"/","/home"})
    public String getHome() {
        return "home";
    }
    
    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }
    
    @GetMapping("/menu")
    public String getMenu() {
        return "menu";
    }
    
    @GetMapping("/checkout")
    public String getCheckout() {
        return "checkout";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String getRegister() {
        return "signup";
    }

    @GetMapping("/restarent1")
    public String restaurant1() {
        return "restarent1";       // → loads templates/restaurant1.html
}
}