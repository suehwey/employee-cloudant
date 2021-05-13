package com.garage.upskills.employeecloudant.config;

import com.garage.upskills.employeecloudant.service.CloudantEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import javax.annotation.PostConstruct;
import java.net.URL;

@ConfigurationProperties("cloudant")
@Configuration
public class EmployeeConfiguration {

    @Autowired
    private CloudantClient cloudantClient;

    @Value("${cloudant.db}")
    private String dbname;

    private Database cloudantDB;

    @Bean
    public CloudantClient client(@Value("${cloudant.url}") URL url, @Value("${cloudant.username}") String username, @Value("${cloudant.password}") String password) {
        return ClientBuilder
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @PostConstruct
    private void getDB() {
        cloudantDB = this.cloudantClient.database(dbname, true);
    }

    @Bean
    CloudantEmployeeService cloudantEmployeeService() {
        return new CloudantEmployeeService(cloudantClient, cloudantDB);
    }

}
