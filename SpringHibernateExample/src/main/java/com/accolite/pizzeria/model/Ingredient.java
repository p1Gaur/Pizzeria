package com.accolite.pizzeria.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.accolite.pizzeria.model.MenuItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ingredient")
public class Ingredient {
	
	private static final long serialVersionUID = -2054386655979281967L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ingrId")
	private int ingrId;
	
	@Column(name="ingrName")
	private String ingrName;
	
	@Column(name="price")
	private double price;
	
	@Column(name="ingredientStatus")
	private IngredientStatus ingredientStatus;
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="ingredients")
	Set<MenuItem> menuItems=new HashSet<>();

	public Ingredient() {
		super();
	}
	
	public Ingredient(String ingrName, double price, IngredientStatus ingredientStatus) {
		super();
		this.ingrName = ingrName;
		this.price = price;
		this.ingredientStatus = ingredientStatus;
	}
	public Ingredient(int ingrId, String ingrName, double price, IngredientStatus ingredientStatus) {
		super();
		this.ingrId = ingrId;
		this.ingrName = ingrName;
		this.price = price;
		this.ingredientStatus = ingredientStatus;
	}

	public int getIngrId() {
		return ingrId;
	}

	public void setIngrId(int ingrId) {
		this.ingrId = ingrId;
	}

	public String getIngrName() {
		return ingrName;
	}

	public void setIngrName(String ingrName) {
		this.ingrName = ingrName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public IngredientStatus getIngredientStatus() {
		return ingredientStatus;
	}

	public void setIngredientStatus(IngredientStatus ingredientStatus) {
		this.ingredientStatus = ingredientStatus;
	}

	public Set<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
