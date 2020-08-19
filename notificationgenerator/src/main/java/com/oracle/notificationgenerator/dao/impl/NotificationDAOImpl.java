package com.oracle.notificationgenerator.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.oracle.notificationgenerator.dao.NotificationDAO;
import com.oracle.notificationgenerator.model.Notification;

@Service
public class NotificationDAOImpl implements NotificationDAO {
	

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Notification> getAllNotification() {
		return mongoTemplate.findAll(Notification.class);
	}

	@Override
	public List<Notification> getNotificationListByName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return mongoTemplate.find(query, Notification.class);
	}

	@Override
	public Notification createNotification(Notification notification) {
		return mongoTemplate.save(notification);
	}

}
