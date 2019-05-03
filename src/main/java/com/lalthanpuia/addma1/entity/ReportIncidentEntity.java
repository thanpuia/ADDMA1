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
public class ReportIncidentEntity {

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
	String zonalOfficerContact;
	
	@Column(name="zonal_officer_id")
	String zonalOfficerId;
	
	@Column(name="zonal_officer_name")
	String zonalOfficerName;
	
	@Column(name="zone_id")
	String zoneId;
	
	@Column(name="zone_name")
	String zoneName;
	
//	@ManyToOne
//	@JoinColumn(name="username")
//	@JsonBackReference
//	private UserEntity userEntity;

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

	public String getZonalOfficerContact() {
		return zonalOfficerContact;
	}

	public void setZonalOfficerContact(String zonalOfficerContact) {
		this.zonalOfficerContact = zonalOfficerContact;
	}

	public String getZonalOfficerId() {
		return zonalOfficerId;
	}

	public void setZonalOfficerId(String zonalOfficerId) {
		this.zonalOfficerId = zonalOfficerId;
	}

	public String getZonalOfficerName() {
		return zonalOfficerName;
	}

	public void setZonalOfficerName(String zonalOfficerName) {
		this.zonalOfficerName = zonalOfficerName;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public ReportIncidentEntity(int serialNumber, String disasterType, String locality, String landmarks,
			String disastersDetails, String details, String disasterTypeId, String district, String inLocation,
			String lat, String lng, String location, String username, String phone, String reportOn, String status,
			String userId, String zonalOfficerContact, String zonalOfficerId, String zonalOfficerName, String zoneId,
			String zoneName) {
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
		this.zonalOfficerContact = zonalOfficerContact;
		this.zonalOfficerId = zonalOfficerId;
		this.zonalOfficerName = zonalOfficerName;
		this.zoneId = zoneId;
		this.zoneName = zoneName;
	}

	public ReportIncidentEntity() {
	}
	
	
}
