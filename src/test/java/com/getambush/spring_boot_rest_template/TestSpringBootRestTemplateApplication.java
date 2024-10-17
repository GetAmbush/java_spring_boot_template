package com.getambush.spring_boot_rest_template;

import org.springframework.boot.SpringApplication;

public class TestSpringBootRestTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringBootRestTemplateApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
