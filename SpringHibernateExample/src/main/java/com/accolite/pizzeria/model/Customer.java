package com.accolite.pizzeria.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.annotations.Cascade;

import com.accolite.pizzeria.model.Coupon;

@Entity
@Table(name="customer")
public class Customer implements Serializable {

	
	private static final long serialVersionUID = -2054386655979281969L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="custId")
	private int custId;
	
	@Column(name="custName")
	private String custName;
	
	@Column(name="custUserName")
	private String custUserName;
	
	@Column(name="emailId")
	private String emailId;
	
	@Column(name="contactNo")
	private String contactNo;
	
	@Column(name="address")
	private String resAddress;
	
	@Column(name="password")
	private String password;

	@OneToOne(fetch = FetchType.EAGER ,mappedBy="customer")
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	private Cart cart;
	
	@OneToMany(fetch = FetchType.EAGER,cascade=javax.persistence.CascadeType.ALL)
	@JoinTable(name="cust_coupon" , joinColumns = { @JoinColumn(name="custId")},inverseJoinColumns= {@JoinColumn(name="couponId")})
	private Set<Coupon> coupons = new HashSet<Coupon>();
	
	public Customer() {
	}
	
	//Constructor for Customer
	public Customer(int custId, String custName, String custUserName, String emailId, String contactNo,
			String resAddress, String password) {
		super();
		this.custId = custId;
		this.custName = custName;
		this.custUserName = custUserName;
		this.emailId = emailId;
		this.contactNo = contactNo;
		this.resAddress = resAddress;
		this.password = password;
	}
	public Customer(String custName, String custUserName, String emailId, String contactNo,
			String resAddress, String password) {
		super();
		this.custName = custName;
		this.custUserName = custUserName;
		this.emailId = emailId;
		this.contactNo = contactNo;
		this.resAddress = resAddress;
		this.password = password;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustUserName() {
		return custUserName;
	}

	public void setCustUserName(String custUserName) {
		this.custUserName = custUserName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getResAddress() {
		return resAddress;
	}

	public void setResAddress(String resAddress) {
		this.resAddress = resAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", custName=" + custName + ", custUserName=" + custUserName + ", emailId="
				+ emailId + ", contactNo=" + contactNo + ", resAddress=" + resAddress + ", password=" + password + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

}
