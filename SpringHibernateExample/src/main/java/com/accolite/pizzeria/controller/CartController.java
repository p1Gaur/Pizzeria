package com.accolite.pizzeria.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.service.CartInfo;
import com.accolite.pizzeria.service.CartService;
import com.accolite.pizzeria.service.CustomerService;
import com.accolite.pizzeria.service.MenuService;

@RestController
@RequestMapping("/cart")
public class CartController {

	private static final Logger logger = Logger.getLogger(CartController.class);
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CustomerService custService;
	
	@Autowired	
	private MenuService menuService;
	
	/*
	 * adds menu item to cart of customer
	 */
	@RequestMapping(value = "/additem/{menuid}/{userid}", method = RequestMethod.GET)
	public ResponseEntity<Void> addItemToCart(@PathVariable String menuid,@PathVariable String userid, HttpServletRequest request,UriComponentsBuilder ucBuilder) {

		logger.info("Creating menu " + menuid+" for userid:"+userid);
		if(custService.findById(Integer.parseInt(userid))!=null)
			cartService.addToCartOfUser(Integer.parseInt(userid),Integer.parseInt(menuid)); 
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/*
	 * sub quantity of cart item by one  from cart of cutomer
	 */
	@RequestMapping(value = "/subitem/{menuid}/{userid}", method = RequestMethod.GET)
	public ResponseEntity<Void> subItemFromCart(@PathVariable String menuid,@PathVariable String userid, HttpServletRequest request,UriComponentsBuilder ucBuilder) {
		
		if(custService.findById(Integer.parseInt(userid))!=null)
		{
			cartService.subFromCartOfUser(Integer.parseInt(userid),Integer.parseInt(menuid)); 
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/*
	 * Deletes item from cart
	 */
	@RequestMapping(value = "/delitem/{menuid}/{userid}", method = RequestMethod.GET)
	public ResponseEntity<Void> delItemFromCart(@PathVariable String menuid,@PathVariable String userid, HttpServletRequest request,UriComponentsBuilder ucBuilder) {

		if(custService.findById(Integer.parseInt(userid))!=null)
		{
			cartService.deleteItemFromCart(Integer.parseInt(userid),Integer.parseInt(menuid)); 
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/*
	 * returns all the cart items in cart
	 */
	@RequestMapping(value = { "/getall/{userid}" }, method = RequestMethod.GET)
	public ResponseEntity<List<CartInfo>> listcart(@PathVariable String userid) {
		logger.info("getting menu items");
		List<CartInfo> cartitems = cartService.getAllCartItemsOfCustomer(Integer.parseInt(userid));
		if(cartitems==null){
            return new ResponseEntity<List<CartInfo>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<CartInfo>>(cartitems, HttpStatus.OK);
	}

	/*
	 * gets total amount of cart
	 */
	@RequestMapping(value = { "/getAmt/{userid}" }, method = RequestMethod.GET)
	public ResponseEntity<Double> getTotalAmount(@PathVariable String userid) {
		logger.info("retrieving cart amount");
		Double amt = cartService.getTotalAmountOfCart(Integer.parseInt(userid));
		if(amt<0.00){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(amt, HttpStatus.OK);
	}
	
	/*
	 * creates and adds custom menu item to cart of customer
	 */
	@RequestMapping(value = "/addcustom/{userid}", method = RequestMethod.POST)
    public ResponseEntity<Void> addCustomMenuItemToCart(@RequestBody int[] ing,@PathVariable int userid,UriComponentsBuilder ucBuilder) {
        
        if(custService.findById(userid)==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        
        MenuItem m1=new MenuItem("Custom Pizza","Custom Pizza",true,"/resources/m1");
		
        menuService.addMenuItem(m1);
		for(int i=0;i<ing.length;i++)
        {
        	logger.info(ing[i]);
    		menuService.addIngredientToItem(m1.getMenuItemId(),ing[i]);
        }
		//add menuitem to cart
		cartService.addToCartOfUser(userid,m1.getMenuItemId());
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
