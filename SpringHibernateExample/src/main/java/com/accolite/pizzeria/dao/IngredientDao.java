package com.accolite.pizzeria.dao;

import java.util.List;

import com.accolite.pizzeria.model.Ingredient;

public interface IngredientDao {
	public Ingredient findById(int id);
	public void saveIngredient(Ingredient ingredient);
	public List<Ingredient> getAllIngredient();
}
