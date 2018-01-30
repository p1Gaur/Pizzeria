package com.accolite.pizzeria.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.accolite.pizzeria.model.Ingredient;

@Repository("ingrDao")
public class IngredientDaoImpl extends AbstractDao<Integer, Ingredient> implements IngredientDao{

	public Ingredient findById(int id) {
		return getByKey(id);
	}
	public void saveIngredient(Ingredient ingredient) {
		persist(ingredient);
	}
	@SuppressWarnings("unchecked")
	public List<Ingredient> getAllIngredient() {
		Criteria criteria = createEntityCriteria();
		return (List<Ingredient>) criteria.list();
	}
}
