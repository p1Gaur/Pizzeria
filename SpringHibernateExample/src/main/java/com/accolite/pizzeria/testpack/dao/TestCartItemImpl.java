package com.accolite.pizzeria.testpack.dao;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.CartItemDaoImpl;
import com.accolite.pizzeria.model.CartItem;

@RunWith(MockitoJUnitRunner.class)
public class TestCartItemImpl {

	@Test
    public void testFindById() {	
    	CartItemDaoImpl cartItemDaoImpl=Mockito.mock(CartItemDaoImpl.class);
    	CartItem cartItem=new CartItem();
    	when(cartItemDaoImpl.getByKey(1)).thenReturn(cartItem);
    	when(cartItemDaoImpl.findById(1)).thenCallRealMethod();    	
    	assertSame(cartItem,cartItemDaoImpl.findById(1));
    }
	
	@Test
	public void testDeleteCartItem() {
		CartItemDaoImpl cartItemDaoImpl=Mockito.mock(CartItemDaoImpl.class);
		CartItem cartItem=new CartItem();
		Mockito.doNothing().when(cartItemDaoImpl).delete(cartItem);
		Mockito.doCallRealMethod().when(cartItemDaoImpl).deleteCartItem(cartItem);
		
		cartItemDaoImpl.deleteCartItem(cartItem);
		Mockito.verify(cartItemDaoImpl).delete(cartItem);
	}
}
