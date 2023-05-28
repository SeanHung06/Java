package com.example.Payroll;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
  // Spring Boot will run ALL CommandLineRunner beans once the application context is loaded. 
  // Bean is used to indicate that a method produces a bean to be managed by the Spring container.
  @Bean
  // CommandLineRunner is a simple Spring Boot interface with a run method and initDatabase method is used to pre-load some data.
  CommandLineRunner initDatabase(EmployeeRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Employee("Bilbo", "Baggins", "burglar")));
      log.info("Preloading " + repository.save(new Employee("Frodo", "Baggins", "thief")));
    };
  }
}