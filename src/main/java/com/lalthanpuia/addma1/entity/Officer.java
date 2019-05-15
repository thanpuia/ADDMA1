package com.lalthanpuia.addma1.entity;
/*
 * HOW THE ZONAL OFFICER IS FETCH:
 *  Currently the zonal officer is fetch according to the localtiy.Each locatity in every district
 *  have a unique value. the value is similar to the zone name. We take the value of the localilty
 *  and using its value we make a query in the zone table.
 * 
 */


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="zonalofficer")
public class Officer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	int zoneId;
	
	@Column(name="contact")
	String contact;
	
	@Column(name="designation")
	String designation;
	
	@Column(name="district")
	String district;
	
	@Column (name="email")
	String email;
	
	@Column (name="zone")
	String zone;
	
	@Column (name="officername")
	String officerName;
	
	@Column (name="officerid")
	String officerId;
	
	@Column (name="locality")
	String locality;

	public Officer() {
	}

	

	public Officer(String contact, String designation, String district, String email, String zone, String officerName,
			String officerId, String locality) {
		this.contact = contact;
		this.designation = designation;
		this.district = district;
		this.email = email;
		this.zone = zone;
		this.officerName = officerName;
		this.officerId = officerId;
		this.locality = locality;
	}



	public String getLocality() {
		return locality;
	}



	public void setLocality(String locality) {
		this.locality = locality;
	}



	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getOfficerName() {
		return officerName;
	}

	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}

	public String getOfficerId() {
		return officerId;
	}

	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}

	
	
	
}
