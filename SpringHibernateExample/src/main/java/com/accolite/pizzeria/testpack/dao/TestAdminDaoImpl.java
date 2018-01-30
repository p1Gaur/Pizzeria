package com.accolite.pizzeria.testpack.dao;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.accolite.pizzeria.dao.AdminDaoImpl;
import com.accolite.pizzeria.model.Admin;

@RunWith(MockitoJUnitRunner.class)
public class TestAdminDaoImpl {

	@Test
    public void testFindById() {	
    	AdminDaoImpl adDaoImpl=Mockito.mock(AdminDaoImpl.class);
    	Admin ad=new Admin();
    	when(adDaoImpl.getByKey(1)).thenReturn(ad);
    	when(adDaoImpl.findById(1)).thenCallRealMethod();    	
    	assertSame(ad,adDaoImpl.findById(1));
    }
	
	@Test
	public void testFindAdminByUsername() {
		AdminDaoImpl adDaoImpl=Mockito.mock(AdminDaoImpl.class);
    	Admin ad=new Admin();
    	
    	String adminUserName="";
    	
    	Criteria criteria = Mockito.mock(Criteria.class);
    	Mockito.doReturn(criteria).when(adDaoImpl).createEntityCriteria();
    	Mockito.doReturn(criteria).when(criteria).add(Restrictions.eq("adminUserName", adminUserName));
    	Mockito.doReturn(ad).when(criteria).uniqueResult();
    	
    	Mockito.doCallRealMethod().when(adDaoImpl).findAdminByUsername(adminUserName);
    	
    	assertSame(ad,adDaoImpl.findAdminByUsername(adminUserName));
    	
	}
	
	@Test
	public void testSaveCustomer() {
		AdminDaoImpl adDaoImpl=Mockito.mock(AdminDaoImpl.class);
    	Admin ad=new Admin();
    	Mockito.doNothing().when(adDaoImpl).persist(ad);
    	Mockito.doCallRealMethod().when(adDaoImpl).saveAdmin(ad);
    	adDaoImpl.saveAdmin(ad);
    	Mockito.verify(adDaoImpl).persist(ad);
	}
}
