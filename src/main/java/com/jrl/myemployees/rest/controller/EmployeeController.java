package com.jrl.myemployees.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jrl.myemployees.rest.exception.RecordAlreadyExistException;
import com.jrl.myemployees.rest.exception.RecordDoesNotExistException;
import com.jrl.myemployees.rest.exception.RecordNotFoundException;
import com.jrl.myemployees.rest.model.Employee;
import com.jrl.myemployees.rest.service.IEmployeeService;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private IEmployeeService service;
	
	@GetMapping(path  = "/", produces = "application/json")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> list = service.getAllEmployees();
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
//		logger.debug("in mapping /employees/{" + id + "}");
		Employee employee = service.getEmployeeById(id);
		if (employee == null) {
			throw new RecordNotFoundException("Invalid employee id " + id);
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<Employee> addEmployee (@Valid @RequestBody Employee employee, UriComponentsBuilder builder) {
		Employee addedEmployee = service.addEmployee(employee);
		if (addedEmployee == null) {
			throw new RecordAlreadyExistException("Employee record already exits - " + employee.toString());
		} 
		return new ResponseEntity<Employee>(addedEmployee, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/")
	public ResponseEntity<Employee> updateEmployee (@Valid @RequestBody Employee employee) {
		Employee updatedEmployee = service.updateEmployee(employee);
		if (updatedEmployee == null) {
			throw new RecordDoesNotExistException("Employee record does not exist - " + employee.toString());
		}
		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Integer id) {
		service.deleteEmployee(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
