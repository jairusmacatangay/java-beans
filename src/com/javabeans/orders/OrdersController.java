package com.javabeans.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.javabeans.orders.Orders;
import com.javabeans.orders.OrdersDBUtil;

@ManagedBean
@SessionScoped
public class OrdersController {
	
	private List<Orders> orders;
	private OrdersDBUtil ordersDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());

	public OrdersController() throws Exception {
		orders = new ArrayList<>();
		
		ordersDBUtil = OrdersDBUtil.getInstance();
	}
	
	public List<Orders> getOrders() {
		return orders;
	}
	
	public void loadOrders()
	{
		logger.info("Loading Orders");
		orders.clear();
		
		try
		{
			orders = ordersDBUtil.getOrders();
		}
		catch(Exception ex)
		{
			addErrorMessage(ex);
		}
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}
