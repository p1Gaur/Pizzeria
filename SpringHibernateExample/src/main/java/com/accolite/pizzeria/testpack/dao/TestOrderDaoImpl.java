package com.accolite.pizzeria.testpack.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.OrderDaoImpl;
import com.accolite.pizzeria.model.Order;
import com.accolite.pizzeria.model.OrderItem;
import com.accolite.pizzeria.model.OrderStatus;

@RunWith(MockitoJUnitRunner.class)
public class TestOrderDaoImpl {
	
    @Test
    public void testFindById() {	
    	OrderDaoImpl orderDaoImpl=Mockito.mock(OrderDaoImpl.class);
    	Order order=new Order();
    	when(orderDaoImpl.getByKey(1)).thenReturn(order);
    	when(orderDaoImpl.findById(1)).thenCallRealMethod();    	
    	assertSame(order,orderDaoImpl.findById(1));
    }
    
    @Test
    public void testSaveOrder() {
    	OrderDaoImpl orderDaoImpl=Mockito.mock(OrderDaoImpl.class);
    	Order order=new Order();
    	Mockito.doNothing().when(orderDaoImpl).persist(order);
    	Mockito.doCallRealMethod().when(orderDaoImpl).saveOrder(order);
    	orderDaoImpl.saveOrder(order);
    	Mockito.verify(orderDaoImpl).persist(order);
    }
    
    @Test
    public void testAddOrderItem() {
    	OrderDaoImpl orderDaoImpl=new OrderDaoImpl();
    	Order order=new Order();
    	OrderItem item=new OrderItem();
    	orderDaoImpl.addOrderItem(order,item);
    	Set<OrderItem> orderItems=order.getOrderItems();
    	assertTrue(orderItems.contains(item));
    }
    @Test
    public void testGetAllOrdersByStatus() {
    	OrderStatus orderStatus=OrderStatus.ACTIVE;
    	List<Order> orderList=new ArrayList<Order>();
    	
    	OrderDaoImpl orderDaoImpl=Mockito.mock(OrderDaoImpl.class);
    	
    	Criteria criteria=Mockito.mock(Criteria.class);
    	Mockito.doReturn(criteria).when(orderDaoImpl).createEntityCriteria();
    	
    	Mockito.doReturn(criteria).when(criteria).add(Restrictions.eq("orderStatus", orderStatus));
    	Mockito.doReturn(criteria).when(criteria).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    	Mockito.doReturn(orderList).when(criteria).list();
    	
    	Mockito.doReturn(orderList).when(orderDaoImpl).getAllOrdersByStatus(OrderStatus.ACTIVE);
    	assertSame(orderList,orderDaoImpl.getAllOrdersByStatus(orderStatus));
    }
    @Test
    public void testGetAllNonNullFeedbackOrders() {
    	OrderDaoImpl orderDaoImpl=Mockito.mock(OrderDaoImpl.class);
    	Session session=Mockito.mock(Session.class);
    	Query query=Mockito.mock(Query.class);
    	List<Order> orderList=new ArrayList<>();
    	
    	Mockito.doReturn(session).when(orderDaoImpl).getSession();
    	Mockito.doReturn(query).when(session).createQuery("FROM Order WHERE feedback is not null");
    	Mockito.doReturn(orderList).when(query).list();
    	Mockito.doCallRealMethod().when(orderDaoImpl).getAllNonNullFeedbackOrders();
    	
    	assertSame(orderList,orderDaoImpl.getAllNonNullFeedbackOrders());
    }
    
    @Test
    public void testGetAllOrdersOfCustomer() {
    	int custId=1;
    	List<Order> orderList=new ArrayList<>();
    	OrderDaoImpl orderDaoImpl=Mockito.mock(OrderDaoImpl.class);
    	Criteria criteria = Mockito.mock(Criteria.class);
    	Mockito.doReturn(criteria).when(orderDaoImpl).createEntityCriteria();
    	Mockito.doReturn(criteria).when(criteria).add(Restrictions.eq("custId", custId));
    	Mockito.doReturn(orderList).when(criteria).list();
    	
    	Mockito.doCallRealMethod().when(orderDaoImpl).getAllOrdersOfCustomer(custId);
    	
    	assertSame(orderList,orderDaoImpl.getAllOrdersOfCustomer(custId));
    	
    }
    @Test
    public void testGetAllOrders() {
    	List<Order> orderList=new ArrayList<>();
    	OrderDaoImpl orderDaoImpl=Mockito.mock(OrderDaoImpl.class);
    	Criteria criteria = Mockito.mock(Criteria.class);
    	Mockito.doReturn(criteria).when(orderDaoImpl).createEntityCriteria();
    	Mockito.doReturn(orderList).when(criteria).list();
    	
    	Mockito.doCallRealMethod().when(orderDaoImpl).getAllOrders();
    	assertSame(orderList,orderDaoImpl.getAllOrders());
    }
}
