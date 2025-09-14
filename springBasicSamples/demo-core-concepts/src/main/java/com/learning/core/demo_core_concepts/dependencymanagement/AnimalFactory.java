package com.learning.core.demo_core_concepts.dependencymanagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AnimalFactory {

    public  Animal getAnimal(String type) {
        // Here we can decide which animal to return

        if (Objects.equals(type.toUpperCase(), AnimalType.CAT.toString())) {
            return new Cat();
        } else if (Objects.equals(type.toUpperCase(), AnimalType.DOG.toString())) {
            return new Dog();
        }
        throw new IllegalArgumentException("Unknown animal type");
    }
}
