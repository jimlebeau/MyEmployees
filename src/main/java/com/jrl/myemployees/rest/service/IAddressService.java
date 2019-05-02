package com.jrl.myemployees.rest.service;

import java.util.List;

import com.jrl.myemployees.rest.model.Address;

public interface IAddressService {
	List<Address> getAllAddresses();
	Address getAddressById(int addressId);
	List<Address> getAddressByEmployeeId(int employeeId);
	boolean addAddress(Address address);
	boolean updateAddress(Address address);
	void deleteAddress(int addressId);

}
