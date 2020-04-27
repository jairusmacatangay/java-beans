package com.javabeans.users;

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

import com.javabeans.users.UsersDBUtil;

@ManagedBean
@SessionScoped
public class UsersController {
	
	public List<Users>transactions;
	private UsersDBUtil usersDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public UsersController() throws Exception {
		transactions = new ArrayList<>();
		usersDBUtil = UsersDBUtil.getInstance();
	}
	
	//register
	public String addUser(Users theUser) {
		logger.info("Adding User: " + theUser);
		try {
			usersDBUtil.addUser(theUser);
			
		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding a user", exc);
			addErrorMessage(exc);
			return null;
		}
		
		return "login?faces-redirect=true";
		
	}
	
	//login
	public String validateUser(Users theUser) throws Exception {
<<<<<<< HEAD
		
		theUser = usersDBUtil.validateUser(theUser);
		
		if(theUser != null) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user_id", theUser.getUser_id());
			return "/pages/customer/home-page?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage("login:submit", new FacesMessage("Incorrect Email and Password!"));
			return "login";
||||||| 9ee58a6
		
		theUser = usersDBUtil.validateUser(theUser);
		
		if(theUser != null) {
			return "/pages/customer/home-page";
		} else {
			FacesContext.getCurrentInstance().addMessage("login:submit", new FacesMessage("Incorrect Email and Password!"));
			return "login";
=======
		try {
			theUser = usersDBUtil.validateUser(theUser);
			String user_type = theUser.getUser_type();
			
			if(user_type.equals("Customer")) {
				return "/pages/customer/home-page?faces-redirect=true";
			} else if (user_type.equals("Admin")) {
				return "/pages/admin/dashboard?faces-redirect=true";
			}
		} catch (Exception ex) {
			addErrorMessage(ex);
>>>>>>> master
		}
		
		FacesContext.getCurrentInstance().addMessage("login:submit", new FacesMessage("Incorrect Email and Password!"));
		return "login";
	}
	
	//get account details for view
	public String accountDetails(Users theUser) {
		logger.info("Account details for user: " + theUser);
		
		try {
			// set user's details
			theUser = usersDBUtil.getUserDetails(theUser);
			
			// set user's transactions
			transactions = usersDBUtil.getTransactionHistory(theUser);
			
		} catch (Exception exc) {
			logger.log(Level.SEVERE,"Error loading user account details: " + theUser, exc);
			addErrorMessage(exc);
			return null;
		}
		return "account-details?faces-redirect=true";
	}
	
	//get account details for edit
	public String editAccountDetails(Users theUser) {
		logger.info("Edit accound details for user: " + theUser);
		try{
			theUser = usersDBUtil.getUserDetails(theUser);

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("users", theUser);	
		} catch(Exception exc) {
			logger.log(Level.SEVERE,"Error loading user account details: " + theUser, exc);
			addErrorMessage(exc);
			return null;
		}
		return "edit-account-details?faces-redirect=true";
	}
	
	public String editAccountDetailsForCheckout(Users theUser) {
		try {
			theUser = usersDBUtil.getUserDetailsForCheckout(theUser);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("users", theUser);
		} catch (Exception ex) {
			addErrorMessage(ex);
			return null;
		}
		return "edit-account-details-checkout?faces-redirect=true";
	}
	
	//update account details
	public String updateAccountDetails(Users theUser) {
		logger.info("Update account details for user: " + theUser);
		
		try {
			usersDBUtil.updateUserDetails(theUser);
		} catch(Exception exc) {
			logger.log(Level.SEVERE, "Error updating user: " + theUser, exc);
			addErrorMessage(exc);
			return null;
		}
		
		return "account-details?faces-redirect=true";
	}
	
	public String updateAccountDetailsForCheckout(Users theUser) {
		try {
			usersDBUtil.updateUserDetailsForCheckout(theUser);
		} catch (Exception ex) {
			addErrorMessage(ex);
			return null;
		}
		return "checkout?faces-redirect=true";
	}
	
	//Transaction History List
	public List<Users> getTransactions() {
		return transactions;
	}
	
	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
