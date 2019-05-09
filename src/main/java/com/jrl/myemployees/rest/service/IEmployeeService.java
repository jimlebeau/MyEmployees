package com.jrl.myemployees.rest.service;

import java.util.List;

import com.jrl.myemployees.rest.model.Employee;

public interface IEmployeeService {
	List<Employee> getAllEmployees();
	Employee getEmployeeById(int employeeId);
	Employee addEmployee(Employee employee);
	Employee updateEmployee(Employee employee);
	void deleteEmployee(int id);

}
