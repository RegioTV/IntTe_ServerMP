package ch.hsr.intte.servermp;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class ApplicationFactory {

	private final static RoomService roomService = new RoomService();
	private final static UserService userService = new UserService();
	
	public static RoomService getRoomService() {
		return roomService;
	}
	
	public static UserService getUserService() {
		return userService;
	}
	
	public static HttpSession getSession() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return (HttpSession) fc.getExternalContext().getSession(false);
	}
}
