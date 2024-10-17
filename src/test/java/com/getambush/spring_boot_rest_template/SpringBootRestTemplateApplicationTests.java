package com.getambush.spring_boot_rest_template;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SpringBootRestTemplateApplicationTests {

    @Test
    void contextLoads() {
    }

}
