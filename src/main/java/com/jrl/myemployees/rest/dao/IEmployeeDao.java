package com.jrl.myemployees.rest.dao;

import java.util.List;

import com.jrl.myemployees.rest.model.Employee;

public interface IEmployeeDao {
	List<Employee> getAllEmployees();
	Employee getEmployeeById(int employeeId);
	Employee addEmployee(Employee employee);
	Employee updateEmployee(Employee employee);
	void deleteEmployee(int employeeId);
	boolean employeeExists(int employeeId);
	int getEmployeeIdByTaxId(String employeeTaxId);
	boolean taxIdExist(String taxId);

}
