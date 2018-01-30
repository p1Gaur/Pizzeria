package com.accolite.pizzeria.testpack.dao;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.IngredientDaoImpl;
import com.accolite.pizzeria.model.Ingredient;


@RunWith(MockitoJUnitRunner.class)
public class TestIngredientDaoImpl {

	@Test
    public void testFindById() {	
		IngredientDaoImpl ingrDaoImpl=Mockito.mock(IngredientDaoImpl.class);
    	Ingredient ingr=new Ingredient();
    	when(ingrDaoImpl.getByKey(1)).thenReturn(ingr);
    	when(ingrDaoImpl.findById(1)).thenCallRealMethod();    	
    	assertSame(ingr,ingrDaoImpl.findById(1));
    }
	
	@Test
	public void testSaveIngredient() {
		IngredientDaoImpl ingrDaoImpl=Mockito.mock(IngredientDaoImpl.class);
    	Ingredient ingr=new Ingredient();
    	Mockito.doNothing().when(ingrDaoImpl).persist(ingr);
    	Mockito.doCallRealMethod().when(ingrDaoImpl).saveIngredient(ingr);
    	ingrDaoImpl.saveIngredient(ingr);
    	Mockito.verify(ingrDaoImpl).persist(ingr);
	}
	@Test
    public void testGetAllIngredient() {
    	List<Ingredient> ingrList=new ArrayList<>();
    	IngredientDaoImpl ingrDaoImpl=Mockito.mock(IngredientDaoImpl.class);
    	Criteria criteria = Mockito.mock(Criteria.class);
    	Mockito.doReturn(criteria).when(ingrDaoImpl).createEntityCriteria();
    	Mockito.doReturn(ingrList).when(criteria).list();
    	
    	Mockito.doCallRealMethod().when(ingrDaoImpl).getAllIngredient();
    	assertSame(ingrList,ingrDaoImpl.getAllIngredient());
    }
}
