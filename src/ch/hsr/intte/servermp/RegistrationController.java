package ch.hsr.intte.servermp;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import ch.hsr.intte.servermp.model.User;
import ch.hsr.intte.servermp.service.UserService;

@ManagedBean
@SessionScoped
public class RegistrationController {

	private String username;
	private String password;

	private UserService userService = UserService.getInstance();

	public String register() throws Exception {
		if (userExists(username))
			throw new Exception("Registration failed for some reason.");

		userService.persist(new User(username, password));
		return "registration_ok.xhtml";
	}

	public void checkUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (userExists((String) value))
			throw new ValidatorException(createUserAlreadyExistsMessage());
	}

	private FacesMessage createUserAlreadyExistsMessage() {
		FacesMessage message = new FacesMessage();

		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		message.setSummary("Username already exists.");
		message.setDetail("Username already exists.");

		return message;
	}

	private boolean userExists(String id) {
		return userService.findById(id) != null;
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
