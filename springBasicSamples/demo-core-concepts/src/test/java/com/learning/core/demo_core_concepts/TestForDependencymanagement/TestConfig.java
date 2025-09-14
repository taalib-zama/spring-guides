package com.learning.core.demo_core_concepts.TestForDependencymanagement;

import com.learning.core.demo_core_concepts.dependencymanagement.AnimalFactory;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public AnimalFactory testAnimalFactory() {
        return Mockito.mock(AnimalFactory.class);
    }
}
