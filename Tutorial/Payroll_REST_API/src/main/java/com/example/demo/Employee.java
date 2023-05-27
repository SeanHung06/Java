package com.example.demo;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


// annotate the class with @Entity because it will be persisted in a relational database

@Entity
public class Employee {
    // declare the fields
    // annotate the id field with @Id so that JPA will recognize it as the object’s ID and annotate the id field with @GeneratedValue to indicate that the ID should be generated automatically
    private @Id @GeneratedValue Long id;
    private String name;
    private String role;
    // create a default constructor because JPA needs it
    Employee() {}

    Employee (String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getRole() {
        return this.role;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name= name;
    }
    public void setRole(String role) {
        this.role = role;
    }

    // annotate the class with @Override because it overrides the default implementation of the equals() method that compares the objects based on their identity
    @Override
    public boolean equals(Object o) {
        // check if the object is compared with itself
        if(this == o){
            return true;
        }
        // check if the object is an instance of Employee
        if(!(o instanceof Employee)){
            return false;
        }
        // cast the object to Employee
        Employee employee = (Employee) o;
        // check if the id, name and role are the same for both objects 
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name) && Objects.equals(this.role, employee.role);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }
    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }
}
