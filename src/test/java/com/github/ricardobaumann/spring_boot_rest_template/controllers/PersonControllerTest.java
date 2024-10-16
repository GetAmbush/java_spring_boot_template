package com.github.ricardobaumann.spring_boot_rest_template.controllers;

import com.github.ricardobaumann.spring_boot_rest_template.usecases.PersonUseCases;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc
@MockBeans({@MockBean(PersonUseCases.class)})
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /*
    This test only covers the static validations at the controller level,
    hence there is no need for an integration test setup
     */
    @Test
    @DisplayName("Invalid person create attempts should return 401 - Bad Request")
    void shouldValidateInput() throws Exception {

        //Individual scenarios can be checked, depending on how error messages are rendered
        mockMvc.perform(post("/people")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        }
                        """)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundOnAbsent() throws Exception {
        mockMvc.perform(post("/people/", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}