package com.learning.core.demo_core_concepts.couplingSample.looseCoupling.controller;

import com.learning.core.demo_core_concepts.couplingSample.looseCoupling.AnimalType;
import com.learning.core.demo_core_concepts.couplingSample.looseCoupling.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personModified")
public class PersonControllerModified {
    @GetMapping("/feedCat")
    public String feedAnimal() {
        Person person = new Person(AnimalType.CAT);
        person.feedAnimal();
        return "Person is feeding cat";
    }

    @GetMapping("/feedDog")
    public String feedDog() {
        Person person = new Person(AnimalType.DOG);
        person.feedAnimal();
        return "Person is feeding dog";
    }
}
