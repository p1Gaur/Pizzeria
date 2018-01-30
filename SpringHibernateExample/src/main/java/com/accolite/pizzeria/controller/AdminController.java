package com.accolite.pizzeria.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accolite.pizzeria.model.Admin;
import com.accolite.pizzeria.model.Ingredient;
import com.accolite.pizzeria.model.IngredientStatus;
import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.model.MenuItemStatus;
import com.accolite.pizzeria.model.Order;
import com.accolite.pizzeria.service.AdminService;
import com.accolite.pizzeria.service.Feedback;
import com.accolite.pizzeria.service.MenuService;
import com.accolite.pizzeria.service.OrderService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = Logger.getLogger(AdminController.class);
	private static final String LOGIN_FAIL = "loginfail";
	private static final String REDIRECT_ADMIN = "redirect: http://localhost:7999/pro/#!/admin";
	private static final String REDIRECT_ADMIN_MAIN = "redirect:http://localhost:7999/pro/#!/adminmain";
	@Autowired
	AdminService service;
	
	@Autowired
	OrderService orderservice;
	
	@Autowired
	MenuService menuservice;
	
	/*
	 * Handles POST request for admin login
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView handleFormRequest(HttpServletRequest request,HttpServletResponse response) throws IOException {
				
		logger.info("Admin Logging in");
		
		boolean flag=true;
		String adminUserName=request.getParameter("userName");
		String adminPassword=request.getParameter("password");
		if(adminUserName.equals("") || adminUserName.length()>50) {
			flag=false;
		}
		if(adminPassword.equals("") || adminPassword.length()>50) {
			flag=false;
		}
		if(!flag) {	//flag==false
			request.getSession().setAttribute(LOGIN_FAIL, "true");
			return new ModelAndView(REDIRECT_ADMIN);
		}
		//check for login
		Admin admin=service.getAdmin(request.getParameter("userName"), request.getParameter("password"));
		if(admin!=null) {
			request.getSession().removeAttribute(LOGIN_FAIL);
			request.getSession().removeAttribute("registrationfail");
			request.getSession().setAttribute("Adminid", admin.getAdminId());
			request.getSession().setAttribute("SessionCreated", "true");
			logger.info("Success login");
			response.getWriter().println(admin.getAdminId());
			return new ModelAndView(REDIRECT_ADMIN_MAIN);
		}
		request.getSession().setAttribute(LOGIN_FAIL, "true");
		return new ModelAndView(REDIRECT_ADMIN);					
	}
			
	/*
	 * returns  all the orders stored in database
	 */
	@RequestMapping(value = { "/getallorder" }, method = RequestMethod.GET)
	public ResponseEntity<List<Order>> listOrderItem() {
		logger.info("getting order items");
		List<Order> items = orderservice.getAllOrders();
		if(items.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<>(items, HttpStatus.OK);
	}	
	
	/*
	 * returns all the feedbacks stored in database
	 */
	@RequestMapping(value = { "/getallfeedback" }, method = RequestMethod.GET)
	public ResponseEntity<List<Feedback>> listFeedback() {
		logger.info("getting feedback items");
		List<Feedback> items = orderservice.getAllFeedbacks();
		if(items==null||items.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<>(items, HttpStatus.OK);
	}	
	/*
	 * adds new ingredient in the database(Only admin can do that).		
	 */
	@RequestMapping(value = "/addingredient", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView adding(HttpServletRequest request,HttpServletResponse response)  {
				
		logger.info("Adding Ingredient");
		String name=request.getParameter("ingredientName");
		double price=Double.parseDouble(request.getParameter("price"));
		logger.info(name+price);
		
		if(name!=null && !name.equals("")&& name.length() < 50 && price > 0.00)
		{
			Ingredient i1=new Ingredient(name,price,IngredientStatus.AVAILABLE);
			menuservice.addNewIngredient(i1);
		}
		else
			request.getSession().setAttribute("addingredientfail", "true");
		return new ModelAndView(REDIRECT_ADMIN_MAIN);					
	}
	/*
	 * adds new menu item in the database		
	 */
	@RequestMapping(value = "/addmenuitem", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView addmenuitem(HttpServletRequest request,HttpServletResponse response)  {
				
		logger.info("Adding Menu Item");
		String name=request.getParameter("pizzaName");
		double price=Double.parseDouble(request.getParameter("price"));
		logger.info(name+price);
				
		if(name!=null && !name.equals("") && name.length() < 50 && price > 0.00)
		{
			MenuItem m1=new MenuItem(name,name,false,"/resources/m1");
			m1.setPrice(price);
			menuservice.addMenuItem(m1);
		}
		else
			request.getSession().setAttribute("addpizzafail", "true");
		return new ModelAndView(REDIRECT_ADMIN_MAIN);					
	}
			
	@RequestMapping(value = "/sendaddress", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView sendaddress(HttpServletRequest request,HttpServletResponse response)  {
				
		logger.info("Sending address:"+request.getParameter("location")+" of pizza of order"+request.getParameter("orderId"));
		//write code to send message to customer
		return new ModelAndView(REDIRECT_ADMIN_MAIN);					
	}
			
	/*
	 * Change the status of menu item
	 */
	@RequestMapping(value = { "/chgpizza/{pizzaid}" }, method = RequestMethod.GET)
	public ResponseEntity<Void> togglepizza(@PathVariable int pizzaid) {
		logger.info("changing pizza status");
				
		MenuItemStatus m=menuservice.getMenuItemStatus(pizzaid);
		if(m==null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if(m==MenuItemStatus.AVAILABLE)
			menuservice.changeMenuItemStatus(pizzaid,MenuItemStatus.UNAVAILABLE);
		else
			menuservice.changeMenuItemStatus(pizzaid,MenuItemStatus.AVAILABLE);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	/*
	 * change the status of ingredient
	 */
	@RequestMapping(value = { "/chging/{ingid}" }, method = RequestMethod.GET)
	public ResponseEntity<Void> toggleingredient(@PathVariable int ingid) {
		logger.info("changing ingredient status");
		IngredientStatus i=menuservice.getIngredientStatus(ingid);
		if(i==null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if(i==IngredientStatus.AVAILABLE)
			menuservice.changeIngredientStatus(ingid,IngredientStatus.OUTOFSTOCK);
		else
			menuservice.changeIngredientStatus(ingid,IngredientStatus.AVAILABLE);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	/*
	 * logout code		
	 */
	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		request.getSession().removeAttribute("SessionCreated");
		request.getSession().removeAttribute("Adminid");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
