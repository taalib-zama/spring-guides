package com.learning.core.demo_core_concepts.couplingSample.tightCoupling.controller;

import com.learning.core.demo_core_concepts.couplingSample.tightCoupling.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {
    @GetMapping("/feed")
    public String feedAnimal() {
        Person person = new Person();
        person.feedAnimal();
        return "Person is feeding animal";
    }
}
