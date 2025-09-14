package com.learning.core.demo_core_concepts.couplingSample.looseCoupling;

public class AnimalFactory  {

    public static Animal getAnimal(AnimalType type) {
        // Here we can decide which animal to return

        if (type == AnimalType.CAT) {
            return new Cat();
        } else if (type == AnimalType.DOG) {
            return new Dog();
        }
        throw new IllegalArgumentException("Unknown animal type");
    }
}
