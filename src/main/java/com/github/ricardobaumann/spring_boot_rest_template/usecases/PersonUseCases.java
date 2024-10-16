package com.github.ricardobaumann.spring_boot_rest_template.usecases;

import com.github.ricardobaumann.spring_boot_rest_template.entities.Person;
import com.github.ricardobaumann.spring_boot_rest_template.inputs.PersonPayload;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface PersonUseCases {
    @Transactional
        //Transaction handling is a service level responsibility
    Person create(PersonPayload personPayload);

    Optional<Person> findById(UUID id);
}
