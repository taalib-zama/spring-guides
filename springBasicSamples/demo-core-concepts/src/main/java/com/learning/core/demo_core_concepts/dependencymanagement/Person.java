package com.learning.core.demo_core_concepts.dependencymanagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class Person {
    @Autowired
    AnimalFactory animalFactory;

    public String feedAnimal(String animalType) {
        Animal animal = animalFactory.getAnimal(animalType);
        if (animal != null) {
            System.out.println("Currently animal is: " + animal.getClass().getSimpleName());
            animal.eat();
            animal.play();
        }
        return  "Animal fed: " + animalType;
    }
}
