package ch.hsr.intte.servermp;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import ch.hsr.intte.servermp.model.User;

@ManagedBean
@SessionScoped
public class RegistrationController {

	private UIComponent growl;

	private String username;
	private String password;

	public String register() {
		UserService userService = ApplicationFactory.getUserService();
		User user = userService.createUser(username, password);
System.out.println("isnull: "+(user==null));
		if (user != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(growl.getClientId(context), new FacesMessage(
					"Registration successful",
					"Your registration was successfull. You can now log in."));
			return "login.xhtml";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(growl.getClientId(context), new FacesMessage(
					"Registration failed",
					"Registration failed for some reason."));
			return "registration.xhtml";
		}
	}

	public void checkUsername(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		if (((String) value).length() < 4)
			throwMessage("Username is too short. (min. 4 characters)",
					FacesMessage.SEVERITY_ERROR);
		else if (!ApplicationFactory.getUserService().isUsernameUnique(
				(String) value))
			throwMessage("Username already exists.",
					FacesMessage.SEVERITY_ERROR);
		else
			throwMessage("Username appears to be ok.",
					FacesMessage.SEVERITY_INFO);
	}

	private void throwMessage(String message, Severity severity)
			throws ValidatorException {
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(severity);
		msg.setSummary(message);
		msg.setDetail(message);
		FacesContext.getCurrentInstance().addMessage("form:username", msg);
		throw new ValidatorException(msg);
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
