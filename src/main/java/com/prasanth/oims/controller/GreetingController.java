package com.prasanth.oims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping("/")
    public String greet() {
        return "Hello, welcome to the OIMS application!";
    }   
    
}
