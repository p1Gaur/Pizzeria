package com.accolite.pizzeria.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="cartitem")
public class CartItem implements Serializable{
	private static final long serialVersionUID = -205438665512232369L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cartItemId")
	private int cartItemId;
	
	@Column(name="menuItemId")
	private int menuItemId;
	
	@Column(name="quantity")
	private int quantity;

	public CartItem() {
		super();
	}
	
	public CartItem(int menuItemId, int quantity) {
		super();
		this.menuItemId = menuItemId;
		this.quantity = quantity;
	}
	public CartItem(int cartItemId, int menuItemId, int quantity) {
		super();
		this.cartItemId = cartItemId;
		this.menuItemId = menuItemId;
		this.quantity = quantity;
	}

	public int getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}

	public int getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(int menuItem) {
		this.menuItemId = menuItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
