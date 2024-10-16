package com.github.ricardobaumann.spring_boot_rest_template.inputs;

import jakarta.validation.constraints.NotBlank;

public record PersonPayload(
        @NotBlank String name,
        @NotBlank String address1,
        String address2
) {
}
