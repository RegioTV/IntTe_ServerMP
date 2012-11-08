package ch.hsr.intte.servermp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import ch.hsr.intte.servermp.model.Room;
import ch.hsr.intte.servermp.model.User;

@ManagedBean
@ViewScoped
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
