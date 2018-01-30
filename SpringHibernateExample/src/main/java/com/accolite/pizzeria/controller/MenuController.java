package com.accolite.pizzeria.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.pizzeria.model.Ingredient;
import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.service.IngredientService;
import com.accolite.pizzeria.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

	private static final Logger logger = Logger.getLogger(MenuController.class);
	
	@Autowired
	private MenuService menuservice;
	
	@Autowired
	private IngredientService ingservice;
	
	/*
	 * returns all non custom menu items
	 */
	@RequestMapping(value = { "/getall" }, method = RequestMethod.GET)
	public ResponseEntity<List<MenuItem>> listMenuItem() {
		logger.info("getting menu items");
		List<MenuItem> menuitems = menuservice.getAllFixedMenuItems();
		if(menuitems.isEmpty()){
            return new ResponseEntity<List<MenuItem>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<MenuItem>>(menuitems, HttpStatus.ACCEPTED);
	}	

	/*
	 * returns all ingredients used for custom pizza.
	 */
	@RequestMapping(value = { "/getallIng" }, method = RequestMethod.GET)
	public ResponseEntity<List<Ingredient>> listIng() {
		logger.info("getting ingredient");
		List<Ingredient> ingitems = ingservice.getAllIngredients();
		for (Ingredient ingredient : ingitems) {
			logger.info(ingredient);
		}
		if(ingitems.isEmpty()){
			return new ResponseEntity<List<Ingredient>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
		return new ResponseEntity<List<Ingredient>>(ingitems, HttpStatus.ACCEPTED);
	}	
}
