package ch.hsr.intte.servermp;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import ch.hsr.intte.servermp.model.Room;
import ch.hsr.intte.servermp.model.User;
import ch.hsr.intte.servermp.util.ChatSession;

@ManagedBean
@ViewScoped
public class RoomController implements Serializable {

	private static final long serialVersionUID = -5041267243587143132L;

	private static final Lock lock = new ReentrantLock();

	private User user;
	private Room room;

	private String message;

	public RoomController() {
		ChatSession chatSession = ChatSession.getInstance();

		user = chatSession.getUser();
		room = chatSession.getRoom();

		user.enterRoom(room);
	}

	public User getUser() {
		return user;
	}

	public Room getRoom() {
		return room;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void sendMessage() {
		if (message.isEmpty())
			return;

		try {
			lock.lock();
			distributeMessage();
			message = "";
		} finally {
			lock.unlock();
		}
	}

	private void distributeMessage() {
		PushContextFactory pushContextFactory = PushContextFactory.getDefault();
		PushContext pushContext = pushContextFactory.getPushContext();

		pushContext.push("/" + room.getName(), user.getUsername() + ": " + message);
	}

	public String logout() {
		user.leaveRoom();

		ChatSession.getInstance().invalidate();
		ChatSession.getInstance().addMessage(FacesMessage.SEVERITY_INFO, "chat.logout.summary", "chat.logout.detail");

		return "login.xhtml";
	}

}
