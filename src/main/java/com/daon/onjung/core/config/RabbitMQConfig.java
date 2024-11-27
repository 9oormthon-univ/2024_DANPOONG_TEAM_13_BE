package com.daon.onjung.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public DirectExchange boardExchange() {
        return new DirectExchange("board-exchange");
    }

    @Bean
    public Queue likeQueue() {
        return new Queue("like-queue", true);
    }

    @Bean
    public Queue commentQueue1() {
        return new Queue("comment-queue-1", true);
    }

    @Bean Queue commentQueue2() {
        return new Queue("comment-queue-2", true);
    }

    @Bean
    public Binding boardQueue1Binding(DirectExchange boardExchange, Queue commentQueue1) {
        return BindingBuilder.bind(commentQueue1).to(boardExchange).with("board.1");
    }

    @Bean
    public Binding boardQueue2Binding(DirectExchange boardExchange, Queue commentQueue2) {
        return BindingBuilder.bind(commentQueue2).to(boardExchange).with("board.0");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter()); // JSON 컨버터 설정
        return rabbitTemplate;
    }
}
