package com.example.Payroll;

import org.springframework.data.jpa.repository.JpaRepository;
// extend from the JpaRepository interface because it provides methods supporting standard CRUD operations
interface EmployeeRepository extends JpaRepository<Employee, Long> {

}