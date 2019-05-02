package com.jrl.myemployees.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.jrl.myemployees.rest.model.History;
import com.jrl.myemployees.rest.service.IHistoryService;

@RestController
@RequestMapping(path = "/history")
public class HistoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

	@Autowired
	private IHistoryService service;
	
	@GetMapping(path  = "/", produces = "application/json")
	public ResponseEntity<List<History>> getAllHistorys() {
		List<History> list = service.getAllHistory();
		return new ResponseEntity<List<History>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<History> getHistoryById(@PathVariable("id") int id) {
//		logger.debug("in mapping /history/{" + id + "}");
		History history = service.getHistoryById(id);
		
		return new ResponseEntity<History>(history, HttpStatus.OK);
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<Void> addHistory (@Valid @RequestBody History history, UriComponentsBuilder builder) {
		service.addHistory(history);
		boolean flag = service.addHistory(history);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("historys/{id}").buildAndExpand(history.getHistoryId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}
	}
	
	@PutMapping(value = "/")
	public ResponseEntity<History> updateHistory (@Valid @RequestBody History history) {
		
		boolean flag = service.updateHistory(history);
		if (flag == Boolean.FALSE) {
			return new ResponseEntity<History>(HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<History>(history, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteHistory(@PathVariable("id") Integer id) {
		service.deleteHistory(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
