package com.jrl.myemployees.rest.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.jrl.myemployees.rest.dao.IEmployeeDao;
import com.jrl.myemployees.rest.model.Employee;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	private IEmployeeDao dao;
	
	private EmployeeService service;
	
	@Before
	public void setUp() {
		dao = Mockito.mock(IEmployeeDao.class);
		service = new EmployeeService(dao);
	}
	
	@Test
	public void getAllEmployeesSuccess() {
		Employee emp1 = new Employee(1, "first1", "last1", "email1", BigDecimal.valueOf(1111111));
		Employee emp2 = new Employee(2, "first2", "last2", "email2", BigDecimal.valueOf(2222222));
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(emp1);
		employeeList.add(emp2);
		
		Mockito.when(dao.getAllEmployees()).thenReturn(employeeList);
		
		assertThat(service.getAllEmployees(), hasSize(2));
	}
	
	@Test
	public void getEmployeeByIdSuccess() {
		Employee emp1 = new Employee(1, "first1", "last1", "email1", BigDecimal.valueOf(1111111));
		Mockito.when(dao.getEmployeeById(1)).thenReturn(emp1);
		Employee employee = service.getEmployeeById(1);
		assertThat(employee.getEmployeeId(), equalTo(1));
	}
	
	@Test
	public void addEmployeeSuccess() {
		Employee emp1 = new Employee(1, "first1", "last1", "email1", BigDecimal.valueOf(1111111));
		
		Mockito.when(dao.employeeExists(emp1.getLastName())).thenReturn(Boolean.FALSE);
		Mockito.when(dao.addEmployee(emp1)).thenReturn(emp1);
		
		Employee result = service.addEmployee(emp1);
		
		assertThat(result.getEmployeeId(), equalTo(1));
		
	}
	
	@Test
	public void addEmployeeFail() {
		Employee emp1 = new Employee(1, "first1", "last1", "email1", BigDecimal.valueOf(1111111));
		Mockito.when(dao.employeeExists(emp1.getLastName())).thenReturn(Boolean.TRUE);
		Employee result = service.addEmployee(emp1);
		assertNull(result);
	}
}
