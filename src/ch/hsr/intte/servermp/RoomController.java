package ch.hsr.intte.servermp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import ch.hsr.intte.servermp.model.Room;
import ch.hsr.intte.servermp.model.User;
import ch.hsr.intte.servermp.util.ChatSession;

@ManagedBean
@SessionScoped
public class RoomController {

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

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void sendMessage() {
		try {
			lock.lock();
			distributeMessage();
		} finally {
			lock.unlock();
		}
	}

	private void distributeMessage() {
		PushContextFactory pushContextFactory = PushContextFactory.getDefault();
		PushContext pushContext = pushContextFactory.getPushContext();

		pushContext.push("/" + room.getName(), user.getUsername() + ": " + message);
	}

	public String leaveRoom() {
		user.leaveRoom();
		ChatSession.getInstance().invalidate();
		return "login.xhtml";
	}

}
