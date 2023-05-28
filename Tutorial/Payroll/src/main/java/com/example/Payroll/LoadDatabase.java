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
  CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {

    return args -> {
      employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
      employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));
      employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));
      
      orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
      orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
      orderRepository.save(new Order("Apple Watch", Status.IN_PROGRESS));
      orderRepository.findAll().forEach(order -> {
        log.info("Preloaded " + order);
      });
      
   
    };
  }
}