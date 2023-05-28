package com.example.Payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayrollApplication {

  // String ... args is a varargs parameter, which is a way to denote that a method
  public static void main(String... args) {
    SpringApplication.run(PayrollApplication.class, args);
  }
}