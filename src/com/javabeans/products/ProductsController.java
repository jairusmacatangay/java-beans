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

	private List<Products> products;
	private ProductsDBUtil productsDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ProductsController() throws Exception {
		products = new ArrayList<>();
		
		productsDBUtil = ProductsDBUtil.getInstance();
	}
	
	public List<Products> getProducts() {
		return products;
	}
	
	//List of Products
	public void loadProducts()
	{
		logger.info("Loading products");
		products.clear();
		
		try
		{
             //get all products from database
              products = productsDBUtil.getProducts();
            
		}
		catch(Exception ex)
		{
			//add error message for JSF page
			addErrorMessage(ex);
		}
	}
	
	//Add Product
	public String addProduct(Products theProduct)
	{
		logger.info("Adding product: " + theProduct);
		
		try
		{
			//add product to the database
			productsDBUtil.addProduct(theProduct);
		}
		catch(Exception exc)
		{
			//send this to server logs
			logger.log(Level.SEVERE, "Error adding product", exc);
			
			//add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "add-product?faces-redirect=true";
	}
	
	//Delete Product
	public String deleteProduct(int productId)
	{
		logger.info("Deleting product id: " + productId);
		
		try
		{
			//delete the product from the database
			productsDBUtil.deleteProduct(productId);
		}
		catch (Exception exc)
		{
			//send this to server logs
			logger.log(Level.SEVERE, "Error deleting product id: " + productId, exc);
			
			//add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "view-all-products?faces-redirect=true";
	}
	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}
