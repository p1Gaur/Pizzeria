package com.accolite.pizzeria.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "orderitem")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = -2054386655979281965L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="orderItemId")
	private int orderItemId;
	
	@Column(name="menuItemId")
	private int menuItemId;
	
	@Column(name="quantity")
	private int quantity;

	public OrderItem() {
		super();
	}
	
	public OrderItem(int menuItemId, int quantity) {
		super();
		this.menuItemId = menuItemId;
		this.quantity = quantity;
	}
	public OrderItem(int orderItemId, int menuItemId, int quantity) {
		super();
		this.orderItemId = orderItemId;
		this.menuItemId = menuItemId;
		this.quantity = quantity;
	}

	public int getCartItemId() {
		return orderItemId;
	}

	public void setCartItemId(int orderItemId) {
		this.orderItemId = orderItemId;
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
