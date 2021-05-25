package com.garage.upskills.employeecloudant.consumer;

import com.garage.upskills.employeecloudant.domain.CloudantEmployee;
import com.garage.upskills.employeecloudant.service.CloudantEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {

    @Autowired
    private CloudantEmployeeService service;

    private final Logger logger = LoggerFactory.getLogger(EmployeeConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "kafkaListener")
    public void consume (CloudantEmployee employee)  {
        logger.info("=== consume: " + employee);
        // add to cloudant database
        CloudantEmployee savedEmployee = service.saveEmployee(employee);

        logger.info("=== saved to cloudant: " + savedEmployee);
    }
}
