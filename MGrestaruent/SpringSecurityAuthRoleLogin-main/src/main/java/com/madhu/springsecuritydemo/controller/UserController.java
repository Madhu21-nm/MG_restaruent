package com.madhu.springsecuritydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.madhu.springsecuritydemo.model.AllUsers;
import com.madhu.springsecuritydemo.repository.AllUsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class UserController {
    
    @Autowired
    private AllUsersRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public String createUser(@ModelAttribute AllUsers user, @RequestParam(required = false) String from, RedirectAttributes redirectAttributes) {
        user.setRole("USER");
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        String redirectUrl = "redirect:/login";
        if ("checkout".equals(from)) {
            redirectUrl += "?from=checkout";
        }
        return redirectUrl;
    }
}