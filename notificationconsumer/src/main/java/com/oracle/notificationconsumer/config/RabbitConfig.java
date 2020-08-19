package com.oracle.notificationconsumer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.oracle.common.model.Notification;

@Component
public class RabbitConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);
	
		
	@RabbitListener(queues = "${oracle.rabbitmq.queue.hotel}")
	public void recievedHotelMessage(Notification notification) {
		logger.info("Recieved Message From Hotel Q: " + notification);
		JSONBuilder builder = new JSONBuilder();
		builder.writeIntoFile(notification);
	}
	
	@RabbitListener(queues = "${oracle.rabbitmq.queue.travel}")
	public void recievedTravelMessage(Notification notification) {
		logger.info("Recieved Message From Travel Q: " + notification);
		JSONBuilder builder = new JSONBuilder();
		builder.writeIntoFile(notification);
	}

}
