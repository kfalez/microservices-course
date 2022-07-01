package com.keltenfalez.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = { // so we have the ability to inject the RabbitMQMessageProducer
                "com.keltenfalez.customer",
                "com.keltenfalez.amqp",
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.keltenfalez.clients"
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}