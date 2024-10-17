package com.getambush.spring_boot_rest_template.services;

import com.getambush.spring_boot_rest_template.entities.Person;
import com.getambush.spring_boot_rest_template.exceptions.DuplicatedPersonException;
import com.getambush.spring_boot_rest_template.inputs.PersonPayload;
import com.getambush.spring_boot_rest_template.repos.PersonRepo;
import com.getambush.spring_boot_rest_template.usecases.PersonUseCases;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonService implements PersonUseCases {

    /*
    Please go for construction injection over field injection
     */
    @Delegate
    private final PersonRepo personRepo;

    @Override
    public Person create(PersonPayload personPayload) {
        /*
        Include any dynamic validations and  business logic here
         */
        if (existsByNameAndAddress1(personPayload.name(), personPayload.address1())) {
            //don't forget to always index fields used to filter and join data
            throw new DuplicatedPersonException();
        }

        /*
        Service class interfaces with the repository layer, but without knowing underlying technology details.
        Is it a RDB, a non-SQL database or a file system? it does not matter.
        Dealing with those details is the repository layer responsibility
         */
        return save(toEntity(personPayload));
    }

    private Person toEntity(final PersonPayload personPayload) {
        return Person.builder() //object mapping can be delegated to a mapper helper, but its is primary a service responsibility
                .id(UUID.randomUUID())
                .name(personPayload.name())
                .address1(personPayload.address1())
                .address2(personPayload.address2())
                .build();
    }

    @Override
    public void update(final PersonPayload personPayload, final UUID id) {
        Person person = toEntity(personPayload);
        person.setId(id);
        save(person);
    }
}
