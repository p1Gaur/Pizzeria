package com.accolite.pizzeria.service;

import com.accolite.pizzeria.model.MenuItem;

public class CartInfo {

	private MenuItem menuItem;
	private int qty;
	
	public CartInfo() {
		
	}

	public CartInfo(MenuItem menuItem, int qty) {
		super();
		this.menuItem = menuItem;
		this.qty = qty;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}
