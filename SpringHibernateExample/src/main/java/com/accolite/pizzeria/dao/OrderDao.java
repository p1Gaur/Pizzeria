package com.accolite.pizzeria.dao;

import java.util.List;

import com.accolite.pizzeria.model.Order;
import com.accolite.pizzeria.model.OrderItem;
import com.accolite.pizzeria.model.OrderStatus;

public interface OrderDao {
	
	public Order findById(int id);
	public void saveOrder(Order order);
	public void addOrderItem(Order order,OrderItem orderItem);
	public List<Order> getAllOrdersByStatus(OrderStatus orderStatus);
	public List<Order> getAllNonNullFeedbackOrders();
	public List<Order> getAllOrdersOfCustomer(int custId);
	public List<Order> getAllOrders();
}
