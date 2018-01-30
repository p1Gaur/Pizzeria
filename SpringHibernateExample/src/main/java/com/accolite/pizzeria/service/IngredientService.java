package com.accolite.pizzeria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accolite.pizzeria.dao.IngredientDao;
import com.accolite.pizzeria.model.Ingredient;

@Service("ingredientService")
@Transactional
public class IngredientService {
	
	@Autowired
	private IngredientDao ingrDao;
	
	/*
	 * gets all saved ingredients from database
	 * 
	 * @return List of ingredients
	 */
	public List<Ingredient> getAllIngredients() {
		return ingrDao.getAllIngredient();
	}

	/*
	 * gets the ingredient having specified Ingredient id.
	 * 
	 * @param IngredientId
	 * 
	 * @return Ingredient object
	 */
	public Ingredient findByIngredientId(int id)
	{
		return ingrDao.findById(id);
	}
}
