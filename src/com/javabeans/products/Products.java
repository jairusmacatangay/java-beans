package com.javabeans.products;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

@ManagedBean
public class Products {

	private int product_id;
	private String product_category;
	private String product_name;
	private String description;
	private float price;
	private String product_image;
	private Part image;
	
	//Total Number of Products Variables
	private int totalCoffee;
	private int totalEquipment;
	private int totalDrinkware;
	private int totalProducts;
	
	List<String> categoryOptions;
	
	public Products() {
		
	}
	
	public Products(int totalCoffee, int totalEquipment, int totalDrinkware, int totalProducts) {
		this.totalCoffee = totalCoffee;
		this.totalEquipment = totalEquipment;
		this.totalDrinkware = totalDrinkware;
		this.totalProducts = totalProducts;
	}
	
	public Products(int product_id, String product_category, String product_name, 
			String description, float price, String product_image) {
		this.product_id = product_id;
		this.product_category = product_category;
		this.product_name = product_name;
		this.price = price;
		this.description = description;
		this.product_image = product_image;
	}
	
	public Products(int product_id, String product_category, String product_name, 
			String description, float price) {
		this.product_id = product_id;
		this.product_category = product_category;
		this.product_name = product_name;
		this.price = price;
		this.description = description;
	}
	
	public List<String> getCategoryOptions() {
		return categoryOptions;
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

	public void setCategoryOptions(List<String> categoryOptions) {
		this.categoryOptions = categoryOptions;
	}

	public Part getImage() {
		return image;
	}

	public void setImage(Part image) {
		this.image = image;
	}

	public int getTotalCoffee() {
		return totalCoffee;
	}

	public void setTotalCoffee(int totalCoffee) {
		this.totalCoffee = totalCoffee;
	}

	public int getTotalEquipment() {
		return totalEquipment;
	}

	public void setTotalEquipment(int totalEquipment) {
		this.totalEquipment = totalEquipment;
	}

	public int getTotalDrinkware() {
		return totalDrinkware;
	}

	public void setTotalDrinkware(int totalDrinkware) {
		this.totalDrinkware = totalDrinkware;
	}

	public int getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}
}