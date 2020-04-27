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

import com.javabeans.feedback.Feedbacks;
import com.javabeans.feedback.FeedbacksDBUtil;

@ManagedBean
@SessionScoped
public class ProductsController {
	private Products currentProduct;
	private List<Feedbacks> feedbacks;
	private FeedbacksDBUtil feedbacksDBUtil;
	private List<Products> productsList;
	private ProductsDBUtil productsDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ProductsController() throws Exception {
		productsList = new ArrayList<>();
		productsDBUtil = ProductsDBUtil.getInstance();
		feedbacksDBUtil = FeedbacksDBUtil.getInstance();
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
	
	public List<Feedbacks> getFeedbacks() {
		return feedbacks;
	}
	
	public Products getCurrentProduct(){
		return currentProduct;
	}
	
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