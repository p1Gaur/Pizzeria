package com.accolite.pizzeria.testpack.dao;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.CartDaoImpl;
import com.accolite.pizzeria.dao.MenuDaoImpl;
import com.accolite.pizzeria.model.Cart;
import com.accolite.pizzeria.model.CartItem;

@RunWith(MockitoJUnitRunner.class)
public class TestCartDaoImpl {

	@InjectMocks
    private CartDaoImpl cartDaoImpl;

    @Mock
    private MenuDaoImpl menuDaoImpl;
    
    @Test
    public void testFindById() {	
    	cartDaoImpl=Mockito.mock(CartDaoImpl.class);
    	Cart ad=new Cart();
    	when(cartDaoImpl.getByKey(1)).thenReturn(ad);
    	when(cartDaoImpl.findById(1)).thenCallRealMethod();    	
    	assertSame(ad,cartDaoImpl.findById(1));
    }
    
    @Test
    public void testAddToCart() {
    	cartDaoImpl=Mockito.mock(CartDaoImpl.class);
    	Cart cart=new Cart();
    	CartItem cartItem = new CartItem();
    	
    	cartDaoImpl.addToCart(cart, cartItem);
    	Mockito.doNothing().when(cartDaoImpl).persist(cart);
    	Mockito.doCallRealMethod().when(cartDaoImpl).addToCart(cart, cartItem);
    	
    	cartDaoImpl.addToCart(cart, cartItem);
    	assertTrue(cart.getCartItems().contains(cartItem));
    }
    
    @Test
	public void testSaveCustomer() {
		cartDaoImpl=Mockito.mock(CartDaoImpl.class);
		Cart cart=new Cart();
    	Mockito.doNothing().when(cartDaoImpl).persist(cart);
    	Mockito.doCallRealMethod().when(cartDaoImpl).saveCart(cart);
    	cartDaoImpl.saveCart(cart);
    	Mockito.verify(cartDaoImpl).persist(cart);
	}
    
}
