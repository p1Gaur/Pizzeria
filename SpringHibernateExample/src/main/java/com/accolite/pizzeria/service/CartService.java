package com.accolite.pizzeria.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accolite.pizzeria.dao.CartDao;
import com.accolite.pizzeria.dao.CartItemDao;
import com.accolite.pizzeria.dao.CustomerDao;
import com.accolite.pizzeria.dao.MenuDao;
import com.accolite.pizzeria.model.Cart;
import com.accolite.pizzeria.model.CartItem;
import com.accolite.pizzeria.model.Customer;
import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.service.CartInfo;

@Service("cartService")
@Transactional
public class CartService {

	@Autowired
	private CustomerDao custDao;
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private CartItemDao cartItemDao;
	
	@Autowired
	private MenuDao menuDao;
	
	
	/*
	 * gets all cart items present in Customer cart
	 * 
	 * @param CustomerId
	 * 
	 * @return list of CartInfo object
	 */
	public List<CartInfo> getAllCartItemsOfCustomer(int custId) {
		
		Customer customer=custDao.findById(custId);
		if(customer==null)
			return new ArrayList<>();
		List<CartItem> cartItems=new ArrayList<CartItem>( customer.getCart().getCartItems() );
		
		List<CartInfo> cartList=new ArrayList<CartInfo>();
		
		for(CartItem cartItem : cartItems) {
			int menuItemId = cartItem.getMenuItemId();
			MenuItem menuItem=menuDao.findById(menuItemId);
			
			cartList.add(new CartInfo(menuItem,cartItem.getQuantity()));
		}
		return cartList;
	}
	
	/*
	 * gets cart object of Customer having specified CustomerId
	 * 
	 * @param CustomerId
	 * 
	 * @return Cart object
	 */
	public Cart getCartOfCustomer(int custId) {
		
		Customer customer=custDao.findById(custId);
		if(customer==null)
			return null;
		return cartDao.findById(customer.getCart().getCartId());
	}
	
	/*
	 *  add menuitem having specified menuitemId in cart of customer
	 *  
	 *  @param CustomerId
	 *  @param MenuItem id
	 *  
	 *  @return true if successfully added to customer cart otherwise false
	 */
	public boolean addToCartOfUser(int custId,int itemId) {
		
		Cart cart=this.getCartOfCustomer(custId);
		if(cart==null)
			return false;
		
		CartItem cartItem=null;
		cartItem=this.getCartItemFromCart(cart, itemId);
		if(cartItem!=null) {
			cartItem.setQuantity(cartItem.getQuantity()+1);
		}
		else {
			cartItem=new CartItem();
			cartItem.setMenuItemId(itemId);
			cartItem.setQuantity(1);
		}
		cartDao.addToCart(cart, cartItem);
		return true;
	}
	
	/*
	 * subtract quantity by one of cart Item from cart of Customer
	 * 
	 *  @param CaustomerId
	 *  @param MenuItemId
	 *  
	 *  @return true if successfully decremented otherwise false
	 */
	public boolean subFromCartOfUser(int custId,int itemId) {
		Cart cart=this.getCartOfCustomer(custId);
		if(cart==null)
			return false;
		
		CartItem cartItem=null;
		cartItem=this.getCartItemFromCart(cart, itemId);
		if(cartItem==null||cartItem.getQuantity()<=0)
		{
			return false;
		}
		cartItem.setQuantity(cartItem.getQuantity()-1);
		cartDao.addToCart(cart, cartItem);
		return true;		
	}

	/*
	 * gets CartItem having specified MenuItemId from cart
	 * 
	 * @param Cart object
	 * @param MenuItemId
	 * 
	 * @return CartItem object
	 */
	public CartItem getCartItemFromCart(Cart cart,int itemId) {
		Set<CartItem> cartItemList=cart.getCartItems();
		for(CartItem cartItem : cartItemList) {
			if(cartItem.getMenuItemId() == itemId)
				return cartItem;
		}
		return null;
	}
	
	/*
	 * deletes all the CartItems from cart of customer
	 * 
	 * @param CustomerId
	 */
	public void clearCartOfUser(int custId) {
		
		Customer customer=custDao.findById(custId);
		if(customer==null)
			return;
		Set<CartItem> cartItemsList=customer.getCart().getCartItems();
		customer.getCart().setCartItems(new HashSet<CartItem>());
		custDao.saveCustomer(customer);
		
		for(CartItem cartItem : cartItemsList) {
			cartItemDao.deleteCartItem(cartItem);
		}
	}
	
	/*
	 * set the quantity of MenuItem in cart of Customer
	 * 
	 * @param CustomerId
	 * @param MenuItemId
	 * @param newQuantity
	 */
	public void setCartItemQuantity(int custId,int itemId,int qty) {
		
		Cart cart=this.getCartOfCustomer(custId);
		if(cart==null)
			return;
		Set<CartItem> cartItems=cart.getCartItems();
		
		for(CartItem cartItem :cartItems) {
			if(cartItem.getMenuItemId() == itemId) {
				cartItem.setQuantity(qty);
			}
		}
		cartDao.saveCart(cart);
	}
	
	/*
	 * deletes CartItem from cart of Customer
	 * 
	 * @param CustomerId
	 * @param MenuItemId
	 */
	public void deleteItemFromCart(int custId,int itemId) {
		Cart cart=this.getCartOfCustomer(custId);
		if(cart==null)
			return;
		
		Set<CartItem> cartItems=cart.getCartItems();
		
		for(CartItem cartItem :cartItems) {
			if(cartItem.getMenuItemId() == itemId) {
				cartItems.remove(cartItem);
				cartDao.saveCart(cart);
				cartItemDao.deleteCartItem(cartItem);
				return;
			}
		}
	}
	
	/*
	 * calculates the total amount of Cart
	 * 
	 * @param CustomerId
	 * 
	 * @return Total amount of cart
	 */
	public double getTotalAmountOfCart(int custId) {
		
		Cart cart=this.getCartOfCustomer(custId);
		if(cart==null)
			return 0.00;
		Set<CartItem> cartItems=cart.getCartItems();
		
		double totAmount=0.00;
		for(CartItem cartItem :cartItems) {
			MenuItem menuItem=menuDao.findById(cartItem.getMenuItemId());
			totAmount+=(menuItem.getPrice()*cartItem.getQuantity());
		}
		return totAmount;
	}

}
