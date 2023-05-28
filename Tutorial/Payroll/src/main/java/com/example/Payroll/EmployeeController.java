package com.example.Payroll;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.IanaLinkRelations;



@RestController
class EmployeeController {
    // create a constructor that takes an EmployeeRepository and autowires it into the controller
    private final EmployeeRepository repository;

    // inject an EmployeeModelAssembler because it is used to create a link to the aggregate root.
    private final EmployeeModelAssembler assembler;

    // Autowiring feature of spring framework enables you to inject the object dependency implicitly. It internally uses setter or constructor injection.
    EmployeeController(EmployeeRepository repository , EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    // all item in the repository will be returned by findAll() and it is a GET request to /employees
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all(){
        // List<EntityModel<Employee>> employees = repository.findAll().stream().map(employee -> EntityModel.of(employee, linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(), linkTo(methodOn(EmployeeController.class).all()).withRel("employees"))).collect(Collectors.toList());  
        // use withSelfRel() to create a link to one() and withRel() to create a link to all().
        // return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());

        // use assembler to convert all the Employee into EntityModel<Employee> and then collect them into a list.
        List<EntityModel<Employee>> employees = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());

        // use CollectionModel.of() to create a CollectionModel<Employee> from the list of employees, and then create a self link using linkTo() and methodOn().
        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }


    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {
        EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

    }


    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        // EntityModel is a generic container from Spring HATEOAS that includes not only the data but a collection of links.
        // linkTo(methodOn(EmployeeController.class).one(id)) builds a link to the one() method of the EmployeeController, and gets the method’s base URI.
        // withSelfRel() tells Spring HATEOAS to build a link to the aggregate root, which is available under the /employees rel.
        // return EntityModel.of(employee, linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(), linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
        // change the return type to use the EmployeeModelAssembler to produce the EntityModel<Employee> instead of creating it manually. 
        return assembler.toModel(employee);

    }


    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        // write a update logic to update the employee if it exists, or create a new employee if it does not.
        Employee updatedEmployee = repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
        // create entity model from the updated employee, and build a self link using the id.
        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);




    }
    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
        // to send a 204 No Content response.
        return ResponseEntity.noContent().build();
    }

    
}
