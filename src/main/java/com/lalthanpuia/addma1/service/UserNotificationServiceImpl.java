package com.lalthanpuia.addma1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.UserNotificationRepository;
import com.lalthanpuia.addma1.entity.UserNotification;

@Service
public class UserNotificationServiceImpl implements UserNotificationService {
	
	private UserNotificationRepository userNotificationRepository;
	
	@Autowired
	public UserNotificationServiceImpl (UserNotificationRepository theUserNotificationRepository) {
		userNotificationRepository = theUserNotificationRepository;
	}

	@Override
	public void save(UserNotification theUserNotification) {
		
		userNotificationRepository.save(theUserNotification);
	}
}
