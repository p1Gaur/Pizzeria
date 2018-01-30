package com.accolite.pizzeria.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.accolite.pizzeria.model.Order;
import com.accolite.pizzeria.model.OrderItem;
import com.accolite.pizzeria.model.OrderStatus;

@Repository("orderDao")
public class OrderDaoImpl extends AbstractDao<Integer, Order> implements OrderDao{
	
	/*
	 * gets the order object having specified order id.
	 * 
	 * @see com.accolite.pizzeria.dao.OrderDao#findById(int)
	 * 
	 * @param id order id
	 * 
	 * @return order object
	 */
	public Order findById(int id) {
		return getByKey(id);
	}
	
	/*
	 * save the order object in database.
	 * 
	 * @see com.accolite.pizzeria.dao.OrderDao#saveOrder(Order)
	 * 
	 * @param order object
	 * 
	 * @return void
	 */
	public void saveOrder(Order order) {
		persist(order);
	}
	
	/*
	 * add new OrderItem in existing order object's orderitems set
	 * @see com.accolite.pizzeria.dao.OrderDao#addOrderItem(com.accolite.pizzeria.model.Order, com.accolite.pizzeria.model.OrderItem)
	 * 
	 * @param  Order object
	 * @param OrderItem new order item
	 * 
	 * @return void
	 */
	public void addOrderItem(Order order,OrderItem orderItem) {
		order.getOrderItems().add(orderItem);
	}
	
	/*
	 * gets all the Orders from database having specified orderstatus
	 * It creates criteria and pass query to it based on order status
	 * @see com.accolite.pizzeria.dao.OrderDao#getAllOrdersByStatus(com.accolite.pizzeria.model.OrderStatus)
	 * 
	 * @param OrderStatus
	 * 
	 * @return List of orders
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getAllOrdersByStatus(OrderStatus orderStatus) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("orderStatus", orderStatus)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Order>) criteria.list();
	}
	
	/*
	 * gets all feedbacks from database
	 * @see com.accolite.pizzeria.dao.OrderDao#getAllNonNullFeedbackOrders()
	 * 
	 * @return List of order objects
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getAllNonNullFeedbackOrders() {
		Query query = getSession().createQuery("FROM Order WHERE feedback is not null");
		return (List<Order>)query.list();
		
	}
	
	/*
	 * gets all orders of particular customer
	 * @see com.accolite.pizzeria.dao.OrderDao#getAllOrdersOfCustomer(int)
	 * 
	 * @param customer_id
	 * 
	 * @return List of order objects
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getAllOrdersOfCustomer(int custId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("custId", custId));
		return (List<Order>)criteria.list();
	}
	
	/*
	 *  gets all orders from database
	 *  
	 *  @return List of orders
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getAllOrders() {
		Criteria criteria = createEntityCriteria();
		return (List<Order>)criteria.list();
	}
}
