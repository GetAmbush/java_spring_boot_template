package com.github.ricardobaumann.spring_boot_rest_template.controllers;

import com.github.ricardobaumann.spring_boot_rest_template.entities.Person;
import com.github.ricardobaumann.spring_boot_rest_template.inputs.PersonPayload;
import com.github.ricardobaumann.spring_boot_rest_template.output.ResourceRef;
import com.github.ricardobaumann.spring_boot_rest_template.usecases.PersonUseCases;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/people")
public class PersonController {

    private final PersonUseCases personUseCases;

    /*
    Controller layer is responsible for
    * Handling and validating STATIC input payloads
    * Handling authorization
    * Calling SERVICE layer logic
    * Rendering response payloads and HTTP statuses based on the service layer results
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceRef createPerson(@RequestBody @Valid PersonPayload personPayload) {
        /*
         * Persistent entities should not be used as I/O payloads
         * Response payloads should be trimmed to the minimum necessary
         */
        return new ResourceRef(personUseCases.create(personPayload).getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonPayload> findByID(@PathVariable UUID id) {
        /*
        I know it's tempting to use the repository layer straight-away for reading operations, it can lead
        to a lot of refactorings if some business logic need to be introduced.
         */
        return personUseCases.findById(id)
                .map(this::toPayload)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private PersonPayload toPayload(Person person) {
        return new PersonPayload(
                person.getName(), person.getAddress1(), person.getAddress2()
        );
    }

}
