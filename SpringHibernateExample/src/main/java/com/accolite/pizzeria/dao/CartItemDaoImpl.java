package com.accolite.pizzeria.dao;

import org.springframework.stereotype.Repository;

import com.accolite.pizzeria.model.CartItem;

@Repository("cartitemDao")
public class CartItemDaoImpl extends AbstractDao<Integer, CartItem> implements CartItemDao{
	
	/*
	 * gets the cartItem having specified cartitem id
	 * @see com.accolite.pizzeria.dao.CartItemDao#findById(int)
	 * 
	 * @param cartItemId
	 * 
	 * @return CartItem Object
	 */
	public CartItem findById(int id) {
		return getByKey(id);
	}
	
	/*
	 * deletes the cartItem from database
	 * @see com.accolite.pizzeria.dao.CartItemDao#deleteCartItem(com.accolite.pizzeria.model.CartItem)
	 * 
	 * @param CartItem object
	 */
	public void deleteCartItem(CartItem cartItem) {
		delete(cartItem);
	}
}
