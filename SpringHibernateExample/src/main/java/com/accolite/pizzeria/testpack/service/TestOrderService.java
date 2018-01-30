package com.accolite.pizzeria.testpack.service;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.OrderDaoImpl;
import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.model.Order;
import com.accolite.pizzeria.model.OrderItem;
import com.accolite.pizzeria.service.CartInfo;
import com.accolite.pizzeria.service.Feedback;
import com.accolite.pizzeria.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class TestOrderService {

	@InjectMocks
	private OrderService myorderService;
	
	@Mock
	private OrderDaoImpl myorderDao;
	
	@Test
	public void testPlaceOrder() {
		Order order=new Order();
		Mockito.doNothing().when(myorderDao).saveOrder(order);
		myorderService.placeOrder(order);
		Mockito.verify(myorderDao).saveOrder(order);
	}
	
	@Test
	public void testAddOrderItems() {
		MenuItem menuItem=new MenuItem();
		menuItem.setMenuItemId(1);
		
		CartInfo cartInfo=new CartInfo();
		cartInfo.setMenuItem(menuItem);
		cartInfo.setQty(1);
		
		List<CartInfo> cartitemsList=new ArrayList<>();
		cartitemsList.add(cartInfo);
		
		Order order=new Order();
		OrderItem oitem=new OrderItem();
		oitem.setMenuItemId(1);
		oitem.setQuantity(1);
		Mockito.doCallRealMethod().when(myorderDao).addOrderItem(order, oitem);
		Mockito.doNothing().when(myorderDao).persist(order);
		
		myorderService.addOrderItems(order, cartitemsList);
		for(OrderItem ordItem : order.getOrderItems())
		{
			assertTrue(ordItem.getMenuItemId()==1);
				
		}
	}
	@Test
	public void testGetAllFeedbacks() {
		Order order=new Order();
		order.setOrderId(1);
		List<Order> ordList=new ArrayList<>();
		ordList.add(order);
		
		Mockito.doReturn(ordList).when(myorderDao).getAllNonNullFeedbackOrders();
		
		List<Feedback> feedlist=myorderService.getAllFeedbacks();
		for(Feedback feed : feedlist) {
			assertTrue(feed.getOrderId()==1);
		}
	}
	
	@Test
	public void testAddFeedback() {
		String feedbackText="abc";
		Order order=new Order();
		Mockito.doReturn(order).when(myorderDao).findById(1);
		Mockito.doNothing().when(myorderDao).saveOrder(order);
		assertTrue(myorderService.addFeedback(1, feedbackText));
	}
	
	@Test
	public void testGetAllOrdersOfCustomer() {
		List<Order> ordList=new ArrayList<>();
		Mockito.doReturn(ordList).when(myorderDao).getAllOrdersOfCustomer(1);
		assertSame(ordList,myorderService.getAllOrdersOfCustomer(1));
	}
	
	@Test
	public void testGetAllOrders() {
		List<Order> ordList=new ArrayList<>();
		Mockito.doReturn(ordList).when(myorderDao).getAllOrders();
		assertSame(ordList,myorderService.getAllOrders());
	}
}
