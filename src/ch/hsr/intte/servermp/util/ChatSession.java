package ch.hsr.intte.servermp.util;

import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ch.hsr.intte.servermp.model.User;

public class ChatSession {

	private static final String USER = "user";

	private static ChatSession instance;

	public static ChatSession getInstance() {
		if (instance != null)
			return instance;
		else
			return instance = new ChatSession();
	}

	private ChatSession() {}

	public User getUser() {
		return (User) getSessionMap().get(USER);
	}

	public void setUser(User user) {
		getSessionMap().put(USER, user);
	}

	private Map<String, Object> getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	public FacesMessage createMessage(Severity severity, String summaryCode, String detailCode) {
		FacesMessage message = new FacesMessage();

		message.setSeverity(severity);
		message.setSummary(getMessage(summaryCode));
		message.setDetail(getMessage(detailCode));

		return message;
	}

	private String getMessage(String messageCode) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ResourceBundle resourceBundle = application.getResourceBundle(context, "messages");

		return resourceBundle.getString(messageCode);
	}

	public void addMessage(Severity severity, String summaryCode, String detailCode) {
		addMessage(createMessage(severity, summaryCode, detailCode));
	}

	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage("form:growl", message);
	}

	public void invalidate() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);

		session.invalidate();
	}

}
