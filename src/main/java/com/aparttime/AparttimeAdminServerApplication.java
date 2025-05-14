package com.aparttime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AparttimeAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AparttimeAdminServerApplication.class, args);
    }

}
