package com.accolite.pizzeria.testpack.controller;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.accolite.pizzeria.controller.CartController;
import com.accolite.pizzeria.model.Customer;
import com.accolite.pizzeria.service.CartInfo;
import com.accolite.pizzeria.service.CartService;
import com.accolite.pizzeria.service.CustomerService;
import com.accolite.pizzeria.service.MenuService;

@RunWith(MockitoJUnitRunner.class)
public class TestCartController {

	@InjectMocks
	private CartController cartcontrol;
	
	@Mock
	private CartService cartService;
	
	@Mock
	private CustomerService custService;
	
	@Mock	
	private MenuService menuService;
	
	@Test
	public void testAddItemToCart() {
		
		Customer c=new Customer();
		String menuid="1";
		String userid="1";
		HttpServletRequest request=null;
		UriComponentsBuilder ucBuilder=null;
		Mockito.doReturn(c).when(custService).findById(Integer.parseInt(userid));
		Mockito.doReturn(false).when(cartService).addToCartOfUser(Integer.parseInt(userid), Integer.parseInt(menuid));
		assertNotNull(cartcontrol.addItemToCart(menuid, userid, request, ucBuilder));
	}
	
	@Test
	public void testSubItemToCart() {
		
		Customer c=new Customer();
		String menuid="1";
		String userid="1";
		HttpServletRequest request=null;
		UriComponentsBuilder ucBuilder=null;
		Mockito.doReturn(c).when(custService).findById(Integer.parseInt(userid));
		Mockito.doReturn(false).when(cartService).addToCartOfUser(Integer.parseInt(userid), Integer.parseInt(menuid));
		assertNotNull(cartcontrol.subItemFromCart(menuid, userid, request, ucBuilder));
	}
	
	@Test
	public void testListCart() {
		List<CartInfo> cartlist=new ArrayList<>();
		String userid="1";
		Mockito.doReturn(cartlist).when(cartService).getAllCartItemsOfCustomer(Integer.parseInt(userid));
		assertNotNull(cartcontrol.listcart(userid));
		
	}
	
	@Test
	public void testGetAmount() {
		String userid="1";
		Mockito.doReturn(100.00).when(cartService).getTotalAmountOfCart(Integer.parseInt(userid));
		assertNotNull(cartcontrol.getTotalAmount(userid));
	}
}
