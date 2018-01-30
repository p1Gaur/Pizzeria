package com.accolite.pizzeria.service;

import java.util.Date;

public class Feedback {

	private int orderId;
	private String feedbackText;
	private Date feedbackTime;
	
	public int getOrderId() {
		return orderId;
	}
	
	public Feedback(int orderId, String feedbackText, Date feedbackTime) {
		super();
		this.orderId = orderId;
		this.feedbackText = feedbackText;
		this.feedbackTime = feedbackTime;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getFeedbackText() {
		return feedbackText;
	}
	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}
	public Date getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
}
