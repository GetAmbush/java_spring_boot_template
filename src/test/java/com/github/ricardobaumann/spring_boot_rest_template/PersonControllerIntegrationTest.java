package com.github.ricardobaumann.spring_boot_rest_template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ricardobaumann.spring_boot_rest_template.output.ResourceRef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
Integration tests are meant to check if the whole endpoint stack works
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldPersistAndReturnValidPerson() throws Exception {
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

        ResourceRef resourceRef = objectMapper.readValue(createdResponse, ResourceRef.class);

        String fetchResponse = mockMvc.perform(get("/people/{id}", resourceRef.id())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        //Response json asserting can also be done with specific libraries, like JsonAssert
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
}