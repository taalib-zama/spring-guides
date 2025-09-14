package com.learning.core.demo_core_concepts.couplingSample.tightCoupling;

public class Person {


    //here we are hardcoding the animal object, and in future if person want to use another animal,
    // we need to change the code here, this is called tight coupling.
    //To avoid this we can use dependency injection.

    Animal animal = new Animal();

    public void feedAnimal() {
        System.out.println("Feeding the animal");
        animal.eat();
    }
}
