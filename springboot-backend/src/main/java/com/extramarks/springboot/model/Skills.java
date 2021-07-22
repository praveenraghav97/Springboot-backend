package com.extramarks.springboot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "skills")
public class Skills {
	
	@Id
	private String id;
	
	private String name;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Skills(String name) {
		super();
		this.name = name;
	}

	public Skills() {
		super();		
	}	
}
