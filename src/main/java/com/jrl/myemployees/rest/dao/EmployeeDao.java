package com.jrl.myemployees.rest.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jrl.myemployees.rest.model.Employee;
import com.jrl.myemployees.rest.model.EmployeeRowMapper;

@Transactional
@Repository
public class EmployeeDao implements IEmployeeDao {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);
	private static final String GETALLEMPLOYEES = "select EmployeeId, FirstName, LastName, Email, CellPhone from Emmployees";
	private static final String GETEMPLOYEEBYID = "select EmployeeId, FirstName, LastName, Email, CellPhone from Emmployees where EmployeeId = ?";
	private static final String INSERTEMPLOYEE = "INSERT INTO Employees (FirstName, LastName, Email, CellPhone) values (?, ?, ?, ?)";
	private static final String GETEMPLOYEEBYLASTNAME = "SELECT EmployeeId FROM Employees WHERE LastName = ?";
	private static final String UPDATEEMPLOYEE = "UPDATE Employees SET FirstName=?, LastName=?, Email=?, CellPhone=? WHERE EmployeeId=?";
	private static final String DELETEEMPLOYEE = "DELETE FROM Employees WHERE EmployeeId=?";
	private static final String EMPLOYEEEXSITS = "SELECT count(*) FROM Employees WHERE LastName = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Employee> getAllEmployees() {
		RowMapper<Employee> rowMapper = new EmployeeRowMapper();
		return this.jdbcTemplate.query(GETALLEMPLOYEES, rowMapper);
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(GETEMPLOYEEBYID, rowMapper, employeeId);
		return employee;
	}

	@Override
	public void addEmployee(Employee employee) {
		jdbcTemplate.update(INSERTEMPLOYEE, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getCellPhone());
		int employeeId = jdbcTemplate.queryForObject(GETEMPLOYEEBYLASTNAME,  Integer.class, employee.getLastName());
		
		employee.setEmployeeId(employeeId);
		
	}

	@Override
	public void updateEmployee(Employee employee) {
		jdbcTemplate.update(UPDATEEMPLOYEE, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getCellPhone(), employee.getEmployeeId());
		
	}

	@Override
	public void deleteEmployee(int employeeId) {
		jdbcTemplate.update(DELETEEMPLOYEE, employeeId);		
		
	}

	@Override
	public boolean employeeExists(String lastName) {
		int count = jdbcTemplate.queryForObject(EMPLOYEEEXSITS, Integer.class, lastName);
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

}
