package com.accolite.pizzeria.testpack.dao;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.CouponDaoImpl;
import com.accolite.pizzeria.model.Coupon;

@RunWith(MockitoJUnitRunner.class)
public class TestCouponDaoImpl {

	@Test
    public void testFindById() {	
		CouponDaoImpl copDaoImpl=Mockito.mock(CouponDaoImpl.class);
		Coupon c=new Coupon();
    	when(copDaoImpl.getByKey(1)).thenReturn(c);
    	when(copDaoImpl.findById(1)).thenCallRealMethod();    	
    	assertSame(c,copDaoImpl.findById(1));
    }
}
