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

import com.jrl.myemployees.rest.exception.RecordAlreadyExistException;
import com.jrl.myemployees.rest.exception.RecordDoesNotExistException;
import com.jrl.myemployees.rest.model.Address;
import com.jrl.myemployees.rest.service.IAddressService;

@RestController
@RequestMapping(path = "/addresses")
public class AddressController {
	
	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

	@Autowired
	private IAddressService service;
	
	@GetMapping(path  = "/", produces = "application/json")
	public ResponseEntity<List<Address>> getAllAddresss() {
		List<Address> list = service.getAllAddresses();
		return new ResponseEntity<List<Address>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Address> getAddressById(@PathVariable("id") int id) {
//		logger.debug("in mapping /address/{" + id + "}");
		Address address = service.getAddressById(id);
		
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<Address> addAddress (@Valid @RequestBody Address address, UriComponentsBuilder builder) {
		Address addedAddress = service.addAddress(address);
		if (addedAddress == null) {
			throw new RecordAlreadyExistException("Address already exist or Employee Id does not exist" + address.toString());
		}
		return new ResponseEntity<Address>(addedAddress, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/")
	public ResponseEntity<Address> updateAddress (@Valid @RequestBody Address address) {
		Address updatedAddress = service.updateAddress(address);
		if (updatedAddress == null) {
			throw new RecordDoesNotExistException("Address does not exist or Employee Id does not exist " + address.toString());
		}
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAddress(@PathVariable("id") Integer id) {
		service.deleteAddress(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
