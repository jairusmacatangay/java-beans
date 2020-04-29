package com.javabeans.inquiries;

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

import com.javabeans.users.Users;

@ManagedBean
@SessionScoped
public class InquiriesController {
	private InquiriesDBUtil inquiriesDBUtil;
	private List<Inquiries> inquiriesList;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public InquiriesController() throws Exception {
		inquiriesDBUtil = InquiriesDBUtil.getInstance();
		inquiriesList = new ArrayList<>();
	}
	
	public String sendInquiry(Inquiries inquiry) {
		try {
			inquiriesDBUtil.sendInquiry(inquiry);
		} catch (Exception ex) {
			addErrorMessage(ex);
			return null;
		}
		return "contact-us?faces-redirect=true";
	}
	
	public void loadUser(Users theUser) {
		try {
			Inquiries user = inquiriesDBUtil.getUser(theUser);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("inquiries", user);
		} catch (Exception ex){
			addErrorMessage(ex);
		}
	}
	
	public List<Inquiries> getInquiries() {
		return inquiriesList;
	}
	
	public void loadInquiries() {
		inquiriesList.clear();
		try {
			inquiriesList = inquiriesDBUtil.getInquiries();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public String getInquiry(int inquiry_id) {
		try {
			Inquiries inquiry = inquiriesDBUtil.getInquiry(inquiry_id);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("inquiries", inquiry);
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
		return "inquiry-details";
	}
	
	public String sendReply(Inquiries inquiry) {
		logger.info("Sending reply");
		try {
			inquiriesDBUtil.sendReply(inquiry);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error: ", ex);
			addErrorMessage(ex);
			return null;
		}
		return "inquiries?faces-redirect=true";
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
