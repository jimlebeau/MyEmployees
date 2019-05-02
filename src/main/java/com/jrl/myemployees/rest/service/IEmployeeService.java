package com.jrl.myemployees.rest.service;

import java.util.List;

import com.jrl.myemployees.rest.model.Employee;

public interface IEmployeeService {
	List<Employee> getAllEmployees();
	Employee getEmployeeById(int employeeId);
	boolean addEmployee(Employee employee);
	boolean updateEmployee(Employee employee);
	void deleteEmployee(int id);

}
