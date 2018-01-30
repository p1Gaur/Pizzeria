package com.accolite.pizzeria.testpack.controller;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.controller.AdminController;
import com.accolite.pizzeria.model.IngredientStatus;
import com.accolite.pizzeria.model.MenuItemStatus;
import com.accolite.pizzeria.service.AdminService;
import com.accolite.pizzeria.service.Feedback;
import com.accolite.pizzeria.service.MenuService;
import com.accolite.pizzeria.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class TestAdminController {

	@InjectMocks
	private AdminController adcontrol;
	
	@Mock
	AdminService service;
	
	@Mock
	OrderService orderservice;
	
	@Mock
	MenuService menuservice;
	
	@Test
	public void testListFeedback() {
		List<Feedback> feedlist=new ArrayList<>();
		feedlist.add(new Feedback(1,"",null));
		
		Mockito.doReturn(feedlist).when(orderservice).getAllFeedbacks();
		
		assertNotNull(adcontrol.listFeedback());
	}
	
	@Test
	public void testTogglePizza() {
		
		Mockito.doReturn(MenuItemStatus.AVAILABLE).when(menuservice).getMenuItemStatus(1);
		Mockito.doNothing().when(menuservice).changeMenuItemStatus(1,MenuItemStatus.UNAVAILABLE);
		assertNotNull(adcontrol.togglepizza(1));
	}
	
	@Test
	public void testToggleIngredient() {
		
		Mockito.doReturn(IngredientStatus.AVAILABLE).when(menuservice).getIngredientStatus(1);
		Mockito.doNothing().when(menuservice).changeIngredientStatus(1,IngredientStatus.OUTOFSTOCK);
		assertNotNull(adcontrol.toggleingredient(1));
	}
}
