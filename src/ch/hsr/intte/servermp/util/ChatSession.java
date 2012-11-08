package ch.hsr.intte.servermp.util;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ch.hsr.intte.servermp.model.Room;
import ch.hsr.intte.servermp.model.User;

public class ChatSession {

	private static final String USER = "user";
	private static final String ROOM = "room";

	private ChatSession() {}

	public static ChatSession getInstance() {
		return new ChatSession();
	}

	public User getUser() {
		return (User) getSessionMap().get(USER);
	}

	public void setUser(User user) {
		getSessionMap().put(USER, user);
	}

	public Room getRoom() {
		return (Room) getSessionMap().get(ROOM);
	}

	public void setRoom(Room room) {
		getSessionMap().put(ROOM, room);
	}

	private Map<String, Object> getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	public void invalidate() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);

		session.invalidate();
	}

}
