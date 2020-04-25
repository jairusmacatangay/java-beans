package com.javabeans.inquiries;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class InquiriesController {
	private InquiriesDBUtil inquiriesDBUtil;
	
	public InquiriesController() throws Exception {
		inquiriesDBUtil = InquiriesDBUtil.getInstance();
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
	
	public void loadUser() {
		try {
			Inquiries user = inquiriesDBUtil.getUser();
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("inquiries", user);
		} catch (Exception ex){
			addErrorMessage(ex);
		}
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
