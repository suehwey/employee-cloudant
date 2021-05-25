package com.garage.upskills.employeecloudant.config;

import com.garage.upskills.employeecloudant.domain.CloudantEmployee;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Autowired
    KafkaConfigurationProperties properties;

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name(properties.getTopic()).partitions(1).replicas(1).build();
    }

    @Bean
    public ProducerFactory<String, CloudantEmployee> producerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getKeySerializer());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getValueSerializer());
        return new DefaultKafkaProducerFactory(config);
    }

    @Bean
    public KafkaTemplate<String, CloudantEmployee> kafkaTemplate(){
        return new KafkaTemplate<String, CloudantEmployee>(producerFactory());
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, CloudantEmployee> consumerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getAutoOffsetReset());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, properties.getKeyDeserializer());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, properties.getValueDeserializer());
        config.put(JsonDeserializer.TRUSTED_PACKAGES, properties.getTrustedPackages());

        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),
                new JsonDeserializer<>(CloudantEmployee.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CloudantEmployee> kafkaListener(){
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
