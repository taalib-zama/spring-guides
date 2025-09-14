package com.learning.core.demo_core_concepts.TestForDependencymanagement;

import com.learning.core.demo_core_concepts.dependencymanagement.Person;
import com.learning.core.demo_core_concepts.dependencymanagement.PersonControllerBeanSample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonControllerBeanSample.class)
@DisplayName("Person Controller Web Layer Tests")
public class PersonControllerWebTest {

    //WebMvcTest - Controller Layer:
    // It focuses only on the web layer, allowing you to test the controller's behavior in isolation.
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Person person;

    @Test
    @DisplayName("Should handle feed request successfully")
    void shouldHandleFeedRequestSuccessfully() throws Exception {
        // Given
        when(person.feedAnimal("dog")).thenReturn("Fed dog successfully");

        // When & Then
        // When & Then
        mockMvc.perform(get("/personModifiedDI/feed")
                        .param("animalType", "dog"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Fed dog"))) // Now works
                .andDo(print());
    }


}
