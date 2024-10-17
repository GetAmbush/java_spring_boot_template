package com.getambush.spring_boot_rest_template.usecases;

import com.getambush.spring_boot_rest_template.entities.Person;
import com.getambush.spring_boot_rest_template.inputs.PersonPayload;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

@Transactional
//Transaction handling is a service level responsibility
public interface PersonUseCases {
    Person create(@NotNull PersonPayload personPayload);

    Optional<Person> findById(UUID id);

    void deleteById(UUID id);

    void update(PersonPayload personPayload, UUID id);
}
