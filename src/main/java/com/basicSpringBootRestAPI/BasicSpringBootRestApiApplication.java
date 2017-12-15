package com.basicSpringBootRestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories({"com.basicSpringBootRestAPI.repository"})
@EnableMongoRepositories({"com.basicSpringBootRestAPI.repository.mongo"})
@ComponentScan("com.basicSpringBootRestAPI")
@EnableAsync
@EnableScheduling
public class BasicSpringBootRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicSpringBootRestApiApplication.class, args);
    }

}
