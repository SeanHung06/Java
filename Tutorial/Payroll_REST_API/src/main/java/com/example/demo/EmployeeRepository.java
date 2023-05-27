package com.example.demo;
import org.springframework.data.jpa.repository.JpaRepository;
// use the JpaRepository interface to create a repository for Employee objects and extend it with the EmployeeRepository interface
// Spring Data JPA repositories are interfaces with methods supporting creating, reading, updating, and deleting records against a back end data store.


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
