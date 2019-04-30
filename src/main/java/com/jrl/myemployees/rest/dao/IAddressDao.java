package com.jrl.myemployees.rest.dao;

import java.util.List;

import com.jrl.myemployees.rest.model.Address;

public interface IAddressDao {
	List<Address> getAllAddresses();
	Address getAddressById(int addressId);
	List<Address> getAddressesByEmployeeId(int employeeId);
	void addAddress(Address address);
	void updateAddress(Address address);
	void deleteAddress(int addressId);
	boolean addressExists(String streetAddress);

}
