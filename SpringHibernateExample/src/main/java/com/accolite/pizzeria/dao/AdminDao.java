package com.accolite.pizzeria.dao;

import com.accolite.pizzeria.model.Admin;

public interface AdminDao {
	
	Admin findById(int id);
	public Admin findAdminByUsername(String adminUserName);
	public void saveAdmin(Admin admin);
}
