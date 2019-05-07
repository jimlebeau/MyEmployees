package com.jrl.myemployees.rest.dao;

import java.util.List;

import com.jrl.myemployees.rest.model.Employee;

public interface IEmployeeDao {
	List<Employee> getAllEmployees();
	Employee getEmployeeById(int employeeId);
	Employee addEmployee(Employee employee);
	void updateEmployee(Employee employee);
	void deleteEmployee(int employeeId);
	boolean employeeExists(String lastName);

}
