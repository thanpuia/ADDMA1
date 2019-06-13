package com.lalthanpuia.addma1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="globalnotification")
public class GlobalNotification {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="serial_number")
	int serialNumber;
	
	@Column(name="name")
	String name;
	
	@Column(name="designation")
	String designation;
	
	@Column(name="subject")
	String subject;
	
	@Column(name="body")
	String body;
	
	@Column(name="report_on")
	String reportOn;
	
	@Column(name="extra")
	String extra;

	public GlobalNotification() {
	}

	public GlobalNotification(int serialNumber, String name, String designation, String subject, String body,
			String reportOn, String extra) {
		this.serialNumber = serialNumber;
		this.name = name;
		this.designation = designation;
		this.subject = subject;
		this.body = body;
		this.reportOn = reportOn;
		this.extra = extra;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getReportOn() {
		return reportOn;
	}

	public void setReportOn(String reportOn) {
		this.reportOn = reportOn;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	
}
