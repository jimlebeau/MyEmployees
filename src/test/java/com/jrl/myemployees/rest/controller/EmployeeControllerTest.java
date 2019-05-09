package com.jrl.myemployees.rest.controller;

import org.hamcrest.core.Is;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;import com.jrl.myemployees.MyEmployeesApplication;
import com.jrl.myemployees.rest.controller.EmployeeController;
import com.jrl.myemployees.rest.model.Employee;
import com.jrl.myemployees.rest.service.IEmployeeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
//@ContextConfiguration(classes= {MyEmployeesApplication.class})
//@SpringBootTest(classes=MyEmployeesApplication.class)
public class EmployeeControllerTest {

	ObjectMapper mapper = new ObjectMapper();
	JSONArray employeesJson = new JSONArray();

	private static Employee employee = new Employee(1, "firstName1", "lastName1", "email1@email.com", BigDecimal.valueOf(1111111), "111223333");
	List<Employee> employees = new ArrayList<>();
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private IEmployeeService service;

	@Before
	public void init() throws JSONException, JsonProcessingException {
		employees.add(employee);
		
	}

	@Test
	public void testGetEmployeeListSuccess() throws Exception {
		
		Mockito.when(service.getAllEmployees()).thenReturn(employees);

		String actual = mapper.writeValueAsString(employees);
		System.out.println(employees);
		System.out.println(actual);
		
		this.mvc.perform(get("/employees/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].employeeId", is(1)));
		
	}

	@Test 
	public void testGetEmployeeByIdSuccess() throws Exception {
		
		Mockito.when(service.getEmployeeById(1)).thenReturn(employee);
		
		this.mvc.perform(get("/employees/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.employeeId", is(1)));

	}
	
	@Test
	public void testUpdateEmployeeSuccess() throws Exception {
		Mockito.when(service.updateEmployee(any(Employee.class))).thenReturn(employee);
		String employeeString = mapper.writeValueAsString(employee);
		System.out.println(employeeString);
		RequestBuilder request = MockMvcRequestBuilders
				.put("/employees/")
				.content(employeeString)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(request).andReturn();
				
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	public void testUpdateEmployeeNotModified() throws Exception {
		Mockito.when(service.updateEmployee(any(Employee.class))).thenReturn(null);
		String employeeString = mapper.writeValueAsString(employee);
		
		RequestBuilder request = MockMvcRequestBuilders
				.put("/employees/")
				.content(employeeString)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(request).andReturn();
				
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.NOT_MODIFIED.value(), response.getStatus());
		
	}

	@Test
	public void testAddEmployeeSuccess() throws Exception {
		Mockito.when(service.addEmployee(any(Employee.class))).thenReturn(employee);
		String actual = mapper.writeValueAsString(employee);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/employees/")
				.content(actual)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
	}
	
	@Test
	public void testAddEmployeeConflict() throws Exception {
		Mockito.when(service.addEmployee(any(Employee.class))).thenReturn(null);
		String actual = mapper.writeValueAsString(employee);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/employees/")
				.content(actual)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
		
	}
	
	@Test
	public void testDeleteEmployeeSuccess() throws Exception {
		int id = 1;
		Mockito.doNothing().when(service).deleteEmployee(id);
		
		this.mvc.perform(delete("/employees/1"))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void whenPostRequestToEmployeesAndIvalidEmployee() throws Exception {
		employee.setLastName("");;
		Mockito.when(service.addEmployee(any(Employee.class))).thenReturn(employee);
		String employeeJson = mapper.writeValueAsString(employee);
		System.out.println(employeeJson);
		
		this.mvc.perform(MockMvcRequestBuilders.post("/employees/")
				.content(employeeJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]", Is.is("last name must not be empty")))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

}
