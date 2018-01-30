package com.accolite.pizzeria.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="orderdetails")
public class Order implements Serializable{
	
	private static final long serialVersionUID = -2054386655979281968L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="orderId")
	private int orderId;
	
	@Column(name="totAmount")
	private double amount;
	
	@Column(name="placingtime")
	private Date placingTime;
	
	@Enumerated(EnumType.ORDINAL)
	private OrderStatus ordStatus;
	
	@Column(name="deliveryAddress")
	private String deliveryAddress;
	
	@Column(name="contactNo")
	private String contactNo;
	
	@Column(name="custId")
	private int custId;

	@Column(name="feedback")
	private String feedback;
	
	@OneToMany(fetch = FetchType.EAGER,cascade=javax.persistence.CascadeType.ALL)
	@JoinTable(name="order_item" , joinColumns = { @JoinColumn(name="orderId")},inverseJoinColumns= {@JoinColumn(name="orderItemId")})
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();

	public Order() {
	}

	public Order(int orderId, double amount, Date placingTime, OrderStatus ordStatus, String deliveryAddress,
			String contactNo,int custId) {
		super();
		this.orderId = orderId;
		this.amount = amount;
		this.placingTime = placingTime;
		this.ordStatus = ordStatus;
		this.deliveryAddress = deliveryAddress;
		this.contactNo = contactNo;
		this.custId = custId;
		this.feedback=null;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getPlacingTime() {
		return placingTime;
	}

	public void setPlacingTime(Date placingTime) {
		this.placingTime = placingTime;
	}

	public OrderStatus getOrdStatus() {
		return ordStatus;
	}

	public void setOrdStatus(OrderStatus ordStatus) {
		this.ordStatus = ordStatus;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", amount=" + amount + ", placingTime=" + placingTime + ", ordStatus="
				+ ordStatus + ", deliveryAddress=" + deliveryAddress + ", contactNo=" + contactNo + "]";
	}
	
}
