package com.accolite.pizzeria.testpack.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.CartDao;
import com.accolite.pizzeria.dao.CouponDao;
import com.accolite.pizzeria.dao.CustomerDao;
import com.accolite.pizzeria.model.Cart;
import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.model.Customer;
import com.accolite.pizzeria.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class TestCustomerService {
	private static final String  DHRUV="dhruv";

	@InjectMocks
	private CustomerService mycService;
	
	@Mock
	private CustomerDao custDao;
	
	@Mock
	private CartDao cartDao;
	
	@Mock
	private CouponDao couponDao;
	
	@Test
	public void testFindById() {
		Customer cust=new Customer();
		Mockito.doReturn(cust).when(custDao).findById(1);
		assertSame(cust,mycService.findById(1));
	}
	
	@Test
	public void testSaveCustomer() {
		Customer cust=new Customer();
		Cart cart=new Cart();
		Mockito.doReturn(cart).when(cartDao).createEmptyCart();
		Mockito.doNothing().when(custDao).saveCustomer(cust);
		mycService.saveCustomer(cust);
		assertSame(cart, cust.getCart());
	}
	
	@Test
	public void testUpdateCustomer() {
		Customer cust=new Customer();
		Customer newCust=new Customer();
		newCust.setCustName(DHRUV);
		newCust.setCustId(1);
		Mockito.doReturn(cust).when(custDao).findById(1);
		mycService.updateCustomer(newCust);
		assertEquals(DHRUV, cust.getCustName());
	}
	
	@Test
	public void testFindCustomerByUsername() {
		Customer cust=new Customer();
		Mockito.doReturn(cust).when(custDao).findCustomerByUsername(DHRUV);
		assertSame(cust,mycService.findCustomerByUsername(DHRUV));
	}
	
	@Test 
	public void testIsCustomerExists() {
		Customer cust=new Customer();
		cust.setPassword(DHRUV);
		Mockito.doReturn(cust).when(custDao).findCustomerByUsername(DHRUV);
		assertEquals(cust,mycService.isCustomerExists(DHRUV, DHRUV));
		
		assertNull(mycService.isCustomerExists(DHRUV, "sharma"));
	}
	
	@Test
	public void testGetCouponByIdOfCustomer() {
		
		Customer cust=new Customer();
		cust.setCustId(1);
		Set<Coupon> coupons=new HashSet<>();
		Coupon c=new Coupon();
		c.setCouponId("abc");
		coupons.add(c);
		cust.setCoupons(coupons);
		
		Mockito.doReturn(cust).when(custDao).findById(1);
		assertSame(c,mycService.getCouponByIdOfCustomer(1, "abc"));
	}
	@Test
	public void testRemoveCoupon() {
		Customer cust=new Customer();
		cust.setCustId(1);
		Set<Coupon> coupons=new HashSet<>();
		Coupon c=new Coupon();
		c.setCouponId("abc");
		coupons.add(c);
		cust.setCoupons(coupons);
		
		Mockito.doReturn(cust).when(custDao).findById(1);
		Mockito.doNothing().when(custDao).saveCustomer(cust);
		mycService.removeCoupon(1, "abc");
		assertTrue(cust.getCoupons().isEmpty());
	}
}
