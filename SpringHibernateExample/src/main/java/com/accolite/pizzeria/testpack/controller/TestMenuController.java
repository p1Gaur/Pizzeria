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

import com.accolite.pizzeria.controller.MenuController;
import com.accolite.pizzeria.model.Ingredient;
import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.service.IngredientService;
import com.accolite.pizzeria.service.MenuService;

@RunWith(MockitoJUnitRunner.class)
public class TestMenuController {

	@InjectMocks
	private MenuController menucontrol;
	
	@Mock
	private MenuService menuservice;
	
	@Mock
	private IngredientService ingservice;
	
	@Test
	public void testListMenuItem() {
		List<MenuItem> menulist=new ArrayList<>();
		Mockito.doReturn(menulist).when(menuservice).getAllFixedMenuItems();
		assertNotNull(menucontrol.listMenuItem());
	}
	
	@Test
	public void testListIng() {
		List<Ingredient> inglist=new ArrayList<>();
		Mockito.doReturn(inglist).when(ingservice).getAllIngredients();
		assertNotNull(menucontrol.listIng());
	}
	
}
