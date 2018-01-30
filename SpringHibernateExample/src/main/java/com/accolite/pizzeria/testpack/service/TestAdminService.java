package com.accolite.pizzeria.testpack.service;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.AdminDao;
import com.accolite.pizzeria.dao.CustomerDao;
import com.accolite.pizzeria.model.Admin;
import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.model.Customer;
import com.accolite.pizzeria.service.AdminService;

@RunWith(MockitoJUnitRunner.class)
public class TestAdminService {
	private static final String  DHRUV="dhruv";
	@InjectMocks
	private AdminService myadservice;
	
	@Mock
	private CustomerDao mycustDao;
	
	@Mock
	private AdminDao myadminDao;

	@Test
	public void testGetAdmin() {
		Admin admin=new Admin();
		admin.setAdminUserName(DHRUV);
		admin.setPassword(DHRUV);
		
		Mockito.doReturn(admin).when(myadminDao).findAdminByUsername(DHRUV);
		
		assertSame(admin, myadservice.getAdmin(DHRUV, DHRUV));
	}
	
	@Test
	public void testAddCouponToCustomer() {
		Customer cust=new Customer();
		cust.setCustId(1);
		Mockito.doReturn(cust).when(mycustDao).findById(1);
		Mockito.doNothing().when(mycustDao).saveCustomer(cust);
		
		Coupon coupon=new Coupon();
		myadservice.addCouponToCustomer(1, coupon);
		assertTrue(cust.getCoupons().contains(coupon));
	}
}
