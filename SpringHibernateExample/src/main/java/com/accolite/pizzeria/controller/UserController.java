package com.accolite.pizzeria.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.model.Customer;
import com.accolite.pizzeria.service.AdminService;
import com.accolite.pizzeria.service.CustomerService;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	private static final String REDIRECT = "redirect: http://localhost:7999/pro/#!";
	private static final String LOGINFAIL = "loginfail";
	private static final String REGISTRATIONFAIL ="registrationfail";
	@Autowired
	CustomerService service;
	
	@Autowired
	AdminService adminservice;
	
	private static final String EMAIL_REGEX = 
		    "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + 
		        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	
	/*
	 * returns CustomerId of customer with specified user name
	 */
	@RequestMapping(value = "/getid/{username}", method = RequestMethod.GET)
	public void addItemToCart(@PathVariable String username, HttpServletRequest request,HttpServletResponse response,UriComponentsBuilder ucBuilder) throws IOException {
		Customer customer=service.findCustomerByUsername(username);
	    logger.info(username+"---"+customer.getCustId());
		response.getWriter().print(customer.getCustId());
	}

	/*
	 * logout the customer
	 */
	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		request.getSession().removeAttribute("SessionCreated");
		request.getSession().removeAttribute("Userid");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/*
	 * Handles post request for customer login
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Void> handleFormRequest(@RequestBody String[] details, HttpServletRequest request,HttpServletResponse response) {
		
		String username=details[0];
		String password=details[1];
		
		if(username.equals("") || username.length() > 50 || password.length() < 7) {
			request.getSession().setAttribute(LOGINFAIL, "true");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		response.setContentType("text/plain");		    
		logger.info("Logging in");
					
		Customer customer=service.isCustomerExists(details[0],details[1]);
		if(customer!=null) {
			request.getSession().removeAttribute(LOGINFAIL);
			request.getSession().removeAttribute(REGISTRATIONFAIL);
			logger.info(customer.getCustId());
			request.getSession().setAttribute("Userid", customer.getCustId());
			request.getSession().setAttribute("SessionCreated", "true");
			logger.info("Success login");
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		request.getSession().setAttribute(LOGINFAIL, "true");
		return new ResponseEntity<>(HttpStatus.CONFLICT);					
	}
				
				

	// Handles POST Request For register form
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView handleRegisterFormRequest(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().removeAttribute(LOGINFAIL);
		request.getSession().removeAttribute(REGISTRATIONFAIL);
		logger.info("Registering New User");
		String custName=request.getParameter("fullName");
		String custUserName=request.getParameter("userName");
		String emailId=request.getParameter("email");
		String contactNo=request.getParameter("phoneNumber");
		String resAddress=request.getParameter("address");
		String password=request.getParameter("password");
		String confirmPassword=request.getParameter("confirmPassword");
		boolean flag=true;
		if(custName.equals("") || custName.length() > 50)
			flag=false;
		if(custUserName.equals("") || custUserName.length() > 50)
			flag=false;
		Matcher matcher = EMAIL_PATTERN.matcher(emailId);
        if(!matcher.matches())
        	flag=false;
        if(resAddress.equals("") || contactNo.length() != 10)
        	flag=false;
		if(!flag) {	
			request.getSession().setAttribute(REGISTRATIONFAIL, "true");
			return new ModelAndView(REDIRECT);	//constant instead of string
		}
			
		if(password.equals(confirmPassword)) {
			Customer customer=new Customer(custName,custUserName,emailId,contactNo,resAddress,password);
			if (service.isCustomerUserNameUnique(customer.getCustUserName())) {
				service.saveCustomer(customer);
				logger.info("Successfully registered");
				Coupon coupon=new Coupon("Adding bonus for "+customer.getCustName(),250.00);
				adminservice.addCouponToCustomer(customer.getCustId(),coupon);

				return new ModelAndView(REDIRECT);       	    
			}
		}
		request.getSession().setAttribute(REGISTRATIONFAIL, "true");
		return new ModelAndView(REDIRECT);					
	}
}