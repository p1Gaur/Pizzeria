
package com.accolite.pizzeria.controller;

import java.util.HashSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.accolite.pizzeria.model.Admin;
import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.model.Customer;
import com.accolite.pizzeria.model.Ingredient;
import com.accolite.pizzeria.model.IngredientStatus;
import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.service.AdminService;
import com.accolite.pizzeria.service.CartService;
import com.accolite.pizzeria.service.CustomerService;
import com.accolite.pizzeria.service.MenuService;
import com.accolite.pizzeria.service.OrderService;

@Controller
@RequestMapping("/")
public class IndexController {
	private static final Logger logger = Logger.getLogger(IndexController.class);
	private static final String PAWAN = "pawan"; 
	@Autowired
	CustomerService customerservice;
	@Autowired
	MenuService menuservice;
	@Autowired
	CartService cartservice;
	@Autowired 
	AdminService adminservice;
	@Autowired 
	OrderService orderservice;
	
	@RequestMapping(value = {"/test" }, method = RequestMethod.GET)
	public String registerCustomer() {

		return "UserManagement";
	}
	/*
	 * testing purpose
	 */
	@RequestMapping(value = {"/" }, method = RequestMethod.GET)
	public String myMain() {
		//initializing some values in tables for testing purpose
		if(customerservice.isCustomerUserNameUnique(PAWAN))
		{		
		Customer customer=new Customer(PAWAN,PAWAN,"pawan@p1.com","1237121231","Delhi","passpawan");
		customerservice.saveCustomer(customer);
		Ingredient i1=new Ingredient("Thin Crust Base",10.00,IngredientStatus.AVAILABLE);
		Ingredient i2=new Ingredient("Cheese",20.00,IngredientStatus.AVAILABLE);
		Ingredient i3=new Ingredient("Onion",10.00,IngredientStatus.AVAILABLE);
		//Ingredient i4=new Ingredient("Capsicum",10.00,IngredientStatus.AVAILABLE)
		HashSet<Ingredient> list1=new HashSet<Ingredient>();
		list1.add(i1);
		list1.add(i2);
		list1.add(i3);
	
		MenuItem m1=new MenuItem(list1,"Onion Pizza","Onion Pizza",false,"/resources/m1");
		m1.setPrice(10000);
		menuservice.addMenuItem(m1);
		Coupon coupon=new Coupon("Adding bonus",500.00);
		adminservice.addCouponToCustomer(customer.getCustId(),coupon);
		Admin admin=new Admin(PAWAN,PAWAN);
		adminservice.addAdmin(admin);
		logger.info("Initialized with default values");			
		}
		
		return "index";
	}
	
	@RequestMapping(value = {"/main" }, method = RequestMethod.GET)
	public String sucess() {
		return "menu";
	}

	@RequestMapping(value = {"/cart" }, method = RequestMethod.GET)
	public String cart() {
		return "cart";
	}

	@RequestMapping(value = {"/failure" }, method = RequestMethod.GET)
	public String success() {
		return "failure";
	}

}
