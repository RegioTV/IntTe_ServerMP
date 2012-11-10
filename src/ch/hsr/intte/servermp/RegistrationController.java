package ch.hsr.intte.servermp;

import java.util.ResourceBundle;

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

	private ResourceBundle messages;

	private UserService userService = UserService.getInstance();

	public RegistrationController() {
		FacesContext context = FacesContext.getCurrentInstance();
		messages = context.getApplication().getResourceBundle(context, "messages");
	}

	public String register() {
		try {
			checkUsername(null, null, username);
			checkPassword(null, null, password);

			userService.persist(new User(username, password));

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("form:growl",
					new FacesMessage(
							messages.getString("registration.success"),
							messages.getString("registration.success_detail")));

			return "login.xhtml";
		} catch (ValidatorException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("form:growl", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getFacesMessage()
							.getSummary(), e.getFacesMessage().getDetail()));
			return "register.xhtml";
		}
	}

	public void checkUsername(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (((String) value).isEmpty())
			throw new ValidatorException(createErrorMessage(
					messages.getString("username.error"),
					messages.getString("username.empty")));
		if (((String) value).length() < 4)
			throw new ValidatorException(createErrorMessage(
					messages.getString("username.error"),
					messages.getString("username.short")));
		if (userExists((String) value))
			throw new ValidatorException(createErrorMessage(
					messages.getString("username.error"),
					messages.getString("username.exists")));
	}

	public void checkPassword(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (((String) value).isEmpty())
			throw new ValidatorException(createErrorMessage(
					messages.getString("password.error"),
					messages.getString("password.empty")));
		if (((String) value).length() < 4)
			throw new ValidatorException(createErrorMessage(
					messages.getString("password.error"),
					messages.getString("password.short")));
	}

	private FacesMessage createErrorMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage();

		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		message.setSummary(summary);
		message.setDetail(detail);

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
