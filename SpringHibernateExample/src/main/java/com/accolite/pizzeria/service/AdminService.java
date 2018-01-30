package com.accolite.pizzeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.dao.AdminDao;
import com.accolite.pizzeria.dao.CustomerDao;
import com.accolite.pizzeria.model.Admin;
import com.accolite.pizzeria.model.Customer;

@Service("adminService")
@Transactional
public class AdminService {

	@Autowired
	private CustomerDao custDao;
	
	@Autowired
	private AdminDao adminDao;
	
	/*
	 *  gets admin object having specified username and password
	 *  
	 *  @param AdminUsername
	 *  @param password
	 *  
	 *  @return Admin Object
	 */
	public Admin getAdmin(String adminUserName,String password) {
		Admin admin = adminDao.findAdminByUsername(adminUserName);
		if(admin!=null && admin.getPassword().equals(password))
			return admin;
		else
			return null;
	}
	/*
	 * saves the admin object in database
	 * 
	 * @param Admin object
	 */
	public void addAdmin(Admin admin) {
		adminDao.saveAdmin(admin);
	}
	
	/*
	 * adds coupon to customer account having specified customer id.
	 * 
	 * @param CustomerId
	 * @param Coupon object
	 */
	public void addCouponToCustomer(int custId,Coupon coupon) {
		Customer customer=custDao.findById(custId);
		if(customer==null)
			return;
		customer.getCoupons().add(coupon);
		custDao.saveCustomer(customer);
	}
}
