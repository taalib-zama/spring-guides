package com.learning.core.demo_core_concepts.couplingSample.looseCoupling;

public interface Animal {

    public void play();

    default void eat(){
        // No-op: Optional behavior
        //used default as i wanted to not have this method in every impl.
    }

}
