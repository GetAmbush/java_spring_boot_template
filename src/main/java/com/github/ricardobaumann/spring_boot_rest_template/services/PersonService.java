package com.github.ricardobaumann.spring_boot_rest_template.services;

import com.github.ricardobaumann.spring_boot_rest_template.entities.Person;
import com.github.ricardobaumann.spring_boot_rest_template.exceptions.DuplicatedPersonException;
import com.github.ricardobaumann.spring_boot_rest_template.inputs.CreatePersonPayload;
import com.github.ricardobaumann.spring_boot_rest_template.repos.PersonRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepo personRepo;

    @Transactional //Transaction handling is a service level responsibility
    public Person create(CreatePersonPayload createPersonPayload) {

        /*
        Include any dynamic validations and  business logic here
         */
        if (personRepo.existsByNameAndAddress1(createPersonPayload.name(), createPersonPayload.address1())) {
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
                        .name(createPersonPayload.name())
                        .address1(createPersonPayload.address1())
                        .address2(createPersonPayload.address2())
                        .build());
    }

}
