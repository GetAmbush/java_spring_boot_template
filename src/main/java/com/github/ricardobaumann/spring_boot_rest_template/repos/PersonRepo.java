package com.github.ricardobaumann.spring_boot_rest_template.repos;

import com.github.ricardobaumann.spring_boot_rest_template.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepo extends CrudRepository<Person, UUID>, PagingAndSortingRepository<Person, UUID> {
    boolean existsByNameAndAddress1(String name, String address1);


    /*
    This layer is responsible for just
    * Dealing with persistence
    * Applying resilience (circuit breakers, retries, rate limiters, bulk heads, etc)
     */

}
