package com.lalthanpuia.addma1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.GlobalNotificationRepository;
import com.lalthanpuia.addma1.entity.GlobalNotification;

@Service
public class GlobalNotificationServiceImpl implements GlobalNotificationService {
	
	private GlobalNotificationRepository globalNotificationRepository;
	
	@Autowired
	public GlobalNotificationServiceImpl(GlobalNotificationRepository theGlobalNotificationRepository) {
		globalNotificationRepository = theGlobalNotificationRepository;
	}

	public void save(GlobalNotification theGlobalNotification) {
		globalNotificationRepository.save(theGlobalNotification);
	}

	public List<GlobalNotification> findAll() {
		// TODO Auto-generated method stub
		return globalNotificationRepository.findAll();
	}
	
	

}
