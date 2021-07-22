package com.extramarks.springboot.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
public class Employee {	
	
	@Id	
	private String id;
	private String firstName;
	private String lastName;
	private String gender;
	private String mobile;
	private String state;
	private String emailId;
	private Long status;
	private String image;
	
	@DBRef 
    private List<Skills> skills;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public List<Skills> getSkills() {
		return skills;
	}
	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}
	public Employee(String id, String firstName, String lastName, String gender, String state, String emailId,
			Long status, List<Skills> skills, String mobile, String image) {
		
		super();
		this.id = id;
		this.mobile = mobile;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.state = state;
		this.emailId = emailId;
		this.status = status;
		this.skills = skills;
		this.image = image;
	}
	
	public Employee() {
		super();
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}	
}
