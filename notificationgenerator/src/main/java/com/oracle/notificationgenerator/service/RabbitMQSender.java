package com.oracle.notificationgenerator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${oracle.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${oracle.rabbitmq.routingkey}")
	private String routingkey;
	
	@Value("${oracle.rabbitmq.routingkey.travel}")
	private String travelRoutingkey;
	
	@Value("${oracle.rabbitmq.routingkey.hotel}")
	private String hotelRoutingkey;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public void send(com.oracle.common.model.Notification notification) {
		rabbitTemplate.convertAndSend(exchange, routingkey, notification);
		LOG.info("Send msg = "+ notification.toString());
	    
	}
	
	public void send(com.oracle.common.model.Notification notification,String routingKey) {
		String rkey = routingKey.contains("travel") ? travelRoutingkey : routingKey.contains("hotel") ? hotelRoutingkey : routingkey;
		rabbitTemplate.convertAndSend(exchange, rkey , notification);
		LOG.info("Send msg = "+ notification.toString());		
	}
}
