package com.garage.upskills.employeecloudant.controller;

import com.garage.upskills.employeecloudant.domain.CloudantEmployee;
import com.garage.upskills.employeecloudant.service.KafkaEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KafkaController {

    @Autowired
    private KafkaEmployeeService service;

    @PostMapping("/kafka/addEmployee")
    public void saveEmployee(@RequestBody CloudantEmployee employee) {
        service.sendEmployee(employee);
    }

    @PostMapping("/kafka/addEmployees")
    public void saveEmployees(@RequestBody List<CloudantEmployee> employees) {
        service.sendEmployees(employees);
    }
}
