package com.accolite.pizzeria.testpack.service;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.IngredientDao;
import com.accolite.pizzeria.model.Ingredient;
import com.accolite.pizzeria.service.IngredientService;

@RunWith(MockitoJUnitRunner.class)
public class TestIngredientService {

	@InjectMocks
	private IngredientService myiservice;
	
	@Mock
	private IngredientDao myingrDao;
	
	@Test
	public void testGetAllFixedMenuItems() {
		List<Ingredient> ingrList= new ArrayList<>();
		Mockito.doReturn(ingrList).when(myingrDao).getAllIngredient();
		assertSame(ingrList,myiservice.getAllIngredients());
	}
	@Test
	public void testFindByIngredientId() {
		Ingredient ingr=new Ingredient();
		Mockito.doReturn(ingr).when(myingrDao).findById(1);
		
		assertSame(ingr,myiservice.findByIngredientId(1));
	}
}
