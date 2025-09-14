package com.learning.core.demo_core_concepts.TestForDependencymanagement;

import com.learning.core.demo_core_concepts.dependencymanagement.Animal;
import com.learning.core.demo_core_concepts.dependencymanagement.AnimalFactory;
import com.learning.core.demo_core_concepts.dependencymanagement.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Person Service Tests")
public class PersonTest {
    // unit test for Person Service

    @Mock
    private AnimalFactory animalFactory;

    @Mock
    private Animal mockAnimal;

    @InjectMocks
    private Person person;

    @Nested
    @DisplayName("Feed Animal")
    class FeedAnimal {

        @ParameterizedTest
        @ValueSource(strings = {"DOG", "CAT"})
        @DisplayName("Should feed animal successfully for valid types")
        void shouldFeedAnimalSuccessfully(String animalType) {
            // Given
            when(animalFactory.getAnimal(animalType)).thenReturn(mockAnimal);

            // When
            String result = person.feedAnimal(animalType);

            // Then
            assertAll(
                    () -> verify(mockAnimal).eat(),
                    () -> verify(mockAnimal).play(),
                    () -> assertThat(result).contains("Animal fed: " + animalType)
            );
        }

        @Test
        @DisplayName("Should throw exception for invalid animal type")
        void shouldThrowExceptionForInvalidType() {
            // Given
            when(animalFactory.getAnimal("INVALID"))
                    .thenThrow(new IllegalArgumentException("Unknown animal type"));

            // When & Then
            assertThatThrownBy(() -> person.feedAnimal("INVALID"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Unknown animal type");
        }
    }

}
