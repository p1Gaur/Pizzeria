package com.accolite.pizzeria.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accolite.pizzeria.dao.OrderDao;
import com.accolite.pizzeria.model.Order;
import com.accolite.pizzeria.model.OrderItem;

@Service("orderService")
@Transactional
public class OrderService {
	
	@Autowired
	OrderDao orderDao;
	
	/*
	 * saves the passed order in the database
	 * 
	 * @param Order object
	 */
	public void placeOrder(Order order) {
		orderDao.saveOrder(order);
	}
	/*
	 * adds the cart items present in list to the existing cart and saves the cart in database.
	 * 
	 * @param Order object
	 * @param List of cart items
	 */
	public void addOrderItems(Order order,List<CartInfo> cartItemsList) {
		
		for(CartInfo cartItem : cartItemsList) {
			orderDao.addOrderItem(order, new OrderItem(cartItem.getMenuItem().getMenuItemId(),cartItem.getQty()));
		}
		orderDao.saveOrder(order);
	}
	
	/*
	 *  gets all feedbacks of all users from the database
	 *  
	 *  @return List of feedback objects
	 */
	public List<Feedback> getAllFeedbacks() {
		
		List<Order> orderList=orderDao.getAllNonNullFeedbackOrders();
		if(orderList.isEmpty())
			return new ArrayList<>();
		List<Feedback> feedbackList=new ArrayList<Feedback>();
		for(Order order : orderList) {
			feedbackList.add(new Feedback(order.getOrderId(),order.getFeedback(),order.getPlacingTime()));
		}
		return feedbackList;
	}
	
	/*
	 *  saves feedback text in existing order 
	 *  
	 *  @param OrderId
	 *  @param FeedbackText
	 *  
	 *  @return boolean true if feedback successfully saved in order otherwise false. 
	 */
	public boolean addFeedback(int orderId,String feedbackText) {
		Order order=orderDao.findById(orderId);
		if(order==null)
			return false;
		order.setFeedback(feedbackText);
		orderDao.saveOrder(order);
		return true;
	}
	
	/*
	 * gets all orders of customer having specified customerId
	 * 
	 * @param CustomerId
	 * 
	 * @return List of orders
	 */
	public List<Order> getAllOrdersOfCustomer(int custId) {
		return orderDao.getAllOrdersOfCustomer(custId);
	}
	public List<Order> getAllOrders() {
		return orderDao.getAllOrders();
	}
}
