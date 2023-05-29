package com.example.accessingdatamongodb;
import org.springframework.data.annotation.Id;

public class Customer {
    @Id
    public String id;

    public String firstName;
    public String lastName;
    public Customer() {}

    public Customer(String firstname , String lastname){
        this.firstName = firstname;
        this.lastName = lastname;
    }

    @Override
    public String toString(){
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}
