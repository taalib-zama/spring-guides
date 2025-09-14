package com.learning.core.demo_core_concepts.dependencymanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personModifiedDI")
public class PersonControllerBeanSample {


    @Autowired
    Person person;

    //API to feed animal based on type : localhost:8080/personModifiedDI/feed?animalType=dog
    @GetMapping("/feed")
    public ResponseEntity<String> feedAnimal(@RequestParam String animalType) {
        return ResponseEntity.ok(person.feedAnimal(animalType));
    }

}
