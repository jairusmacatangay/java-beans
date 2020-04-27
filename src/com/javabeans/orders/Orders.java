package com.javabeans.orders;

public class Orders {
	
	private int productId;
	private int userId;
	
	private int orderId;
	private String productName;
	private int price;
	private int qty;
	private String status;
	private int total;
	private String firstName;
	private String lastName;
	
	public Orders(int orderId, String productName, int price, int qty, String status, int total, String firstName, String lastName)
	{
		this.orderId = orderId;
		this.productName = productName;
		this.price = price;
		this.qty = qty;
		this.status = status;
		this.total = total;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
