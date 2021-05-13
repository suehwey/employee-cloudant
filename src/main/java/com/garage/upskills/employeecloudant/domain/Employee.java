package com.garage.upskills.employeecloudant.domain;

import com.cloudant.client.api.model.Document;
import lombok.Data;

@Data
public class Employee extends Document {

    private String firstName;
    private String lastName;
    private String role;
    private String city;
    private String email;
}
