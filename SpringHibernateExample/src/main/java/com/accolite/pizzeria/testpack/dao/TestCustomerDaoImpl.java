package com.accolite.pizzeria.testpack.dao;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.CustomerDaoImpl;
import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.model.Customer;

@RunWith(MockitoJUnitRunner.class)
public class TestCustomerDaoImpl {

	@Test
    public void testFindById() {	
    	CustomerDaoImpl custDaoImpl=Mockito.mock(CustomerDaoImpl.class);
    	Customer cust=new Customer();
    	when(custDaoImpl.getByKey(1)).thenReturn(cust);
    	when(custDaoImpl.findById(1)).thenCallRealMethod();    	
    	assertSame(cust,custDaoImpl.findById(1));
    }
	@Test
	public void testSaveCustomer() {
		CustomerDaoImpl custDaoImpl=Mockito.mock(CustomerDaoImpl.class);
    	Customer cust=new Customer();
    	Mockito.doNothing().when(custDaoImpl).persist(cust);
    	Mockito.doCallRealMethod().when(custDaoImpl).saveCustomer(cust);
    	custDaoImpl.saveCustomer(cust);
    	Mockito.verify(custDaoImpl).persist(cust);
	}
	
	@Test
	public void testDeleteCustomerByUsername() {
		CustomerDaoImpl custDaoImpl=Mockito.mock(CustomerDaoImpl.class);
    	Session session=Mockito.mock(Session.class);
    	SQLQuery query=Mockito.mock(SQLQuery.class);
    	
    	String custUserName="";
    	
    	Mockito.doReturn(session).when(custDaoImpl).getSession();
    	Mockito.doReturn(query).when(session).createSQLQuery("delete from Customer where custUserName = :custUserName");
    	Mockito.doReturn(query).when(query).setString("custUserName", custUserName);
    	Mockito.doReturn(1).when(query).executeUpdate();
    	Mockito.doCallRealMethod().when(custDaoImpl).deleteCustomerByUsername(custUserName);
    	
    	custDaoImpl.deleteCustomerByUsername(custUserName);
    	
    	Mockito.verify(query).executeUpdate();
	}
	
	@Test
    public void testFindAllCustomers() {
    	List<Customer> custList=new ArrayList<>();
    	CustomerDaoImpl custDaoImpl=Mockito.mock(CustomerDaoImpl.class);
    	Criteria criteria = Mockito.mock(Criteria.class);
    	Mockito.doReturn(criteria).when(custDaoImpl).createEntityCriteria();
    	Mockito.doReturn(custList).when(criteria).list();
    	
    	Mockito.doCallRealMethod().when(custDaoImpl).findAllCustomers();
    	assertSame(custList,custDaoImpl.findAllCustomers());
    }
	
	@Test
	public void testFindCustomerByUsername() {
		CustomerDaoImpl custDaoImpl=Mockito.mock(CustomerDaoImpl.class);
    	Customer cust=new Customer();
    	
    	String custUserName="";
    	
    	Criteria criteria = Mockito.mock(Criteria.class);
    	Mockito.doReturn(criteria).when(custDaoImpl).createEntityCriteria();
    	Mockito.doReturn(criteria).when(criteria).add(Restrictions.eq("custUserName", custUserName));
    	Mockito.doReturn(cust).when(criteria).uniqueResult();
    	
    	Mockito.doCallRealMethod().when(custDaoImpl).findCustomerByUsername(custUserName);
    	
    	assertSame(cust,custDaoImpl.findCustomerByUsername(custUserName));
    	
	}
	
	@Test
	public void testGetAllCoupons() {
		CustomerDaoImpl custDaoImpl=new CustomerDaoImpl();
		Customer cust=new Customer();
	
		cust.setCoupons(null);
		assertSame(null,custDaoImpl.getAllCoupons(cust));
		
		Set<Coupon> coupons=new HashSet<>();
		Coupon coupon=new Coupon();
		coupons.add(coupon);
		cust.setCoupons(coupons);
		
		List<Coupon>copList=custDaoImpl.getAllCoupons(cust);
	
		assertTrue(copList.contains(coupon));
	}
}
