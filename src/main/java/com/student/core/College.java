package com.student.core;

public class College {
	
 
	private long id;
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	private String name;
	private String street;
	private String city;
	private String state;
 
	public College() {
		super();
	}
 
	 

	public College(long id, String name, String street, String city, String state) {
		super();
		this.id = id;
		this.name = name;
		this.street = street;
		this.city = city;
		this.state = state;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}



	@Override
	public String toString() {
		return "College [name=" + name + ", street=" + street + ", city=" + city + ", state=" + state + "]";
	}
	 
}
