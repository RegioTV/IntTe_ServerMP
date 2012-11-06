package ch.hsr.intte.servermp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ch.hsr.intte.servermp.model.User;

@ManagedBean
@SessionScoped
public class RegistrationController {

	private String username;
	private String password;
	
	public String register() {
		UserService userService = ServiceFactory.getUserService();
		if (!userService.isUsernameUnique(username)) {
			// TODO: set error message
		}
		
		User user = userService.createUser(username, password);
		// Save to session
		return "login.xhtml";
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
