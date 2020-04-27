package com.javabeans.users;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.javabeans.users.UsersDBUtil;

@ManagedBean
@SessionScoped
public class UsersController {
	
	private List<Users> users;
	private UsersDBUtil usersDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public UsersController() throws Exception {
		users = new ArrayList<>();
		
		usersDBUtil = UsersDBUtil.getInstance();
	}
	
	public List<Users> getUsers() {
		return users;
	}
	
	public void loadUsers()
	{
		logger.info("Loading Users");
		users.clear();
		
		try
		{
              users = usersDBUtil.getUsers();
		}
		catch(Exception ex)
		{
			addErrorMessage(ex);
		}
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
