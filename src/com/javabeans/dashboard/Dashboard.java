package com.javabeans.dashboard;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Dashboard {
	private int totalUsers;
	private int totalProducts;
	private int totalOrders;
	private float totalEarnings;
	
	public Dashboard(int totalUsers, int totalProducts, int totalOrders, float totalEarnings) {
		this.totalUsers = totalUsers;
		this.totalProducts = totalProducts;
		this.totalOrders = totalOrders;
		this.totalEarnings = totalEarnings;
	}
	
	public int getTotalUsers() {
		return totalUsers;
	}
	
	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}
	
	public int getTotalProducts() {
		return totalProducts;
	}
	
	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}
	
	public int getTotalOrders() {
		return totalOrders;
	}
	
	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
	
	public float getTotalEarnings() {
		return totalEarnings;
	}
	
	public void setTotalEarnings(float totalEarnings) {
		this.totalEarnings = totalEarnings;
	}
}
