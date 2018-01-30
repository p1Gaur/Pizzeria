package com.accolite.pizzeria.dao;


import com.accolite.pizzeria.model.Cart;
import com.accolite.pizzeria.model.CartItem;

public interface CartDao {
	
	Cart findById(int id);
	
	public void addToCart(Cart cart,CartItem cartItem);
	
	public void saveCart(Cart cart);
	
	public Cart createEmptyCart();
	
	
}
