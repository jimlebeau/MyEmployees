package com.jrl.myemployees.rest.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.jrl.myemployees.rest.model.Employee;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class EmployeeDaoTest {

	@Autowired
	private EmployeeDao dao;
	
	private Employee emp1;
	private Employee emp2;
	
	@Before
	public void setUp() {
		emp1 = new Employee(1, "firstName1", "lastName1", "email1", BigDecimal.valueOf(1111111), "111223333");		
		emp2 = new Employee(2, "firstName2", "lastName2", "email2", BigDecimal.valueOf(2222222), "222334444");
	}

	@Test
	public void constructorTest() throws Exception {
		dao = new EmployeeDao();
		assertThat(dao, notNullValue());
	}

	@Test
	public void add_shouldCreateEmployee_AndGetById() {
		Employee result = dao.addEmployee(emp1);
		
		assertThat(result.getFirstName(), equalTo(emp1.getFirstName()));
		assertThat(result.getCellPhone(), equalTo(emp1.getCellPhone()));
		
	}
	
	@Test
	public void add_shouldReturnNull_WhenEmployeeExist() {
		Employee result = dao.addEmployee(emp1);
		
		result = dao.addEmployee(emp1);
		assertNull(result);
	}

	@Test
	public void shouldGetEmployeeId() {
		Employee result = dao.addEmployee(emp1);
		int employeeId = dao.getEmployeeId(result.getTaxId());
		assertThat(employeeId, equalTo(emp1.getEmployeeId()));
		
	}
	
	@Test
	public void shouldNotGetEmployeeId() {
		Employee result = dao.addEmployee(emp1);
		int employeeId = dao.getEmployeeId("bogus");
		assertThat(employeeId, equalTo(0));
		
	}
	
	@Test
	public void getAllEmployees_shouldReturnEmployees() {
		dao.addEmployee(emp1);
		dao.addEmployee(emp2);
		
		List<Employee> employees = dao.getAllEmployees();
		assertThat(employees, hasSize(2));
		
	}

	@Test
	public void updateEmployee_shouldUpdateEmployee() {
		String newEmail = "newEmail.com";
		dao.addEmployee(emp1);
		emp1.setEmail(newEmail);
		Employee result = dao.updateEmployee(emp1);
		
		assertThat(result.getEmail(), equalTo(emp1.getEmail()));
		
	}
	
	@Test
	public void updateEmployee_shouldNotUpdate() {
		Employee result =  dao.updateEmployee(emp1);
		assertNull(result);
	}
	
	@Test
	public void deleteEmployee_shouldDeleteEmployee() {
		dao.addEmployee(emp1);
		dao.addEmployee(emp2);
		dao.deleteEmployee(emp1.getEmployeeId());
		assertThat(dao.employeeExists(emp1.getTaxId()), equalTo(Boolean.FALSE));
		
	}
	
	@Test
	public void employeeExists_shouldReturnTrue() {
		dao.addEmployee(emp1);
		assertThat(dao.employeeExists(emp1.getTaxId()), equalTo(Boolean.TRUE));
		
	}

	@Test
	public void employeeExist_shouldReturnFalse() {
		dao.addEmployee(emp1);
		assertThat(dao.employeeExists(emp2.getTaxId()), equalTo(Boolean.FALSE));
	}
}
