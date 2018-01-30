package com.accolite.pizzeria.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.model.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends AbstractDao<Integer, Customer> implements CustomerDao {

	/*
	 * gets the customer having specified customer id
	 * @see com.accolite.pizzeria.dao.CustomerDao#findById(int)
	 * 
	 * @param customerId
	 * 
	 * @return Customer object
	 */
	public Customer findById(int id) {
		return getByKey(id);
	}

	/*
	 * saves the customer object in database
	 * @see com.accolite.pizzeria.dao.CustomerDao#saveCustomer(com.accolite.pizzeria.model.Customer)
	 * 
	 * @param Customer object
	 */
	public void saveCustomer(Customer customer) {
		persist(customer);
	}
	
	/*
	 * delete customer  from database having specified user name 
	 * @see com.accolite.pizzeria.dao.CustomerDao#deleteCustomerByUsername(java.lang.String)
	 * 
	 * @param CustomerUsername
	 */
	public void deleteCustomerByUsername(String custUserName) {
		Query query = getSession().createSQLQuery("delete from Customer where custUserName = :custUserName");
		query.setString("custUserName", custUserName);
		query.executeUpdate();
	}

	/*
	 * gets all the customers' data from database
	 * @see com.accolite.pizzeria.dao.CustomerDao#findAllCustomers()
	 * 
	 * @return List of customers
	 */
	@SuppressWarnings("unchecked")
	public List<Customer> findAllCustomers() {
		Criteria criteria = createEntityCriteria();
		return (List<Customer>) criteria.list();
	}

	/*
	 * gets the customer having specified username
	 * @see com.accolite.pizzeria.dao.CustomerDao#findCustomerByUsername(java.lang.String)
	 * 
	 * @param CustomerUsername
	 * 
	 * @return Customer object
	 */
	public Customer findCustomerByUsername(String custUserName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("custUserName", custUserName));
		return (Customer) criteria.uniqueResult();
	}
	
	/*
	 * gets all coupons of customer
	 * @see com.accolite.pizzeria.dao.CustomerDao#getAllCoupons(com.accolite.pizzeria.model.Customer)
	 * 
	 * @param customer object
	 * 
	 * @return List of coupons
	 */
	public List<Coupon> getAllCoupons(Customer customer) {
		if(customer.getCoupons()==null)
			return new ArrayList<>();
		return new ArrayList<>(customer.getCoupons());
	}
}
