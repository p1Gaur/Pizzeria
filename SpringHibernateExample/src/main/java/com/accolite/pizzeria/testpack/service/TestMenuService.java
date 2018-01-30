package com.accolite.pizzeria.testpack.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.IngredientDao;
import com.accolite.pizzeria.dao.MenuDao;
import com.accolite.pizzeria.model.Ingredient;
import com.accolite.pizzeria.model.IngredientStatus;
import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.model.MenuItemStatus;
import com.accolite.pizzeria.service.MenuService;

@RunWith(MockitoJUnitRunner.class)
public class TestMenuService {

	@InjectMocks
	private MenuService mymenuService;
	
	@Mock
	private MenuDao mymenuDao;
	
	@Mock
	private IngredientDao myingrDao;
	
	@Test
	public void testGetAllFixedMenuItems() {
		List<MenuItem> mList=new ArrayList<>();
		
		Mockito.doReturn(mList).when(mymenuDao).getAllFixedMenuItems();
		
		assertSame(mList, mymenuService.getAllFixedMenuItems());
	}
	
	@Test
	public void testAddMenuItem() {
		MenuItem menuItem=new MenuItem();
		Mockito.doNothing().when(mymenuDao).saveMenuItem(menuItem);
		mymenuService.addMenuItem(menuItem);
		Mockito.verify(mymenuDao).saveMenuItem(menuItem);
	}
	
	@Test
	public void testAddIngredientToItem() {
		MenuItem mitem=new MenuItem();
		Ingredient ingr=new Ingredient();
		ingr.setIngredientStatus(IngredientStatus.AVAILABLE);
		Mockito.doReturn(mitem).when(mymenuDao).findById(1);
		Mockito.doReturn(ingr).when(myingrDao).findById(1);
		Mockito.doNothing().when(mymenuDao).saveMenuItem(mitem);
		assertTrue(mymenuService.addIngredientToItem(1, 1));
		
		ingr.setIngredientStatus(IngredientStatus.OUTOFSTOCK);
		assertFalse(mymenuService.addIngredientToItem(1, 1));
	}
	
	@Test
	public void testRemoveIngredientFromItem() {
		MenuItem mitem=new MenuItem();
		mitem.setMenuItemId(1);
		Ingredient ingr=new Ingredient();
		ingr.setIngrId(1);
		Set<Ingredient> iset=new HashSet<>();
		iset.add(ingr);
		mitem.setIngredients(iset);
		
		Mockito.doReturn(mitem).when(mymenuDao).findById(1);
		Mockito.doNothing().when(mymenuDao).saveMenuItem(mitem);
		
		mymenuService.removeIngredientFromItem(1, 1);
		
		assertTrue(mitem.getIngredients().isEmpty());
	}
	
	@Test
	public void testGetMenuItemStatus() {
		MenuItem mitem=new MenuItem();
		mitem.setMenuItemId(1);
		mitem.setMenuItemStatus(MenuItemStatus.AVAILABLE);
		Mockito.doReturn(mitem).when(mymenuDao).findById(1);
		
		assertSame(MenuItemStatus.AVAILABLE,mymenuService.getMenuItemStatus(1));
	}
	
	@Test
	public void testChangeMenuItemStatus() {
		MenuItem mitem=new MenuItem();
		mitem.setMenuItemId(1);
		Mockito.doReturn(mitem).when(mymenuDao).findById(1);
		Mockito.doNothing().when(mymenuDao).saveMenuItem(mitem);
		
		mymenuService.changeMenuItemStatus(1, MenuItemStatus.AVAILABLE);
		assertSame(MenuItemStatus.AVAILABLE,mitem.getMenuItemStatus());
	}
}
