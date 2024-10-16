package com.github.ricardobaumann.spring_boot_rest_template.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedPersonException extends RuntimeException {
}
