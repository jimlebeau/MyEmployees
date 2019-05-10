package com.jrl.myemployees.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrl.myemployees.rest.dao.IEmployeeDao;
import com.jrl.myemployees.rest.model.Employee;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	private IEmployeeDao dao;
	
	public EmployeeService(IEmployeeDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Employee> getAllEmployees() {
		return dao.getAllEmployees();
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		Employee employee = dao.getEmployeeById(employeeId);
		return employee;
	}

	@Override
	public Employee addEmployee(Employee employee) {
		if (dao.employeeExists(employee.getEmployeeId())) {
			return null;
		} else {
			return dao.addEmployee(employee);
		}
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		if (dao.getEmployeeById(employee.getEmployeeId()) != null) {
			return dao.updateEmployee(employee);
		} else {
			return null;
		}
	}

	@Override
	public void deleteEmployee(int id) {
		dao.deleteEmployee(id);
		
	}

}
