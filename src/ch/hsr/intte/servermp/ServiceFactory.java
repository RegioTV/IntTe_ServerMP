package ch.hsr.intte.servermp;

import ch.hsr.intte.servermp.services.RoomService;
import ch.hsr.intte.servermp.services.UserService;

public class ServiceFactory {

	private final static RoomService roomService = new RoomService();
	private final static UserService userService = new UserService();

	public static RoomService getRoomService() {
		return roomService;
	}

	public static UserService getUserService() {
		return userService;
	}
	
}
