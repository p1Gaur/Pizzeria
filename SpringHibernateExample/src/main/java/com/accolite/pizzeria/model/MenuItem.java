package com.accolite.pizzeria.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

@Entity
@Table(name="menuitem")
public class MenuItem {

	private static final long serialVersionUID = -2054386655979281966L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="menuItemId")
	private int menuItemId;
	
	@Column(name="menuItemName")//,unique=true)
	private String menuItemName;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER,cascade=javax.persistence.CascadeType.ALL)
	@JoinTable(name="menuItem_ingr" , joinColumns = { @JoinColumn(name="menuItemId")},inverseJoinColumns= {@JoinColumn(name="ingrId")})
	Set<Ingredient> ingredients = new HashSet<Ingredient>();

	@Column(name="description")
	private String description;
	
	@Column(name="custom")
	private boolean custom;
	
	@Column(name="imageUrl")
	private String imageUrl;
	
	@Column(name="price")
	private double price;
	
	@Column(name="starPoints")
	private int starPoints;
	
	@Enumerated(EnumType.ORDINAL)
	private MenuItemStatus menuItemStatus;
	
	public MenuItem() {
		super();
	}

	public MenuItem(int menuItemId,String itemName, Set<Ingredient> ingredients) {
		super();
		this.menuItemName=itemName;
		this.menuItemId = menuItemId;
		this.ingredients = ingredients;
		this.menuItemStatus=MenuItemStatus.AVAILABLE;
	}

	public MenuItem(Set<Ingredient> ingredients,String itemName, String description,boolean custom,String imageUrl) {
		super();
		this.menuItemName=itemName;
		this.ingredients = ingredients;
		this.description = description;
		this.custom=custom;
		this.imageUrl=imageUrl;
		this.menuItemStatus=MenuItemStatus.AVAILABLE;
	}
	public MenuItem(String itemName, String description,boolean custom,String imageUrl) {
		super();
		this.menuItemName=itemName;
		this.ingredients = new HashSet<>();
		this.description = description;
		this.custom=custom;
		this.imageUrl=imageUrl;
		this.menuItemStatus=MenuItemStatus.AVAILABLE;
	}
	public MenuItem(int menuItemId,String itemName, Set<Ingredient> ingredients, String description,boolean custom,String imageUrl) {
		super();
		this.menuItemName=itemName;
		this.menuItemId = menuItemId;
		this.ingredients = ingredients;
		this.description = description;
		this.custom=custom;
		this.imageUrl=imageUrl;
		this.menuItemStatus=MenuItemStatus.AVAILABLE;
	}

	public int getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public MenuItemStatus getMenuItemStatus() {
		return menuItemStatus;
	}

	public void setMenuItemStatus(MenuItemStatus menuItemStatus) {
		this.menuItemStatus = menuItemStatus;
	}

	public double getPrice() {
		
		if(custom)
			return calculatePrice();
		else
			return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	private double calculatePrice() {
		
		double totAmount=0.00;
		for(Ingredient ingredient : ingredients) {
			totAmount+=ingredient.getPrice();
		}
		return totAmount;
	}
	
	public int getStarPoints() {
		return starPoints;
	}

	public void setStarPoints(int starPoints) {
		this.starPoints = starPoints;
	}

	@Override
	public String toString() {
		return "MenuItem [menuItemId=" + menuItemId + ", ingredients=" + ingredients + "]";
	}
	
}
