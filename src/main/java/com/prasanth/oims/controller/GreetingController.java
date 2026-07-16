package com.prasanth.oims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @RequestMapping
    public String greet() {
        return "Hello, welcome to the OIMS application!";
    }   
    
}
