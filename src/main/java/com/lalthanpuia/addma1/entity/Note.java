//THIS ENTITIY IS MAINLY FOR SENDING ADDITIONAL INFORMATION ACRROSS

package com.lalthanpuia.addma1.entity;
	
public class Note {

	String Subject;
	String body1;
	String body2;
	String body3;
	String body4;
	String body5;
	
	
	public Note() {
	}


	public Note(String subject, String body1, String body2, String body3, String body4, String body5) {
		Subject = subject;
		this.body1 = body1;
		this.body2 = body2;
		this.body3 = body3;
		this.body4 = body4;
		this.body5 = body5;
	}


	public String getSubject() {
		return Subject;
	}


	public void setSubject(String subject) {
		Subject = subject;
	}


	public String getBody1() {
		return body1;
	}


	public void setBody1(String body1) {
		this.body1 = body1;
	}


	public String getBody2() {
		return body2;
	}


	public void setBody2(String body2) {
		this.body2 = body2;
	}


	public String getBody3() {
		return body3;
	}


	public void setBody3(String body3) {
		this.body3 = body3;
	}


	public String getBody4() {
		return body4;
	}


	public void setBody4(String body4) {
		this.body4 = body4;
	}


	public String getBody5() {
		return body5;
	}


	public void setBody5(String body5) {
		this.body5 = body5;
	}
	
	
}
