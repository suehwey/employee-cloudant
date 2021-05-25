package com.garage.upskills.employeecloudant.service;

import com.garage.upskills.employeecloudant.domain.CloudantEmployee;
import com.garage.upskills.employeecloudant.producer.EmployeeProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaEmployeeService {

    @Autowired
    private EmployeeProducer producer;

    public void sendEmployee (CloudantEmployee employee) {
        producer.sendMessage(employee);
    }

    public void sendEmployees(List<CloudantEmployee> employees) {
        employees.forEach(this::sendEmployee);
    }
}
