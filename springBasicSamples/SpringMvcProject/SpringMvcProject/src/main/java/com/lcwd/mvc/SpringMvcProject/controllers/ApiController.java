package com.lcwd.mvc.SpringMvcProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController//(Controller+ResponseBody)
@RequestMapping("/api")
public class ApiController {


    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public String helloApi() {
        return "Hello, how are you, whats going these days";

    }

    @RequestMapping(value="/users",method = RequestMethod.GET)
    public List<String> getUserData() {
        return Arrays.asList("Ram", "Shyam", "Chinkku");
    }

    @RequestMapping(value = "/create-user",method = RequestMethod.POST)
    public String createUser(){
        System.out.println("creating user");
        return "user created !!";
    }

}
