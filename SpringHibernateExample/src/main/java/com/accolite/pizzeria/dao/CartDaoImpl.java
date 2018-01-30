package com.accolite.pizzeria.dao;


import org.springframework.stereotype.Repository;

import com.accolite.pizzeria.model.Cart;
import com.accolite.pizzeria.model.CartItem;

@Repository("cartDao")
public class CartDaoImpl extends AbstractDao<Integer, Cart> implements CartDao {

	/*
	 * gets the cart having specified cart id
	 * @see com.accolite.pizzeria.dao.CartDao#findById(int)
	 * 
	 * @param cartId
	 * 
	 * @return Cart object
	 */
	public Cart findById(int id) {
		return getByKey(id);
	}
	/*
	 * adds the new CartItem to existing cart
	 * @see com.accolite.pizzeria.dao.CartDao#addToCart(com.accolite.pizzeria.model.Cart, com.accolite.pizzeria.model.CartItem)
	 * 
	 * @param Cart Object
	 * @param CartItem object
	 */
	public void addToCart(Cart cart,CartItem cartItem) {
		cart.getCartItems().add(cartItem);
		persist(cart);
	}
	/*
	 * saves the cart in database
	 * 	 * @see com.accolite.pizzeria.dao.CartDao#saveCart(com.accolite.pizzeria.model.Cart)
	 */
	public void saveCart(Cart cart) {
		persist(cart);
	}
	/*
	 * creates new cart
	 * @see com.accolite.pizzeria.dao.CartDao#createEmptyCart()
	 */
	public Cart createEmptyCart() {
		return new Cart();
	}
}
