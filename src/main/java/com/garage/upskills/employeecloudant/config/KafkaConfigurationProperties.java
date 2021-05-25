package com.garage.upskills.employeecloudant.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URL;

@Data
@Component
@ConfigurationProperties("spring.kafka")
public class KafkaConfigurationProperties {

    String bootstrapServers;

    String groupId;

    String autoOffsetReset;

    String keySerializer;

    String valueSerializer;

    String keyDeserializer;

    String valueDeserializer;

    String topic;

    String trustedPackages;

//    String enableAutoCommit;


}
