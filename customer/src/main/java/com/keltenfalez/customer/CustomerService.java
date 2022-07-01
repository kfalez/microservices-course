package com.keltenfalez.customer;

import com.keltenfalez.amqp.RabbitMQMessageProducer;
import com.keltenfalez.clients.fraud.FraudCheckResponse;
import com.keltenfalez.clients.fraud.FraudClient;
import com.keltenfalez.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final FraudClient fraudClient;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // TODO: check if email is valid
        // TODO: check if email is not taken
        customerRepository.saveAndFlush(customer); // so database instantly gets the customer
        // TODO: check if fraudster

        FraudCheckResponse fraudCheckResponse =
                fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        // TODO: send notification
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to keltenservices", customer.getFirstName())
        );
        // adding to the queue (making it async)
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
