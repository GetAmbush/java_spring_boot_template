package com.getambush.spring_boot_rest_template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getambush.spring_boot_rest_template.output.ResourceRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
Integration tests are meant to check if the whole endpoint stack works
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(statements = "delete from person")//Reusable annotation can be created to clean-up/setup DB
class PersonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private ResourceRef resourceRef;

    /*
    Basic REST CRUD tests could even be generated with AI, or with
    some annotation-based code generator
     */
    @BeforeEach
    void setup() throws Exception {
        String createdResponse = mockMvc.perform(post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "test-person",
                                    "address1": "first address line",
                                    "address2": "second address line"
                                }
                                """)
                ).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        resourceRef = objectMapper.readValue(createdResponse, ResourceRef.class);
    }

    @Test
    void shouldReturnValidPerson() throws Exception {
        //When
        String fetchResponse = fetchPerson();

        //Response json asserting can also be done with specific libraries, like JsonAssert
        //Then
        assertThat(fetchResponse).isEqualToIgnoringWhitespace(
                """
                        {
                            "name": "test-person",
                            "address1": "first address line",
                            "address2": "second address line"
                        }
                        """
        );
    }

    private String fetchPerson() throws Exception {
        return mockMvc.perform(get("/people/{id}", resourceRef.id())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test
    void shouldUpdatePerson() throws Exception {
        //When
        final String content = """
                {
                    "name": "test-person updated",
                    "address1": "first address line updated",
                    "address2": "second address line updated"
                }
                """;
        mockMvc.perform(put("/people/{id}", resourceRef.id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk());

        //Then
        assertThat(fetchPerson()).isEqualToIgnoringWhitespace(content);
    }

    @Test
    void shouldDeletePerson() throws Exception {
        //Then
        mockMvc.perform(delete("/people/{id}", resourceRef.id()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/people/{id}", resourceRef.id()))
                .andExpect(status().isNotFound());
    }
}