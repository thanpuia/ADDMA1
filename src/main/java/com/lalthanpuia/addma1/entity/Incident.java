package com.lalthanpuia.addma1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name="report")
public class Incident {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="serial_number")
	int serialNumber;
	
	@Column(name="disaster_type")
	String disasterType;
	
	@Column(name="locality")
	String locality;
	
	@Column(name="landmarks")
	String landmarks;
	
	@Column(name="disasters_details")
	String disastersDetails;
	
	@Column(name="details")
	String details;
	
	@Column(name="disaster_type_id")
	String disasterTypeId;
	
	@Column(name="district")
	String district;
	
	@Column(name="in_location")
	String inLocation;
	
	@Column(name="lat")
	String lat;
																					
	@Column(name="lng")
	String lng;
	
	@Column(name="location")
	String location;
	
	@Column(name="username")
	String username;
	
	@Column(name="phone")
	String phone;
	
	@Column(name="report_on")
	String reportOn;
	
	@Column(name="status")
	String status;
	
	@Column(name="user_id")
	String userId;
	
	@Column(name="zonal_officer_contact")
	String officerContact;
	
	@Column(name="zonal_officer_id")
	String officerId;
	
	@Column(name="zonal_officer_name")
	String officerName;
	
	@Column(name="zone_id")
	String zoneId;
	
	@Column(name="zone_name")
	String officerZone;
	
//	@ManyToOne
//	@JoinColumn(name="username")
//	@JsonBackReference
//	private UserEntity userEntity;

	public Incident() {
	}

public Incident(int serialNumber, String disasterType, String locality, String landmarks, String disastersDetails,
		String details, String disasterTypeId, String district, String inLocation, String lat, String lng,
		String location, String username, String phone, String reportOn, String status, String userId,
		String officerContact, String officerId, String officerName, String zoneId, String officerZone) {
	this.serialNumber = serialNumber;
	this.disasterType = disasterType;
	this.locality = locality;
	this.landmarks = landmarks;
	this.disastersDetails = disastersDetails;
	this.details = details;
	this.disasterTypeId = disasterTypeId;
	this.district = district;
	this.inLocation = inLocation;
	this.lat = lat;
	this.lng = lng;
	this.location = location;
	this.username = username;
	this.phone = phone;
	this.reportOn = reportOn;
	this.status = status;
	this.userId = userId;
	this.officerContact = officerContact;
	this.officerId = officerId;
	this.officerName = officerName;
	this.zoneId = zoneId;
	this.officerZone = officerZone;
}

public int getSerialNumber() {
	return serialNumber;
}

public void setSerialNumber(int serialNumber) {
	this.serialNumber = serialNumber;
}

public String getDisasterType() {
	return disasterType;
}

public void setDisasterType(String disasterType) {
	this.disasterType = disasterType;
}

public String getLocality() {
	return locality;
}

public void setLocality(String locality) {
	this.locality = locality;
}

public String getLandmarks() {
	return landmarks;
}

public void setLandmarks(String landmarks) {
	this.landmarks = landmarks;
}

public String getDisastersDetails() {
	return disastersDetails;
}

public void setDisastersDetails(String disastersDetails) {
	this.disastersDetails = disastersDetails;
}

public String getDetails() {
	return details;
}

public void setDetails(String details) {
	this.details = details;
}

public String getDisasterTypeId() {
	return disasterTypeId;
}

public void setDisasterTypeId(String disasterTypeId) {
	this.disasterTypeId = disasterTypeId;
}

public String getDistrict() {
	return district;
}

public void setDistrict(String district) {
	this.district = district;
}

public String getInLocation() {
	return inLocation;
}

public void setInLocation(String inLocation) {
	this.inLocation = inLocation;
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

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getReportOn() {
	return reportOn;
}

public void setReportOn(String reportOn) {
	this.reportOn = reportOn;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getOfficerContact() {
	return officerContact;
}

public void setOfficerContact(String officerContact) {
	this.officerContact = officerContact;
}

public String getOfficerId() {
	return officerId;
}

public void setOfficerId(String officerId) {
	this.officerId = officerId;
}

public String getOfficerName() {
	return officerName;
}

public void setOfficerName(String officerName) {
	this.officerName = officerName;
}

public String getZoneId() {
	return zoneId;
}

public void setZoneId(String zoneId) {
	this.zoneId = zoneId;
}

public String getOfficerZone() {
	return officerZone;
}

public void setOfficerZone(String officerZone) {
	this.officerZone = officerZone;
}
	
	
}
