package com.getambush.spring_boot_rest_template.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String address1;

    private String address2;

}
