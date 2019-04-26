package com.lalthanpuia.addma1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="requestrelief")
public class RequestReliefMaterialEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="serial_number")
	int serialNumber;
	
	@Column(name="details")
	String details;
	
	@Column(name="district")
	String district;
	
	@Column(name="landmarks")
	String landmarks;
	
	@Column(name="lat")
	String lat;
	
	@Column(name="lng")
	String lng;
	
	@Column(name="locality")
	String locality;
	
	@Column(name="material")
	String material;
	
	@Column(name="material_id")
	String materialId;
	
	@Column(name="username")
	String username;
	
	@Column(name="phone")
	String phone;
	
	@Column(name="quantity")
	String quantity;
	
	@Column(name="request_on")
	String requestOn;
	
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


	public RequestReliefMaterialEntity() {
	}


	public RequestReliefMaterialEntity(int serialNumber, String details, String district, String landmarks, String lat,
			String lng, String locality, String material, String materialId, String username, String phone,
			String quantity, String requestOn, String status, String userId, String zonalOfficerContact,
			String zonalOfficerId, String zonalOfficerName, String zoneId, String zoneName) {
		this.serialNumber = serialNumber;
		this.details = details;
		this.district = district;
		this.landmarks = landmarks;
		this.lat = lat;
		this.lng = lng;
		this.locality = locality;
		this.material = material;
		this.materialId = materialId;
		this.username = username;
		this.phone = phone;
		this.quantity = quantity;
		this.requestOn = requestOn;
		this.status = status;
		this.userId = userId;
		this.zonalOfficerContact = zonalOfficerContact;
		this.zonalOfficerId = zonalOfficerId;
		this.zonalOfficerName = zonalOfficerName;
		this.zoneId = zoneId;
		this.zoneName = zoneName;
	}


	public int getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getLandmarks() {
		return landmarks;
	}


	public void setLandmarks(String landmarks) {
		this.landmarks = landmarks;
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


	public String getMaterial() {
		return material;
	}


	public void setMaterial(String material) {
		this.material = material;
	}


	public String getMaterialId() {
		return materialId;
	}


	public void setMaterialId(String materialId) {
		this.materialId = materialId;
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


	public String getQuantity() {
		return quantity;
	}


	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}


	public String getRequestOn() {
		return requestOn;
	}


	public void setRequestOn(String requestOn) {
		this.requestOn = requestOn;
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
	
	
	
}
