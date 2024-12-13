package com.ziomson.email_service.consumer;


import com.ziomson.email_service.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

private Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

@RabbitListener(queues = "${rabbitmq.queue.email.name}")
public void consumer(OrderEvent orderEvent) {
    LOGGER.info(String.format("Order event received in email service => %s", orderEvent.toString()));


    // service should send email to customer
}

}