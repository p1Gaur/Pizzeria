package com.accolite.pizzeria.dao;

import java.util.List;

import com.accolite.pizzeria.model.MenuItem;

public interface MenuDao {

	public MenuItem findById(int id);
	public MenuItem findMenuItemByItemId(int itemId);
	public List<MenuItem> getAllFixedMenuItems();
	public void saveMenuItem(MenuItem menuItem);
}
