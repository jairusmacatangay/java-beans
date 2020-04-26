package com.javabeans.products;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.javabeans.feedback.Feedbacks;
import com.javabeans.feedback.FeedbacksDBUtil;


@ManagedBean
@SessionScoped
public class ProductsController {
	
	private Products currentProduct;
	private List<Products> products;
	private List<Feedbacks> feedbacks;
	private ProductsDBUtil productsDBUtil;
	private FeedbacksDBUtil feedbacksDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ProductsController() throws Exception {
		products = new ArrayList<>();
		productsDBUtil = ProductsDBUtil.getInstance();
		feedbacksDBUtil = FeedbacksDBUtil.getInstance();
	}
	
	//get list of products   FOR TESTING ONLY(Products Catalog not my part, just for button)!!!! -Bern
	public List<Products> getProducts() {
		return products;
	}
	
	//load list of products   FOR TESTING ONLY(Products Catalog not my part, just for button)!!!! -Bern
	public void loadProducts() {
		logger.info("Loading products");
		products.clear();
		
		try {
			products = productsDBUtil.getProducts();
		} catch (Exception exc) {
			addErrorMessage(exc);
		}
	}
	
	public List<Feedbacks> getFeedbacks() {
		return feedbacks;
	}
	
	public Products getCurrentProduct(){
		return currentProduct;
	}
	
	//product details
	public String productDetails(Products theProduct) {
		logger.info("Product details for: " + theProduct);
		
		try {
			//set product details
			theProduct = productsDBUtil.getProductDetails(theProduct);
			
			//set products' feedbacks
			feedbacks = feedbacksDBUtil.getFeedbacks(theProduct.getProduct_id());
			
			//for displaying a single product
			currentProduct = theProduct;
			
		}catch (Exception exc) {
			logger.log(Level.SEVERE,"Error loading product details: " + theProduct, exc);
			addErrorMessage(exc);
			return null;
		}
		return "/pages/customer/product-details?faces-redirect=true";
	}
	
	//add feedback
	public String addFeedback(Feedbacks theFeedback) {
		logger.info("Adding feedback: " + theFeedback);
		
		try {
			//user_id 
			int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
			theFeedback.setUser_id(user_id);
			
			//product_id
			theFeedback.setProduct_id(currentProduct.getProduct_id());
			
			feedbacksDBUtil.addFeedback(theFeedback);
			
			//refresh list of feedbacks after adding a feedback to the DB
			feedbacks = feedbacksDBUtil.getFeedbacks(currentProduct.getProduct_id());
			
		} catch(Exception exc) {
			logger.log(Level.SEVERE, "Error adding student", exc);
			addErrorMessage(exc);
			
			return null;
		}
		return "product-details?faces-redirect=true";
	}
	
	private void addErrorMessage(Exception ex) {
		FacesMessage message = new FacesMessage("Error: " + ex.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
