package com.accolite.pizzeria.dao;

import java.util.List;

import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.model.Customer;

public interface CustomerDao {

	Customer findById(int id);

	void saveCustomer(Customer customer);
	
	void deleteCustomerByUsername(String custUserName);
	
	List<Customer> findAllCustomers();

	Customer findCustomerByUsername(String custUserName);
	
	List<Coupon> getAllCoupons(Customer customer);
}
