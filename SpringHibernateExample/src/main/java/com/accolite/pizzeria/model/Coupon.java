package com.accolite.pizzeria.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="coupon")
public class Coupon implements Serializable {
	
	@Id
	@Column(name="couponId")
	private String couponId;
	
	@Column(name="discountAmount")
	private double discountAmount;

	public Coupon() {
		super();
	}

	public Coupon(String couponId,double discountAmount) {
		super();
		this.couponId = couponId;
		this.discountAmount = discountAmount;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	
}
