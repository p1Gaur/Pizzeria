package com.accolite.pizzeria.dao;

import com.accolite.pizzeria.model.Coupon;

public class CouponDaoImpl extends AbstractDao<Integer, Coupon> implements CouponDao{

	/*
	 * gets coupon having specified coupon id
	 * @see com.accolite.pizzeria.dao.CouponDao#findById(int)
	 * 
	 * @param couponId
	 * 
	 * @return Coupon object
	 */
	public Coupon findById(int id) {
		return getByKey(id);
	}
}
