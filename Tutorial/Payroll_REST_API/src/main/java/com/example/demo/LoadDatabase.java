package com.example.demo;
// Preload the database with some employees using Spring Boot’s CommandLineRunner
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// annotate the class with @Configuration because it will be used by Spring to bootstrap the application context
@Configuration 

class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    // annotate the method with @Bean because it will be used by Spring to generate a CommandLineRunner which will then run the code
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        // return a lambda expression that saves two employees and logs the result
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
        };
    }
    
}




