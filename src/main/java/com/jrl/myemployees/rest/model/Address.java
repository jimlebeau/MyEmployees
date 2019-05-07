package com.jrl.myemployees.rest.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class Address implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "street address can not be empty")
	private String streetAddress;
	
	private String city;
	
	@NotEmpty(message = "zip code can not be empty")
	private String zipCode;
	
	private String state;
	
	private Integer employeeId;
	
	private Integer addressId;
	
	public Address() {
		
	}

	public Address(String streetAddress, String city, String zipCode, String state, Integer employeeId, Integer addressId) {
		super();
		this.streetAddress = streetAddress;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
		this.employeeId = employeeId;
		this.addressId = addressId;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getAddressId() {
		return addressId;
	}
	
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	
	@Override
	public String toString() {
		return "Address [streetAddress=" + streetAddress + ", city=" + city + ", zipCode=" + zipCode + ", state="
				+ state + ", employeeId=" + employeeId + ", addressId=" + addressId + "]";
	}

}
