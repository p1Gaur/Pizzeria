package com.accolite.pizzeria.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.JoinColumn;

@Entity
@Table(name="cart")
public class Cart implements Serializable {

	private static final long serialVersionUID = -2054386655979281129L;
	
	@Id
	@Column(name="cartId", unique=true, nullable=false)
	@GeneratedValue(generator="gen")
	@GenericGenerator(name="gen", strategy="foreign", parameters={@Parameter(name="property", value="customer")})
	private int cartId;
	
	
	@OneToMany(fetch = FetchType.EAGER,cascade=javax.persistence.CascadeType.ALL)
	@JoinTable(name="cart_item" , joinColumns = { @JoinColumn(name="cartId")},inverseJoinColumns= {@JoinColumn(name="cartItemId")})
	private Set<CartItem> cartItems = new HashSet<CartItem>();

	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Customer customer;
	
	@Column(name="cartAmount")
	private double cartAmount;
	
	public Cart() {
		
	}

	public Cart(int cartId, Set<CartItem> cartItems) {
		super();
		this.cartId = cartId;
		this.cartItems = cartItems;
		this.cartAmount=0.00;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	
	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getCartAmount() {
		return cartAmount;
	}

	public void setCartAmount(double cartAmount) {
		this.cartAmount = cartAmount;
	}
	
}
