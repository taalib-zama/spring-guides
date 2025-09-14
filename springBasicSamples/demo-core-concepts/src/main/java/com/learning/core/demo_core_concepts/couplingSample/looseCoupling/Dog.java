package com.learning.core.demo_core_concepts.couplingSample.looseCoupling;

public class Dog implements Animal{
    public void eat() {
        System.out.println("Cat is eating");
    }

    @Override
    public void play() {
        System.out.println("Cat is playing");
    }
}
