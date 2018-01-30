package com.accolite.pizzeria.testpack.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

import com.accolite.pizzeria.controller.PaymentController;
import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.service.CartService;
import com.accolite.pizzeria.service.CustomerService;
import com.accolite.pizzeria.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class TestPaymentController {

	@InjectMocks
	private PaymentController paycontrol;

	@Mock
	private CustomerService customerService;
	
	@Mock
	private CartService cartService;
	
	@Mock
	private OrderService orderService;

    @Test
    public void testListCoupons(){
    	
    	String userid="1";
    	List<Coupon> coupons=new ArrayList<>();
    	Mockito.doReturn(coupons).when(customerService).getAllCouponsOfCustomer(Integer.parseInt(userid));
    	assertNotNull(paycontrol.listcoupons(userid));
    }
    
    @Test
    public void testSavesFeedback() {
    	String feedback="Hello";
    	String userid="1";
    	String orderId="1";
    	Mockito.doReturn(false).when(orderService).addFeedback(Integer.parseInt(orderId), feedback);
    	
    	assertNotNull(paycontrol.savesFeedback(feedback, userid, orderId));
    }
}
