package com.javabeans.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean
public class OrdersController {

	private List<Orders> cart;
	private OrdersDBUtil ordersDBUtil;
	
	public OrdersController() throws Exception {
		cart = new ArrayList<>();
		ordersDBUtil = OrdersDBUtil.getInstance();
	}
	
	public List<Orders> getCart() {
		return cart;
	}
	
	public void loadCart() {
		cart.clear();

		try {
			cart = ordersDBUtil.getCart();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public void loadOrderSummary() {
		try {
			Orders order = ordersDBUtil.getOrderSummValues();
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("orders", order);
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public void loadUserDetails() {
		try {
			Users user = ordersDBUtil.getUserDetails();
			
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
	
	public String placeOrder() {
		try {
			ordersDBUtil.placeOrder();
		} catch (Exception ex) {
			addErrorMessage(ex);
			return null;
		}
		return "cart?faces-redirect=true";
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}	
}
