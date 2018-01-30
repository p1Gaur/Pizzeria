package com.accolite.pizzeria.testpack.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.annotations.BeforeMethod;

import com.accolite.pizzeria.dao.CartDaoImpl;
import com.accolite.pizzeria.dao.CartItemDao;
import com.accolite.pizzeria.dao.CustomerDao;
import com.accolite.pizzeria.dao.MenuDao;
import com.accolite.pizzeria.model.Cart;
import com.accolite.pizzeria.model.CartItem;
import com.accolite.pizzeria.model.Customer;
import com.accolite.pizzeria.model.MenuItem;
import com.accolite.pizzeria.service.CartInfo;
import com.accolite.pizzeria.service.CartService;

@RunWith(MockitoJUnitRunner.class)
public class TestCartService {

	@InjectMocks
	private CartService mycService;
	
	@Mock
	private CustomerDao custDao;
	
	@Mock
	private CartDaoImpl cartDao;
	
	@Mock
	private CartItemDao cartItemDao;
	
	@Mock
	private MenuDao menuDao;
	
	@BeforeMethod
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testGetAllCartItemsOfCustomer() {
		
		Set<CartItem> cartItems=new HashSet<>();
		CartItem citem=new CartItem();
		citem.setMenuItemId(1);
		cartItems.add(citem);
		
		Customer cust=new Customer();
		cust.setCustId(1);
		cust.setCart(new Cart());
		cust.getCart().setCartItems(cartItems);
		
		MenuItem menu=new MenuItem();
		Mockito.doReturn(cust).when(custDao).findById(1);
		Mockito.doReturn(menu).when(menuDao).findById(1);
		
		List<CartInfo> cartList=mycService.getAllCartItemsOfCustomer(1);
		for(CartInfo cinfo : cartList)
			assertSame(menu,cinfo.getMenuItem());
	}
	
	@Test
	public void testAddToCartOfUser() {
		Customer customer=new Customer();
		
		Cart cart=new Cart();
		cart.setCartId(1);
		customer.setCart(cart);
		
		CartItem cartItem=new CartItem();
		cartItem.setCartItemId(1);
		cartItem.setQuantity(1);
		
		Set<CartItem> citems=new HashSet<>();
		citems.add(cartItem);
		cart.setCartItems(citems);
		
		
		Mockito.doReturn(customer).when(custDao).findById(1);
		Mockito.doReturn(cart).when(cartDao).findById(1);
		
		Mockito.doNothing().when(cartDao).addToCart(cart ,cartItem);
		
		assertTrue(mycService.addToCartOfUser(1, 1));
	}
	
	@Test
	public void testGetCartItemFromCart() {
		CartItem cartItem=new CartItem();
		cartItem.setMenuItemId(1);
		Set<CartItem> citems=new HashSet<>();
		citems.add(cartItem);
		
		Cart cart=new Cart();
		cart.setCartItems(citems);
		
		assertSame(cartItem,mycService.getCartItemFromCart(cart, 1));
		
	}
	
	@Test
	public void testSetCartItemQuantity() {
		
		
		Customer customer=new Customer();
		
		CartItem cartItem=new CartItem();
		cartItem.setMenuItemId(1);
		Set<CartItem> citems=new HashSet<>();
		citems.add(cartItem);
		
		Cart cart=new Cart();
		cart.setCartId(1);
		cart.setCartItems(citems);
		
		customer.setCart(cart);
		
		Mockito.doReturn(customer).when(custDao).findById(1);
		Mockito.doReturn(cart).when(cartDao).findById(1);
		Mockito.doNothing().when(cartDao).saveCart(cart);
		mycService.setCartItemQuantity(1, 1, 5);
		assertEquals(5,cartItem.getQuantity());
	}
	
	@Test
	public void testGetTotalAmountOfCart() {
		Customer customer=new Customer();
		
		CartItem cartItem=new CartItem();
		cartItem.setMenuItemId(1);
		cartItem.setQuantity(2);
		Set<CartItem> citems=new HashSet<>();
		citems.add(cartItem);
		
		Cart cart=new Cart();
		cart.setCartId(1);
		cart.setCartItems(citems);
		
		customer.setCart(cart);
		
		MenuItem menu=new MenuItem();
		menu.setMenuItemId(1);
		menu.setPrice(200.00);
		
		Mockito.doReturn(customer).when(custDao).findById(1);
		Mockito.doReturn(cart).when(cartDao).findById(1);
		Mockito.doReturn(menu).when(menuDao).findById(1);
		
		assertEquals(400.00,mycService.getTotalAmountOfCart(1),0.5);
	}
}
