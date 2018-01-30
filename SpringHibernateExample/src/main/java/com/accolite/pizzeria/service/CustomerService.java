package com.accolite.pizzeria.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.dao.CartDao;
import com.accolite.pizzeria.dao.CustomerDao;
import com.accolite.pizzeria.model.Cart;
import com.accolite.pizzeria.model.Customer;

@Service("customerService")
@Transactional
public class CustomerService {

	@Autowired
	private CustomerDao custDao;
	
	@Autowired
	private CartDao cartDao;
	
	
	/*
	 *  gets customer having specified customer id
	 *  
	 *  @param CustomerId
	 *  
	 *  @return Customer Object
	 */
	public Customer findById(int id) {
		return custDao.findById(id);
	}

	/*
	 * saves customer in database(creates empty cart for customer too)
	 * 
	 * @param Customer object
	 */
	public void saveCustomer(Customer customer) {
		
		//creating cart along with customer
		Cart cart=cartDao.createEmptyCart();
		customer.setCart(cart);
		cart.setCustomer(customer);
		custDao.saveCustomer(customer);
	}

	/*
	 * updates customer fields in database
	 * 
	 * @param Customer object
	 */
	public void updateCustomer(Customer customer) {
		Customer entity = custDao.findById(customer.getCustId());
		if(entity!=null){
			entity.setCustName(customer.getCustName());
			entity.setCustUserName(customer.getCustUserName());
			entity.setEmailId(customer.getEmailId());
			entity.setContactNo(customer.getContactNo());
			entity.setResAddress(customer.getResAddress());
			entity.setPassword(customer.getPassword());
		}
	}

	/*
	 * deletes customer having specified user name in database
	 */
	public void deleteCustomerByUsername(String custUserName) {
		custDao.deleteCustomerByUsername(custUserName);
	}
	
	/*
	 * gets all customers from database
	 * 
	 * @return List of customers
	 */
	public List<Customer> findAllCustomers() {
		return custDao.findAllCustomers();
	}

	/*
	 * gets customer having specified user name
	 * 
	 * @param CustomerUsername
	 * 
	 * @return Customer object
	 */
	public Customer findCustomerByUsername(String custUserName) {
		return custDao.findCustomerByUsername(custUserName);
	}

	/*
	 * checks if username is unique in database
	 * 
	 * @param CustomerUsername
	 * 
	 * @return true if customer user name is not present in database
	 */
	public boolean isCustomerUserNameUnique(String custUserName) {
		Customer customer = findCustomerByUsername(custUserName);
		return ( customer == null );
	}
	
	/*
	 * checks if customer exists with specified username and password
	 * 
	 * @param CustomerUsername
	 * @param password
	 * 
	 * @return Customer object(null if customer doesn't exist)
	 */
	public Customer isCustomerExists(String custUserName,String password) {
		
		Customer customer = findCustomerByUsername(custUserName);
		if(customer !=null && customer.getPassword().equals(password))
			return customer;
		else
			return null;
	}
	
	/*
	 *  gets all the coupons of customer having specified customer id
	 *  
	 *  @param CustomerId
	 *  
	 *  @return List of coupons
	 */
	public List<Coupon> getAllCouponsOfCustomer(int custId) {
		Customer customer=custDao.findById(custId);
		if(customer==null)
			return new ArrayList<>();
		return custDao.getAllCoupons(customer);
	}
	
	/*
	 * gets Coupon of specified couponId from customer account
	 * 
	 * @param CustomerId
	 * @param CouponId
	 * 
	 * @return Coupon Object
	 */
	public Coupon getCouponByIdOfCustomer(int custId,String couponId) {
		
		Customer customer=custDao.findById(custId);
		if(customer==null)
			return null;
		for(Coupon coupon : customer.getCoupons()) {
			if(coupon.getCouponId().equals(couponId))
				return coupon;
		}
		return null;
	}
	
	/*
	 * remove coupons having specified CouponId from customer account
	 * 
	 * @param CustomerId
	 * @param CouponId
	 */
	public void removeCoupon(int custId,String couponId) {
		Customer customer=custDao.findById(custId);
		if(customer==null)
			return;
		for(Coupon coupon : customer.getCoupons()) {
			if(coupon.getCouponId().equals(couponId))
				customer.getCoupons().remove(coupon);
		}
		custDao.saveCustomer(customer);
	}
}
