package com.javabeans.home;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean
public class HomeProductsController {
	private HomeProductsDBUtil productsDBUtil;
	private List<HomeProducts> equipment;
	private List<HomeProducts> coffee;
	private List<HomeProducts> drinkware;
		
	public HomeProductsController() throws Exception {
		equipment = new ArrayList<>();
		coffee = new ArrayList<>();
		drinkware = new ArrayList<>();
		productsDBUtil = HomeProductsDBUtil.getInstance();
	}
	
	public List<HomeProducts> getEquipment() {
		return equipment;
	}
	
	public void loadEquipment() {
		equipment.clear();
		try {
			equipment = productsDBUtil.getEquipment();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public List<HomeProducts> getCoffee() {
		return coffee;
	}
	
	public void loadCoffee() {
		coffee.clear();
		try {
			coffee = productsDBUtil.getCoffee();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public List<HomeProducts> getDrinkware() {
		return drinkware;
	}
	
	public void loadDrinkware() {
		drinkware.clear();
		try {
			drinkware = productsDBUtil.getDrinkware();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
