package com.github.ricardobaumann.spring_boot_rest_template.services;

import com.github.ricardobaumann.spring_boot_rest_template.entities.Person;
import com.github.ricardobaumann.spring_boot_rest_template.exceptions.DuplicatedPersonException;
import com.github.ricardobaumann.spring_boot_rest_template.inputs.PersonPayload;
import com.github.ricardobaumann.spring_boot_rest_template.repos.PersonRepo;
import com.github.ricardobaumann.spring_boot_rest_template.usecases.PersonUseCases;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonService implements PersonUseCases {

    @Delegate
    private final PersonRepo personRepo;

    @Override
    @Transactional //Transaction handling is a service level responsibility
    public Person create(PersonPayload personPayload) {

        /*
        Include any dynamic validations and  business logic here
         */
        if (personRepo.existsByNameAndAddress1(personPayload.name(), personPayload.address1())) {
            //don't forget to always index fields used to filter and join data
            throw new DuplicatedPersonException();
        }

        /*
        Service class interfaces with the repository layer, but without knowing underlying technology details.
        Is it a RDB, a non-SQL database or a file system? it does not matter.
        Dealing with those details is the repository layer responsibility
         */
        return personRepo.save(
                Person.builder() //object mapping can be delegated to a mapper helper, but its is primary a service responsibility
                        .id(UUID.randomUUID())
                        .name(personPayload.name())
                        .address1(personPayload.address1())
                        .address2(personPayload.address2())
                        .build());
    }
}
