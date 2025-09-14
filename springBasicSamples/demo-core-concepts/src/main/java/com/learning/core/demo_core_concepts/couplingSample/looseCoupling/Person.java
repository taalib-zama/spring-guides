package com.learning.core.demo_core_concepts.couplingSample.looseCoupling;

public class Person {
    private Animal animal;

    public Person(AnimalType type) {
        // Using a factory method to get an instance of Animal
        this.animal = AnimalFactory.getAnimal(type);
    }

    public void feedAnimal() {
        animal.eat();
        animal.play();
    }
}
