package com.jrl.myemployees.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer employeeId;
	
	@NotEmpty(message = "first name must not be empty")
	private String firstName;
	
	@NotEmpty(message = "last name must not be empty")
	private String lastName;
	
	@NotEmpty(message = "email must not be emmpty")
	@Email(message = "email should be a valid email address")
	private String email;
	
	private BigDecimal cellPhone;
	
	@NotEmpty(message = "TaxId is required")
	@Size(min=9, max=9, message = "TaxId must be 9 characters")
	private String taxId;

	public Employee() {
		
	}
	
	public Employee(Integer employeeId, String firstName, String lastName, String email, BigDecimal cellPhone, String taxId) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.cellPhone = cellPhone;
		this.taxId = taxId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(BigDecimal cellPhone) {
		this.cellPhone = cellPhone;
	}
	
	public String getTaxId() {
		return taxId;
	}
	
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", cellPhone=" + cellPhone + ", taxId=" + taxId + "]";
	}
	
	
}
