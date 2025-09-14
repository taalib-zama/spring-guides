package com.learning.core.demo_core_concepts.couplingSample.tightCoupling;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Animal {
    public void eat() {
        System.out.println("Animal is eating");
    }

    public void play() {
        System.out.println("Animal is playing");
    }

}
