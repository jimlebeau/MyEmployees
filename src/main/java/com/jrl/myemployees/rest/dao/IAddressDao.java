package com.jrl.myemployees.rest.dao;

import java.util.List;

import com.jrl.myemployees.rest.model.Address;

public interface IAddressDao {
	List<Address> getAllAddresses();
	Address getAddressById(int addressId);
	List<Address> getAddressesByEmployeeId(int employeeId);
	int getAddressIdByAddress(int employeeId, String streetAddress, String city, String zipCode);
	Address addAddress(Address address);
	Address updateAddress(Address address);
	void deleteAddress(int addressId);
	boolean addressExists(int addressId);

}
