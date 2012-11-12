package ch.hsr.intte.servermp;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import ch.hsr.intte.servermp.model.User;
import ch.hsr.intte.servermp.service.UserService;
import ch.hsr.intte.servermp.util.ChatSession;

@ManagedBean
@ViewScoped
public class RegistrationController implements Serializable {

	private static final long serialVersionUID = -7318320729973864281L;

	private String username;
	private String password;

	public String register() {
		try {
			checkUsername(username);
			checkPassword(password);

			UserService.getInstance().persist(new User(username, password));
			ChatSession.getInstance().addMessage(SEVERITY_INFO, "registration.success", "registration.success_detail");

			return "login.xhtml";
		} catch (ValidatorException exception) {
			ChatSession.getInstance().addMessage(exception.getFacesMessage());
			return "registration.xhtml";
		}
	}

	public void checkUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		checkUsername((String) value);
	}

	private void checkUsername(String username) {
		if (username.isEmpty())
			throw createValidatorException("username.error", "username.empty");
		if (username.length() < 4)
			throw createValidatorException("username.error", "username.short");
		if (UserService.getInstance().exists(username))
			throw createValidatorException("username.error", "username.exists");
	}

	public void checkPassword(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		checkPassword((String) value);
	}

	private void checkPassword(String password) {
		if (password.isEmpty())
			throw createValidatorException("password.error", "password.empty");
		if (password.length() < 4)
			throw createValidatorException("password.error", "password.short");
	}

	private ValidatorException createValidatorException(String summaryCode, String detailCode) {
		return new ValidatorException(ChatSession.getInstance().createMessage(SEVERITY_ERROR, summaryCode, detailCode));
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
