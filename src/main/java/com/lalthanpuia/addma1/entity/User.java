package com.lalthanpuia.addma1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="usertable")
public class UserEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@JoinColumn(name="department_id")
	@Column(name="serial_no")
	int serialNumber;
	
	@Column(name="alt_contact_name")
	String altContactName;
	
	@Column(name="alt_contact_number")
	String altContactNo;
	
	@Column(name="created_at")
	String createdAt;
	
	@Column(name="district")
	String district;
	
	@Column(name="email")
	String email;
	
	@Column(name="emer_contact_name")
	String emergencyContactName;
	
	@Column(name="emer_contact_number")
	String emergencyContactNo;
	
	@Column(name="last_login")
	String lastLogin;
	
	@Column(name="lat")
	String lat;
	
	@Column(name="lng")
	String lng;
	
	@Column(name="locality")
	String locality;
	
	@Column(name="username")
	String username;
	
	@Column(name="phone_number")
	String phoneNo;
	
	@Column(name="user_role")
	String userRole;
	
	@Column(name="update_at")
	String updateAt;
	
	@Column(name="vol_with_us")
	String volunteer;

	public UserEntity() {
	}

	public UserEntity(String altContactName, String altContactNo, String createdAt, String district, String email,
			String emergencyContactName, String emergencyContactNo, String lastLogin, String lat, String lng,
			String locality, String username, String phoneNo, String userRole, String updateAt, String volunteer) {
		this.altContactName = altContactName;
		this.altContactNo = altContactNo;
		this.createdAt = createdAt;
		this.district = district;
		this.email = email;
		this.emergencyContactName = emergencyContactName;
		this.emergencyContactNo = emergencyContactNo;
		this.lastLogin = lastLogin;
		this.lat = lat;
		this.lng = lng;
		this.locality = locality;
		this.username = username;
		this.phoneNo = phoneNo;
		this.userRole = userRole;
		this.updateAt = updateAt;
		this.volunteer = volunteer;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAltContactName() {
		return altContactName;
	}

	public void setAltContactName(String altContactName) {
		this.altContactName = altContactName;
	}

	public String getAltContactNo() {
		return altContactNo;
	}

	public void setAltContactNo(String altContactNo) {
		this.altContactNo = altContactNo;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmergencyContactName() {
		return emergencyContactName;
	}

	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}

	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}

	public void setEmergencyContactNo(String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(String volunteer) {
		this.volunteer = volunteer;
	}

	


	
}
