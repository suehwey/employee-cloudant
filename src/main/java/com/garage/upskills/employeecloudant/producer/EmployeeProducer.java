package com.garage.upskills.employeecloudant.producer;

import com.garage.upskills.employeecloudant.config.KafkaConfigurationProperties;
import com.garage.upskills.employeecloudant.domain.CloudantEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProducer {

    @Autowired
    KafkaConfigurationProperties properties;

    @Autowired
    private KafkaTemplate<String, CloudantEmployee> kafkaTemplate;

    public void sendMessage(CloudantEmployee employee)  {
        kafkaTemplate.send(properties.getTopic(), employee);
    }

}
