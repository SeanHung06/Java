package com.example.Payroll;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

// apply Component annotation so that Spring will automatically discover this component when Spring creates an instance of the EmployeeController.
@Component
class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>>{
    @Override
    // convert a non-model object (Employee) into a model-based object (EntityModel<Employee>).
    // The toModel() method is overridden to create a link to the aggregate root, as well as a link to each individual item in the collection.
    public EntityModel<Employee> toModel(Employee employee){
        return EntityModel.of(employee,linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
    
    
    }
    
}
