package com.javabeans.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ProductsController {
	private List<Products> productsList;
	private ProductsDBUtil productsDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ProductsController() throws Exception {
		productsList = new ArrayList<>();
		productsDBUtil = ProductsDBUtil.getInstance();
	}
	
	public List<Products> getProducts() {
		return productsList;
	}
	
	public void loadProducts() {
		logger.info("Loading products");
		productsList.clear();
		try {
			productsList = productsDBUtil.getProducts();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public String addProduct(Products theProduct) {
		logger.info("Adding product: " + theProduct);
		try {
			productsDBUtil.addProduct(theProduct);
		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding product", exc);
			addErrorMessage(exc);
			return null;
		}
		return "add-product?faces-redirect=true";
	}
	
	public String deleteProduct(int productId) {
		logger.info("Deleting product id: " + productId);
		try {
			productsDBUtil.deleteProduct(productId);
		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting product id: " + productId, exc);
			addErrorMessage(exc);
			return null;
		}
		return "view-all-products?faces-redirect=true";
	}
		
	public void loadProduct(int product_id) {
		logger.info("Loading product: " + product_id);
		try {
			Products theProduct = productsDBUtil.getProduct(product_id);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("products", theProduct);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error loading product:" + product_id, ex);
			addErrorMessage(ex);
		}
	}
	
	public String updateProduct(Products theProduct) {
		logger.info("Updating Product: " + theProduct);
		
		try {
			productsDBUtil.updateProduct(theProduct);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error updating product: " + theProduct, ex);
			addErrorMessage(ex);
			return null;
		}
		return "view-all-products?faces-redirect=true";
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
