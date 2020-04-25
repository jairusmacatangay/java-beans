package com.javabeans.home;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Products {
	private int product_id;
	private String product_name;
	private float price;
	private String product_image;
	
	public Products() {
		
	}
	
	public Products(String product_name, float price, String product_image) {
		this.product_name = product_name;
		this.price = price;
		this.product_image = product_image;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
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
}
