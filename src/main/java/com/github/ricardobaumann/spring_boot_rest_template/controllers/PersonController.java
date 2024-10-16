package com.github.ricardobaumann.spring_boot_rest_template.controllers;

import com.github.ricardobaumann.spring_boot_rest_template.inputs.CreatePersonPayload;
import com.github.ricardobaumann.spring_boot_rest_template.output.ResourceRef;
import com.github.ricardobaumann.spring_boot_rest_template.services.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    /*
    Controller layer is responsible for
    * Handling and validating STATIC input payloads
    * Calling SERVICE layer logic
    * Rendering response payloads and HTTP statuses based on the service layer results
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceRef createPerson(@RequestBody @Valid CreatePersonPayload createPersonPayload) {
        /*
         * Persistent entities should not be used as I/O payloads
         * Response payloads should be trimmed to the minimum necessary
         */
        return new ResourceRef(personService.create(createPersonPayload).getId());
    }

}
