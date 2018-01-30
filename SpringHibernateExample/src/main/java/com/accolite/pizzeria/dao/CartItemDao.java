package com.accolite.pizzeria.dao;

import com.accolite.pizzeria.model.CartItem;

public interface CartItemDao {
	CartItem findById(int id);
	public void deleteCartItem(CartItem cartItem);
}
