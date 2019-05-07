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
import com.jrl.myemployees.rest.model.History;
import com.jrl.myemployees.rest.service.IHistoryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(HistoryController.class)
public class HistoryControllerTest {

	ObjectMapper mapper = new ObjectMapper();
	JSONArray historiesJson = new JSONArray();

	private static LocalDate testStartDate;
	private static LocalDate testEndDate;
	private String startDateString;
	private String endDateString;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private static History history;
	List<History> histories = new ArrayList<>();
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private IHistoryService service;

	@Before
	public void init() throws JSONException, JsonProcessingException {
		endDateString = "2019-06-30";
		startDateString = "2019-03-01";
		testStartDate = LocalDate.parse(startDateString, formatter);
		testEndDate = LocalDate.parse(endDateString, formatter);
		history  = new History("job description", testStartDate, testEndDate, 1, 1);
		histories.add(history);
	}

	@Test
	public void testGetHistoryesSuccess() throws Exception {
		
		Mockito.when(service.getAllHistory()).thenReturn(histories);
		String actual = mapper.writeValueAsString(histories);
		
		this.mvc.perform(get("/history/"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0]historyId", is(1)));
	}
	
	@Test
	public void testUpdateHistorySuccess() throws Exception {
		Mockito.when(service.updateHistory(any(History.class))).thenReturn(Boolean.TRUE);
		String historyString = mapper.writeValueAsString(history);
		RequestBuilder request = MockMvcRequestBuilders
				.put("/history/")
				.content(historyString)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(request).andReturn();
				
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		

	}
	@Test
	public void testUpdateHistoryNotModified() throws Exception {
		Mockito.when(service.updateHistory(any(History.class))).thenReturn(Boolean.FALSE);
		String historyString = mapper.writeValueAsString(history);
		System.out.println(history.toString());
		System.out.println(historyString);
		RequestBuilder request = MockMvcRequestBuilders
				.put("/history/")
				.content(historyString)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(request).andReturn();
				
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.NOT_MODIFIED.value(), response.getStatus());
		
	}

	@Test
	public void testAddHistorySuccess() throws Exception {
		Mockito.when(service.addHistory(any(History.class))).thenReturn(Boolean.TRUE);
		String actual = mapper.writeValueAsString(history);
		System.out.println(actual);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/history/")
				.content(actual)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
	}
	
	@Test
	public void testAddHistoryConflict() throws Exception {
		Mockito.when(service.addHistory(any(History.class))).thenReturn(Boolean.FALSE);
		String actual = mapper.writeValueAsString(history);
		System.out.println(actual);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/history/")
				.content(actual)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
		
	}
	
	@Test
	public void testDeleteHistorySuccess() throws Exception {
		int id = 1;
		Mockito.doNothing().when(service).deleteHistory(id);
		
		this.mvc.perform(delete("/history/1"))
		.andExpect(status().isNoContent());
	}

}
