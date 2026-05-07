package com.ws101.cantong.EcommerceApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/test")
    public String test() {
    return "test";
    }
    @GetMapping("/simple")
    public String simple() {
    return "simple";
    }
}