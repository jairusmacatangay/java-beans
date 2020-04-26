package com.javabeans.products;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class Products {

	private int product_id;
	private String product_category;
	private String product_name;
	private String description;
	private float price;
	private String product_image;
	
	public Products() {
		
	}
	
	public Products(int product_id, String product_category, String product_name, String description, 
			float price, String product_image) {
		
		this.product_id = product_id;
		this.product_category = product_category;
		this.product_name = product_name;
		this.description = description;
		this.price = price;
		this.product_image = product_image;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
