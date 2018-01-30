package com.accolite.pizzeria.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.accolite.pizzeria.model.Admin;

@Repository("adminDao")
public class AdminDaoImpl extends AbstractDao<Integer, Admin> implements AdminDao{

	/*
	 * gets the admin having specified adminId
	 * @see com.accolite.pizzeria.dao.AdminDao#findById(int)
	 * 
	 * @param AdminId
	 * 
	 * @return Admin object
	 */
	public Admin findById(int id) {
		return getByKey(id);
	}
	
	/*
	 * gets the admin having specified username
	 * @see com.accolite.pizzeria.dao.AdminDao#findAdminByUsername(java.lang.String)
	 * 
	 * @param AdminUsername
	 * 
	 * @return admin object
	 */
	public Admin findAdminByUsername(String adminUserName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("adminUserName", adminUserName));
		return (Admin) criteria.uniqueResult();
	}
	
	/*
	 * save Admin in database
	 * @see com.accolite.pizzeria.dao.AdminDao#saveAdmin(com.accolite.pizzeria.model.Admin)
	 * 
	 * @param Admin Object
	 */
	public void saveAdmin(Admin admin) {
		persist(admin);
	}
}
