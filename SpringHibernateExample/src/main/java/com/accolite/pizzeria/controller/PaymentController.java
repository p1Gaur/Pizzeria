package com.accolite.pizzeria.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.accolite.pizzeria.model.Cart;
import com.accolite.pizzeria.model.Coupon;
import com.accolite.pizzeria.model.Customer;
import com.accolite.pizzeria.model.Order;
import com.accolite.pizzeria.model.OrderStatus;
import com.accolite.pizzeria.service.CartInfo;
import com.accolite.pizzeria.service.CartService;
import com.accolite.pizzeria.service.CustomerService;
import com.accolite.pizzeria.service.OrderService;

@RestController
@RequestMapping("/payment")
@EnableWebMvc
public class PaymentController {

	private static final Logger logger = Logger.getLogger(PaymentController.class);

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	/*
	 * returns all the coupons customer with userid has.
	 */
	@RequestMapping(value = "/coupons/{userid}", method = RequestMethod.GET)
	public ResponseEntity<List<Coupon>> listcoupons(@PathVariable String userid) {
		logger.info("getting coupons");
		List<Coupon> coupons = customerService.getAllCouponsOfCustomer(Integer.parseInt(userid));
		if(coupons==null){
            return new ResponseEntity<List<Coupon>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
	}
	
	/*
	 * make the payment
	 * Removes the items from cart and place items in order table.
	 * Deduct the amount obtained by applying coupons
	 */
	@RequestMapping(value = "/make", method = RequestMethod.POST)
	public ResponseEntity<Integer> makepayment(@RequestBody String[] couponsList) {
		int userid=Integer.parseInt(couponsList[couponsList.length-1]);
		
		Customer customer=customerService.findById(userid);
		Cart cart=customer.getCart();
		double cartAmount=cartService.getTotalAmountOfCart(userid);
		logger.info("Amount of cart: " + cartAmount);
		for(int i=0;i<couponsList.length-1;i++) {
			String couponid=couponsList[i];
			Coupon coupon=customerService.getCouponByIdOfCustomer(userid,(couponid));
			if(coupon!=null)
			{
			if( cartAmount < 0) {
				return new ResponseEntity<Integer>(-1,HttpStatus.CONFLICT);
			}
				cartAmount -= coupon.getDiscountAmount();
				customerService.removeCoupon(userid, (couponsList[i]));
			}
			
		}
		cart.setCartAmount(cartAmount);
		
		//placing order
		List<CartInfo> cartList=cartService.getAllCartItemsOfCustomer(customer.getCustId());
		Order order=new Order();
		order.setAmount(cartAmount);
		order.setContactNo(customer.getContactNo());
		order.setDeliveryAddress(customer.getResAddress());
		order.setOrdStatus(OrderStatus.ACTIVE);
		order.setPlacingTime(new Date());
		order.setCustId(customer.getCustId());
				
		orderService.addOrderItems(order, cartList);
		cartService.clearCartOfUser(userid);
		
		return new ResponseEntity<>(order.getOrderId(),HttpStatus.CREATED);			
	}
	
	/*
	 * saves feedback in database
	 */
	@RequestMapping(value = "/feedback/{userid}/{orderId}/{feedback}", method = RequestMethod.GET)
	public ResponseEntity<Void> savesFeedback(@PathVariable String feedback,@PathVariable String userid,@PathVariable String orderId) {
		orderService.addFeedback(Integer.parseInt(orderId), feedback);
        return new ResponseEntity<>(HttpStatus.OK);
	}
}
