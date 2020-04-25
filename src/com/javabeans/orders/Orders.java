package com.javabeans.orders;

import javax.faces.bean.ManagedBean;

import java.time.LocalDateTime;

@ManagedBean
public class Orders {
	
	//Order_Details Table Fields
	private int ref_no;
	private int order_id;
	private int product_id;
	private int user_id;
	private int quantity;
	private float total_amount;
	
	//Products Table Fields
	private String product_category;
	private String product_name;
	private float price;
	private String product_image;
	
	//Order Summary Variables
	private float gross;
	private float vat;
	private float shippingFee;
	private float overallTotal;
	
	//Orders Table Fields
	private String status;
	private LocalDateTime date_ordered;
	
	public Orders(float total_amount, float gross, float vat, float shippingFee, float overallTotal) {
		this.total_amount = total_amount;
		this.gross = gross;
		this.vat = vat;
		this.shippingFee = shippingFee;
		this.overallTotal = overallTotal;
	}
	
	public Orders(int ref_no, int order_id, int product_id, int user_id, int quantity, float total_amount,
			String product_category, String product_name, float price, String product_image) {

		this.ref_no = ref_no;
		this.order_id = order_id;
		this.product_id = product_id;
		this.user_id = user_id;
		this.quantity = quantity;
		this.total_amount = total_amount;
		this.product_category = product_category;
		this.product_name = product_name;
		this.price = price;
		this.product_image = product_image;

	}
	
	public Orders() {
		
	}
	
	public int getRef_no() {
		return ref_no;
	}
	
	public void setRef_no(int ref_no) {
		this.ref_no = ref_no;
	}
	
	public int getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	public int getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public float getTotal_amount() {
		return total_amount;
	}
	
	public void setTotal_amount(float total_amount) {
		this.total_amount = total_amount;
	}
	
	public String getProduct_category() {
		return product_category;
	}
	
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	
	public String getProduct_name() {
		return product_name;
	}
	
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
		
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public String getProduct_image() {
		return product_image;
	}
	
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}

	public float getGross() {
		return gross;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDate_ordered() {
		return date_ordered;
	}

	public void setDate_ordered(LocalDateTime date_ordered) {
		this.date_ordered = date_ordered;
	}

	public void setGross(float gross) {
		this.gross = gross;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(float vat) {
		this.vat = vat;
	}

	public float getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(float shippingFee) {
		this.shippingFee = shippingFee;
	}

	public float getOverallTotal() {
		return overallTotal;
	}

	public void setOverallTotal(float overallTotal) {
		this.overallTotal = overallTotal;
	}
}
