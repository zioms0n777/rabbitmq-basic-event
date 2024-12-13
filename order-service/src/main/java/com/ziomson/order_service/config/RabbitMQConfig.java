package com.ziomson.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {



    @Value("${rabbitmq.queue.order.name}")
    private String orderQueue;


@Value("${rabbitmq.exchange.name}")
    private String exchangeQueue;

@Value("${rabbitmq.binding.routing.key}")
private String routingKey;


@Value("${rabbitmq.binding.email.routing.key}")
private String emailRoutingKey;

@Value("${rabbitmq.queue.email.name}")
private String emailQueue;
    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }

@Bean
    public Queue emailQueue() {return new Queue(emailQueue); }

    @Bean
public TopicExchange orderExchange() {
        return new TopicExchange(exchangeQueue);
}

@Bean
public Binding binding()
{
    return BindingBuilder.bind(orderQueue())
            .to(orderExchange())
            .with(routingKey);
}
@Bean
    public Binding emailBinding()
    {
        return BindingBuilder.bind(emailQueue())
                .to(orderExchange())
                .with(emailRoutingKey);
    }
@Bean
public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;

    }
}
