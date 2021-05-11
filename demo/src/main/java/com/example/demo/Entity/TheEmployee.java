package com.example.demo.Entity;

import javax.persistence.Entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class TheEmployee {
	
	private String short_description;
	private String number;
	private String assigned_to;
	private String resolution_code;
	
	public String getResolution_code() {
		return resolution_code;
	}
	public void setResolution_code(String resolution_code) {
		this.resolution_code = resolution_code;
	}
	public String getAssigned_to() {
		return assigned_to;
	}
	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}
	public String getShort_description() {
		return short_description;
	}
	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	

}
