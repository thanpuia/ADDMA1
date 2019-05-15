package com.lalthanpuia.addma1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.NotificationRepository;
import com.lalthanpuia.addma1.entity.Notification;
import com.lalthanpuia.addma1.entity.UserNotification;

@Service
public class NotificationServiceImpl implements NotificationService {

	private NotificationRepository notificationRepository;
	
	@Autowired
	public NotificationServiceImpl (NotificationRepository theNotificationRepository) {
		notificationRepository = theNotificationRepository;
	}

	@Override
	public List<Notification> findAll() {
		return notificationRepository.findAll();
	}
	
}
