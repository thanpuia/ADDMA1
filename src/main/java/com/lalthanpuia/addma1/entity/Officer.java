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
	
	@Column(name="zonal_officer_contact")
	String officerContact;
	
	@Column(name="designation")
	String officerDesignation;
	
	@Column(name="district")
	String officerDistrict;
	
	@Column (name="email")
	String officerEmail;
	
	@Column (name="zone")
	String officerZone;
	
	@Column (name="zonal_officer_name")
	String officerName;
	
	@Column (name="officerid")
	String officerId;
	
	@Column (name="locality")
	String officerLocality;

	public Officer() {
	}

	public Officer(int zoneId, String officerContact, String officerDesignation, String officerDistrict,
			String officerEmail, String officerZone, String officerName, String officerId, String officerLocality) {
		this.zoneId = zoneId;
		this.officerContact = officerContact;
		this.officerDesignation = officerDesignation;
		this.officerDistrict = officerDistrict;
		this.officerEmail = officerEmail;
		this.officerZone = officerZone;
		this.officerName = officerName;
		this.officerId = officerId;
		this.officerLocality = officerLocality;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public String getOfficerContact() {
		return officerContact;
	}

	public void setOfficerContact(String officerContact) {
		this.officerContact = officerContact;
	}

	public String getOfficerDesignation() {
		return officerDesignation;
	}

	public void setOfficerDesignation(String officerDesignation) {
		this.officerDesignation = officerDesignation;
	}

	public String getOfficerDistrict() {
		return officerDistrict;
	}

	public void setOfficerDistrict(String officerDistrict) {
		this.officerDistrict = officerDistrict;
	}

	public String getOfficerEmail() {
		return officerEmail;
	}

	public void setOfficerEmail(String officerEmail) {
		this.officerEmail = officerEmail;
	}

	public String getOfficerZone() {
		return officerZone;
	}

	public void setOfficerZone(String officerZone) {
		this.officerZone = officerZone;
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

	public String getOfficerLocality() {
		return officerLocality;
	}

	public void setOfficerLocality(String officerLocality) {
		this.officerLocality = officerLocality;
	}

	
	
}
