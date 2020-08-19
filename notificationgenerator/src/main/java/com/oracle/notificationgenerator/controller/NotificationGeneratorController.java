package com.oracle.notificationgenerator.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.notificationgenerator.dao.NotificationDAO;
import com.oracle.notificationgenerator.model.Notification;
import com.oracle.notificationgenerator.service.RabbitMQSender;

@RestController
@RequestMapping(value = "/")
public class NotificationGeneratorController {
	
	@Autowired
	private NotificationDAO notificationRepository;
	
	@Autowired
	RabbitMQSender rabbitMQSender;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Notification> getAllNotifications() {
		LOG.info("Getting all Notification.");
		return notificationRepository.getAllNotification();
		
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public List<Notification> getAllNotificationByName(@PathVariable String name) {
		return notificationRepository.getNotificationListByName(name.toUpperCase());
		
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Notification createNotification(@RequestBody Notification notification) {
		LOG.info("Creating a new notification");
		//Create and insert
		Notification notify = notificationRepository.createNotification(notification);
		rabbitMQSender.send(serializeModelNotification(notify),notify.getName().toLowerCase());
		return notify;
		
	}
	
	private com.oracle.common.model.Notification serializeModelNotification(Notification notification) {
		com.oracle.common.model.Notification notify = new com.oracle.common.model.Notification();
		notify.setId(notification.getId());
		notify.setName(notification.getName());
		notify.setDescription(notification.getDescription());
		return notify;
		
	}
	

}
