package com.lalthanpuia.addma1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalthanpuia.addma1.entity.GlobalNotification;
import com.sun.mail.imap.protocol.ID;

public interface GlobalNotificationRepository extends JpaRepository<GlobalNotification, Integer> {

}
