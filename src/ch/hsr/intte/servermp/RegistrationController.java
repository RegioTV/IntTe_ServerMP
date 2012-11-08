package ch.hsr.intte.servermp;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import ch.hsr.intte.servermp.model.User;
import ch.hsr.intte.servermp.services.UserService;

@ManagedBean
@SessionScoped
public class RegistrationController {

	private String username;
	private String password;

	public String register() throws Exception {
		UserService userService = ServiceFactory.getUserService();
		User user = userService.createUser(username, password);
		if (user != null)
			return "registration_ok.xhtml";
		else
			throw new Exception("Registration failed for some reason.");

	}
	
	public void checkUsername (FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (!ServiceFactory.getUserService().isUsernameUnique((String) value)) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Username already exists.");
			message.setDetail("Username already exists.");
			context.addMessage("form:username", message);
			throw new ValidatorException(message);
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
