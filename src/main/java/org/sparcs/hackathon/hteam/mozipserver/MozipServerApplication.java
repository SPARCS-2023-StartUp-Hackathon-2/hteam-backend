package org.sparcs.hackathon.hteam.mozipserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MozipServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MozipServerApplication.class, args);
    }

}
