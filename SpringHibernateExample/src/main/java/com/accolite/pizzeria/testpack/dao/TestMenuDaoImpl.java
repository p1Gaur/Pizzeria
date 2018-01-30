package com.accolite.pizzeria.testpack.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.MenuDaoImpl;
import com.accolite.pizzeria.model.MenuItem;

@RunWith(MockitoJUnitRunner.class)
public class TestMenuDaoImpl {

	@Test
    public void testFindById() {	
    	MenuDaoImpl menuDaoImpl=Mockito.mock(MenuDaoImpl.class);
    	MenuItem menuItem=new MenuItem();
    	when(menuDaoImpl.getByKey(1)).thenReturn(menuItem);
    	when(menuDaoImpl.findById(1)).thenCallRealMethod();    	
    	assertSame(menuItem,menuDaoImpl.findById(1));
    }
	
	@Test
	public void testFindMenuItemByItemId() {
		int itemId=1;
		MenuItem menuItem=new MenuItem();
		MenuDaoImpl menuDaoImpl=Mockito.mock(MenuDaoImpl.class);
		
    	Criteria criteria = Mockito.mock(Criteria.class);
    	Mockito.doReturn(criteria).when(menuDaoImpl).createEntityCriteria();
    	Mockito.doReturn(criteria).when(criteria).add(Restrictions.eq("menuItemId", itemId));
    	Mockito.doReturn(menuItem).when(criteria).uniqueResult();
    	
    	Mockito.doCallRealMethod().when(menuDaoImpl).findMenuItemByItemId(itemId);
    	
    	assertSame(menuItem,menuDaoImpl.findMenuItemByItemId(itemId));
	}
	
	
	@Test
	public void testGetAllFixedMenuItems() {
		
		List<MenuItem> menuList=new ArrayList<>();
		MenuDaoImpl menuDaoImpl=Mockito.mock(MenuDaoImpl.class);
		
    	Criteria criteria = Mockito.mock(Criteria.class);
    	Mockito.doReturn(criteria).when(menuDaoImpl).createEntityCriteria();
    	Mockito.doReturn(criteria).when(criteria).add(Restrictions.eq("custom", false));
    	Mockito.doReturn(criteria).when(criteria).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    	Mockito.doReturn(menuList).when(criteria).list();
    	Mockito.doReturn(menuList).when(menuDaoImpl).getAllFixedMenuItems();
    	
    	assertSame(menuList,menuDaoImpl.getAllFixedMenuItems());
	}
	
	@Test
	public void testSaveMenuItem() {
		MenuDaoImpl menuDaoImpl=Mockito.mock(MenuDaoImpl.class);
		MenuItem menuItem=new MenuItem();
    	Mockito.doNothing().when(menuDaoImpl).persist(menuItem);
    	Mockito.doCallRealMethod().when(menuDaoImpl).saveMenuItem(menuItem);
    	menuDaoImpl.saveMenuItem(menuItem);
    	Mockito.verify(menuDaoImpl).persist(menuItem);
	}
	
	@Test
	public void testGetAmount() {
		
		MenuItem menuItem=new MenuItem();
		menuItem.setPrice(100.00);
		
		MenuDaoImpl menuDaoImpl=new MenuDaoImpl();
		assertEquals(100.00, menuDaoImpl.getAmount(menuItem), 0.05);
	}
}
