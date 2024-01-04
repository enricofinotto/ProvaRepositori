package com.student.core;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@XmlRootElement
public class Student {
 
	private long id;
 
	private String firstName;
	 
	private String surname;
 
	private String dept;
	 
     private Double fees;
     
     private long id_college;
     
     public long getId_college() {
		return id_college;
	}

	public void setId_college(long id_college) {
		this.id_college = id_college;
	}

	private College college;
	
	public Student() {
		super();
	}
	 
	public Student(long id, String firstName, String surname, String dept, Double fees, long id_college) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.dept = dept;
		this.fees = fees;
		this.id_college = id_college;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Double getFees() {
		return fees;
	}
	public void setFees(Double fees) {
		this.fees = fees;
	}
	
	
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", surname=" + surname + ", dept=" + dept + ", fees="
				+ fees + "]";
	}
	
	
}
