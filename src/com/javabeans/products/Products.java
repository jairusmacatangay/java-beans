package com.javabeans.products;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Products {

	private int id;
	private String name;
	private String category;
	private float price;
	private String description;
	//private image
	private Date datetime;
	
	List<String> categoryOptions;
	
	public Products()
	{
		
	}
	
	public Products(int id, String name, String category, float price, String description)
	{
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.description = description;
	}
	
	public List<String> getCategoryOptions() {
		return categoryOptions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
}
