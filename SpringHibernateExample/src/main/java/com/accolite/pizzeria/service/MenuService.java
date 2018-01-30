package com.accolite.pizzeria.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accolite.pizzeria.dao.IngredientDao;
import com.accolite.pizzeria.dao.MenuDao;
import com.accolite.pizzeria.model.Ingredient;
import com.accolite.pizzeria.model.IngredientStatus;
import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.model.MenuItemStatus;

@Service("menuService")
@Transactional
public class MenuService {

	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private IngredientDao ingrDao;
	
	/*
	 * gets all fixed(non-custom) menu items from database
	 * 
	 * @return List of menuItems
	 */
	public List<MenuItem> getAllFixedMenuItems() {
		return menuDao.getAllFixedMenuItems();
	}
	
	/*
	 *  saves Menu Item in database
	 *  
	 *  @param MenuItem
	 */
	public void addMenuItem(MenuItem menuItem) {
		menuDao.saveMenuItem(menuItem);
	}
	
	/*
	 *  add existing ingredient to existing menu item
	 *  
	 *  @param MenuItemId
	 *  @param IngredientId
	 *  
	 *  @return true if ingredient is available and successfully saved otherwise false
	 */
	public boolean addIngredientToItem(int menuItemId,int ingrId) {
		MenuItem menuItem=menuDao.findById(menuItemId);
		Ingredient ingredient=ingrDao.findById(ingrId);
		if(ingredient.getIngredientStatus()==IngredientStatus.AVAILABLE) {
			menuItem.getIngredients().add(ingredient);
			menuDao.saveMenuItem(menuItem);
			return true;
		}
		return false;
	}
	
	/*
	 * removes ingredient from Menu Item
	 * 
	 * @param MenuItemId
	 * @param IngredientId
	 */
	public void removeIngredientFromItem(int menuItemId,int ingrId) {
		MenuItem menuItem=menuDao.findById(menuItemId);
		Set<Ingredient> ingredients=menuItem.getIngredients();
		for(Ingredient ingredient : ingredients) {
			if(ingredient.getIngrId() == ingrId) 
				ingredients.remove(ingredient);
		}
		menuDao.saveMenuItem(menuItem);
	}
	
	/*
	 *  saves new ingredient in database
	 *  
	 *  @param Ingredient object
	 */
	public void addNewIngredient(Ingredient ingredient) {
		ingrDao.saveIngredient(ingredient);
	}
	
	/*
	 * get status of MenuItem
	 * 
	 * @param MenuItemId
	 * 
	 * @return MenuItemStatus
	 */
	public MenuItemStatus getMenuItemStatus(int itemId) {
		MenuItem menuItem=menuDao.findById(itemId);
		if(menuItem==null)
			return null;
		return menuItem.getMenuItemStatus();
	}
	public IngredientStatus getIngredientStatus(int ingrId) {
		Ingredient ingredient=ingrDao.findById(ingrId);
		if(ingredient==null)
			return null;
		return ingredient.getIngredientStatus();
	}
	
	/*
	 * changes the status of menu item
	 * 
	 * @param MenuItemId
	 * @param MenuItemStatus
	 */
	public void changeMenuItemStatus(int itemId,MenuItemStatus status) {
		MenuItem menuItem=menuDao.findById(itemId);
		menuItem.setMenuItemStatus(status);
		menuDao.saveMenuItem(menuItem);
	}
	
	/*
	 * changes the status of ingredient
	 * 
	 * @param IngredientId
	 * @param IngredientStatus
	 */
	public void changeIngredientStatus(int ingrId,IngredientStatus status) {
		Ingredient ingredient=ingrDao.findById(ingrId);
		ingredient.setIngredientStatus(status);
		ingrDao.saveIngredient(ingredient);
	}

}
