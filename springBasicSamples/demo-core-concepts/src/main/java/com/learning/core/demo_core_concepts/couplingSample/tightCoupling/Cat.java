package com.learning.core.demo_core_concepts.couplingSample.tightCoupling;

public class Cat extends Animal {
    @Override
    public void eat() {
        System.out.println("Cat is eating");
    }

    @Override
    public void play() {
        System.out.println("Cat is playing");
    }
}
