package com.javabeans.dashboard;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean
public class DashboardController {
	private DashboardDBUtil dashboardDBUtil;
	
	public DashboardController() throws Exception {
		dashboardDBUtil = DashboardDBUtil.getInstance();
	}
		
	public void loadDashboardValues() {
		try {
			Dashboard values = dashboardDBUtil.getDashboardValues();
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("dashboard", values);
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
