package com.lalthanpuia.addma1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usernotification")
public class UserNotification {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="serial_number")
	int serialNo;
	
	@Column(name="district")
	String district;
	
	@Column(name="message")
	String message;
	
	@Column(name="phone")
	String phone;
	
	@Column(name="photo")
	String photo;
	
	@Column(name="sent_time")
	String sentTime;
	
	@Column(name="user_serialno")
	String userSerialNo;
	
	@Column(name="username")
	String username;
	
	@Column(name="sent_type")
	String sentType;
	
	@Column(name="zonal_officer_name")
	String zonalOfficerName;
	
	@Column(name="zonal_officer_contact")
	String zonalOfficerContact;

	public UserNotification() {
	}

	public UserNotification(String district, String message, String phone, String photo, String sentTime,
			String userSerialNo, String username, String sentType, String zonalOfficerName,
			String zonalOfficerContact) {
		this.district = district;
		this.message = message;
		this.phone = phone;
		this.photo = photo;
		this.sentTime = sentTime;
		this.userSerialNo = userSerialNo;
		this.username = username;
		this.sentType = sentType;
		this.zonalOfficerName = zonalOfficerName;
		this.zonalOfficerContact = zonalOfficerContact;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSentTime() {
		return sentTime;
	}

	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}

	public String getUserSerialNo() {
		return userSerialNo;
	}

	public void setUserSerialNo(String userSerialNo) {
		this.userSerialNo = userSerialNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSentType() {
		return sentType;
	}

	public void setSentType(String sentType) {
		this.sentType = sentType;
	}

	public String getZonalOfficerName() {
		return zonalOfficerName;
	}

	public void setZonalOfficerName(String zonalOfficerName) {
		this.zonalOfficerName = zonalOfficerName;
	}

	public String getZonalOfficerContact() {
		return zonalOfficerContact;
	}

	public void setZonalOfficerContact(String zonalOfficerContact) {
		this.zonalOfficerContact = zonalOfficerContact;
	}
	
	
}
