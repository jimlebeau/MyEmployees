package com.jrl.myemployees.rest.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
	private static final String GETALLEMPLOYEES = "select EmployeeId, FirstName, LastName, Email, CellPhone, TaxId from Employees";
	private static final String GETEMPLOYEEBYID = "select EmployeeId, FirstName, LastName, Email, CellPhone, TaxId from Employees where EmployeeId = ?";
	private static final String INSERTEMPLOYEE = "INSERT INTO Employees (FirstName, LastName, Email, CellPhone, TaxId) values (?, ?, ?, ?, ?)";
	private static final String GETEMPLOYEEBYTAXID = "SELECT EmployeeId FROM Employees WHERE TaxId = ?";
	private static final String UPDATEEMPLOYEE = "UPDATE Employees SET FirstName=?, LastName=?, Email=?, CellPhone=?, TaxId=? WHERE EmployeeId=?";
	private static final String DELETEEMPLOYEE = "DELETE FROM Employees WHERE EmployeeId=?";
	private static final String EMPLOYEEEXSITS = "SELECT count(*) FROM Employees WHERE EmployeeId = ?";
	private static final String TAXIDEXIST = "SELECT count(*) FROM Employees WHERE TaxId = ?";
	
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
		Employee employee = null;

		try {
			employee = jdbcTemplate.queryForObject(GETEMPLOYEEBYID, rowMapper, employeeId);
		} catch (EmptyResultDataAccessException ex) {
			
		}
		return employee;
	}

	@Override
	public Employee addEmployee(Employee employee) {
		
			int employeeId = getEmployeeIdByTaxId(employee.getTaxId());
			if (employeeId != 0) {
				// tax id already exits
				return null;
			} else {
				jdbcTemplate.update(INSERTEMPLOYEE, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getCellPhone(), employee.getTaxId());
				employeeId = getEmployeeIdByTaxId(employee.getTaxId());
				employee.setEmployeeId(employeeId);
				return employee;
			}
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		Employee updatedEmployee = null;
		if (employeeExists(employee.getEmployeeId())) {
			// employee record does not exist
			if (!taxIdExist(employee.getTaxId())) {
				// employee taxId does not already exist
				jdbcTemplate.update(UPDATEEMPLOYEE, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getCellPhone(), employee.getTaxId(), employee.getEmployeeId());
				updatedEmployee = getEmployeeById(employee.getEmployeeId());
				
			} else {
				int checkEmployee = getEmployeeIdByTaxId(employee.getTaxId());
				if (employee.getEmployeeId() == checkEmployee) {
					jdbcTemplate.update(UPDATEEMPLOYEE, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getCellPhone(), employee.getTaxId(), employee.getEmployeeId());
					updatedEmployee = getEmployeeById(employee.getEmployeeId());					
				}				
			}
		}
		return updatedEmployee;
	}

	@Override
	public void deleteEmployee(int employeeId) {
		jdbcTemplate.update(DELETEEMPLOYEE, employeeId);		
		
	}

	@Override
	public boolean employeeExists(int employeeId) {
		int count = jdbcTemplate.queryForObject(EMPLOYEEEXSITS, Integer.class, employeeId);
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean taxIdExist(String taxId) {
		
		try {
			int count = jdbcTemplate.queryForObject(TAXIDEXIST, Integer.class, taxId);
			if (count == 0) {
				return false;
			} else {
				return true;
			}
		} catch (EmptyResultDataAccessException ex) {
			return false;
		}
	}
	
	@Override
	public int getEmployeeIdByTaxId(String taxId) {

		try {
			return jdbcTemplate.queryForObject(GETEMPLOYEEBYTAXID,  Integer.class, taxId);
			
		} catch(EmptyResultDataAccessException ex) {
			return 0;
		} catch (IncorrectResultSizeDataAccessException ex) {
			return 0;
		}
		
	}

}
