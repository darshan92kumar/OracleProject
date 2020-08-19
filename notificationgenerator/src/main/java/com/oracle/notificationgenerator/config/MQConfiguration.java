package com.oracle.notificationgenerator.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class MQConfiguration {

	@Value("${oracle.rabbitmq.exchange}")
	String queueName;

	@Value("${oracle.rabbitmq.queue}")
	String exchange;

	@Value("${oracle.rabbitmq.routingkey}")
	private String routingkey;
	
	@Value("${oracle.rabbitmq.queue.travel}")
	String travelQueueName;
	
	@Value("${oracle.routingkey.travel}")
	private String travelRoutingkey;
	
	@Value("${oracle.rabbitmq.queue.hotel}")
	String hotelQueueName;
	
	@Value("${oracle.rabbitmq.routingkey.hotel}")
	private String hotelRoutingkey;
	
	

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}
	
	@Bean
	Binding travelBinding(TopicExchange exchange) {
	    return BindingBuilder.bind(new Queue(travelQueueName, false)).to(exchange).with(travelRoutingkey);
	}
	
	@Bean
	Binding hotelBinding(TopicExchange exchange) {
	    return BindingBuilder.bind(new Queue(hotelQueueName, false)).to(exchange).with(hotelRoutingkey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}