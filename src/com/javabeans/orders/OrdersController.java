package com.javabeans.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.javabeans.users.Users;


@SessionScoped
@ManagedBean
public class OrdersController {

	private List<Orders> cart;
	private List<Orders> orders;
	private OrdersDBUtil ordersDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public OrdersController() throws Exception {
		cart = new ArrayList<>();
		orders = new ArrayList<>();
		ordersDBUtil = OrdersDBUtil.getInstance();
	}
	
	public List<Orders> getCart() {
		return cart;
	}
	
	public void loadCart(Users theUser) {
		cart.clear();

		try {
			cart = ordersDBUtil.getCart(theUser);
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public List<Orders> getOrders() {
		return orders;
	}
	
	public void loadOrders() {
		logger.info("Loading Orders");
		orders.clear();
		try {
			orders = ordersDBUtil.getOrders();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public void loadOrderSummary(Users theUser) {
		try {
			Orders order = ordersDBUtil.getOrderSummValues(theUser);
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("orders", order);
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public void loadUserDetails(Users theUser) {
		try {
			OrdersUsers user = ordersDBUtil.getUserDetails(theUser);
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("users", user);
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public String deleteCartItem(int ref_no) {
		try {
			ordersDBUtil.deleteCartItem(ref_no);
		} catch (Exception ex) {
			addErrorMessage(ex);
			return null;
		}
		return "cart?faces-redirect=true";
	}
	
	public String redirectCheckout() {
		return "checkout.xhtml";
	}
	
	public String placeOrder(Users theUser) {
		try {
			ordersDBUtil.placeOrder(theUser);
		} catch (Exception ex) {
			addErrorMessage(ex);
			return null;
		}
		return "cart?faces-redirect=true";
	}
	
	public String addToCart(int product_id, int quantity, Users theUser) {
		try {
			ordersDBUtil.addToCart(product_id, quantity, theUser);
		} catch (Exception ex) {
			addErrorMessage(ex);
			return null;
		}
		return "products-catalog";
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}	
}