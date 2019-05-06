package com.jrl.myemployees.rest.controller;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrl.myemployees.rest.model.Address;
import com.jrl.myemployees.rest.service.IAddressService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {

	ObjectMapper mapper = new ObjectMapper();
	JSONArray addressesJson = new JSONArray();

	private static Address address = new Address("street address", "city", "zip code", "state", 1, 1);
	List<Address> addresses = new ArrayList<>();
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private IAddressService service;

	@Before
	public void init() throws JSONException, JsonProcessingException {
		addresses.add(address);
		
	}

	@Test
	public void testGetAddressesSuccess() throws Exception {
		
		Mockito.when(service.getAllAddresses()).thenReturn(addresses);
		String actual = mapper.writeValueAsString(addresses);
		
		this.mvc.perform(get("/addresses/"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0]addressId", is(1)));
	}
	
	@Test
	public void testUpdateAddressSuccess() throws Exception {
		Mockito.when(service.updateAddress(any(Address.class))).thenReturn(Boolean.TRUE);
		String addressString = mapper.writeValueAsString(address);
		RequestBuilder request = MockMvcRequestBuilders
				.put("/addresses/")
				.content(addressString)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(request).andReturn();
				
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		

	}
	@Test
	public void testUpdateAddressNotModified() throws Exception {
		Mockito.when(service.updateAddress(any(Address.class))).thenReturn(Boolean.FALSE);
		String addresseString = mapper.writeValueAsString(address);
		System.out.println(addresseString);
		RequestBuilder request = MockMvcRequestBuilders
				.put("/addresses/")
				.content(addresseString)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(request).andReturn();
				
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.NOT_MODIFIED.value(), response.getStatus());
		
	}

	@Test
	public void testAddAddressSuccess() throws Exception {
		Mockito.when(service.addAddress(any(Address.class))).thenReturn(Boolean.TRUE);
		String actual = mapper.writeValueAsString(address);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/addresses/")
				.content(actual)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
	}
	
	@Test
	public void testAddAddressConflict() throws Exception {
		Mockito.when(service.addAddress(any(Address.class))).thenReturn(Boolean.FALSE);
		String actual = mapper.writeValueAsString(address);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/addresses/")
				.content(actual)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
		
	}
	
	@Test
	public void testDeleteAddressSuccess() throws Exception {
		int id = 1;
		Mockito.doNothing().when(service).deleteAddress(id);
		
		this.mvc.perform(delete("/addresses/1"))
		.andExpect(status().isNoContent());
	}

}
