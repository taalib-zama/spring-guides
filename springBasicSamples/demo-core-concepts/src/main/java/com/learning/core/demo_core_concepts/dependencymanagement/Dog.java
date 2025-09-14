package com.learning.core.demo_core_concepts.dependencymanagement;

import org.springframework.stereotype.Component;


public class Dog implements Animal {
    public void eat() {
        System.out.println("Cat is eating");
    }

    @Override
    public void play() {
        System.out.println("Cat is playing");
    }
}
