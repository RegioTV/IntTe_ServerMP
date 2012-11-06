package ch.hsr.intte.servermp;

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
