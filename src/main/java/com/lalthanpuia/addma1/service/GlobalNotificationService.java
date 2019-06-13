package com.lalthanpuia.addma1.service;

import java.util.List;

import com.lalthanpuia.addma1.entity.GlobalNotification;

public interface GlobalNotificationService {

	public void save(GlobalNotification theGlobalNotification);
	
	public List<GlobalNotification> findAll();
}
