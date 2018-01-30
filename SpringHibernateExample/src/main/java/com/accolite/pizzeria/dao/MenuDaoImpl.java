package com.accolite.pizzeria.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.accolite.pizzeria.model.MenuItem;

@Repository("menuDao")
public class MenuDaoImpl extends AbstractDao<Integer, MenuItem> implements MenuDao{

	/*
	 * gets MenuItem from the database having specified menu item id
	 * @see com.accolite.pizzeria.dao.MenuDao#findById(int)
	 * 
	 * @param menuItemId
	 * 
	 * @return MenuItem object
	 */
	public MenuItem findById(int id) {
		return getByKey(id);
	}
	
	/*
	 * gets menu Item having particular id using criteria object 
	 * @see com.accolite.pizzeria.dao.MenuDao#findMenuItemByItemId(int)
	 * 
	 * @param menuItemId
	 * 
	 * @return MenuItem object
	 */
	public MenuItem findMenuItemByItemId(int itemId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("menuItemId", itemId));
		return (MenuItem) criteria.uniqueResult();
	}
	
	/*
	 * gets all menu Items that are fixed(not custom)
	 * @see com.accolite.pizzeria.dao.MenuDao#getAllFixedMenuItems()
	 * 
	 * @return List of menuItems
	 */
	@SuppressWarnings("unchecked")
	public List<MenuItem> getAllFixedMenuItems() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("custom", false)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<MenuItem>) criteria.list();
		
	}
	
	/*
	 * save new menu item in database
	 * @see com.accolite.pizzeria.dao.MenuDao#saveMenuItem(com.accolite.pizzeria.model.MenuItem)
	 * 
	 * @param MenuItem
	 */
	public void saveMenuItem(MenuItem menuItem) {
		persist(menuItem);
	}
	
	/*
	 * gets amount of MenuItem 
	 * 
	 * @param Menu Item
	 * 
	 * @return amount of menu item (in double data type)
	 */
	public double getAmount(MenuItem menuItem) {
		return menuItem.getPrice();
	}
}
