package com.oracle.notificationgenerator.dao;

import java.util.List;

import com.oracle.notificationgenerator.model.Notification;

public interface  NotificationDAO {
	
	List<Notification> getAllNotification();
	
	List<Notification> getNotificationListByName(String name);
	
	Notification createNotification(Notification notification);

}
