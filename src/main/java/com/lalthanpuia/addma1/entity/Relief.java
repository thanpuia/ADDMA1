
package com.lalthanpuia.addma1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="requestrelief")
public class Relief {

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
	String officerContact;
	
	@Column(name="zonal_officer_id")
	String officerId;
	
	@Column(name="zonal_officer_name")
	String officerName;
	
	@Column(name="zone_id")
	String zoneId;
	
	@Column(name="zone_name")
	String officerZone;


	public Relief() {
	}


	public Relief(int serialNumber, String details, String district, String landmarks, String lat, String lng,
			String locality, String material, String materialId, String username, String phone, String quantity,
			String requestOn, String status, String userId, String officerContact, String officerId, String officerName,
			String zoneId, String officerZone) {
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
