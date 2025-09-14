package com.learning.core.demo_core_concepts.dependencymanagement;

public enum AnimalType {
    CAT,
    DOG, DEFAULT;


    public static AnimalType getDefault() {
        return DOG;
    }
}
