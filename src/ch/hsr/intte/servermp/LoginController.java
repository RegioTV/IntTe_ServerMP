package ch.hsr.intte.servermp;

import java.util.Collection;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ch.hsr.intte.servermp.model.Room;
import ch.hsr.intte.servermp.model.User;
import ch.hsr.intte.servermp.service.RoomService;
import ch.hsr.intte.servermp.service.UserService;
import ch.hsr.intte.servermp.util.ChatSession;

@ManagedBean
@SessionScoped
public class LoginController {

	private String username = "";
	private String password = "";

	private User currentUser;
	private Room selectedRoom;
	private Collection<Room> availableRooms;

	private String customRoomName = "";
	private boolean customRoom = false;

	private RoomService roomService = RoomService.getInstance();
	private ResourceBundle messages;

	public LoginController() {
		availableRooms = roomService.findAll();
		FacesContext context = FacesContext.getCurrentInstance();
		messages = context.getApplication().getResourceBundle(context,
				"messages");
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public boolean getCustomRoom() {
		return customRoom;
	}

	public void setCustomRoom(boolean customRoom) {
		this.customRoom = customRoom;
	}

	public int getRoomCount() {
		return availableRooms.size();
	}

	public Collection<Room> getAvailableRooms() {
		return availableRooms;
	}

	public Room getSelectedRoom() {
		return selectedRoom;
	}

	public void setSelectedRoom(Room selectedRoom) {
		this.selectedRoom = selectedRoom;
	}

	public String getCustomRoomName() {
		return customRoomName;
	}

	public void setCustomRoomName(String createdRoom) {
		this.customRoomName = createdRoom;
	}

	public String enter() {
		if (roomIsAvailable() && inputIsVerified()) {
			setSessionParameters();
			generateMessage(FacesMessage.SEVERITY_INFO,
					messages.getString("lobby.success"), "");
			return "room.xhtml";
		} else {
			return "login.xhtml";
		}
	}

	private boolean roomIsAvailable() {
		if (customRoom) {
			selectedRoom = createCustomRoom();
		}
		return selectedRoom != null;
	}

	public boolean inputIsVerified() {
		currentUser = UserService.getInstance().findById(username);
		if (currentUser != null && currentUser.getPassword().equals(password)) {
			return true;
		} else {
			generateMessage(FacesMessage.SEVERITY_ERROR,
					messages.getString("lobby.login.userError.summary"),
					messages.getString("lobby.login.userError.detail"));
			return false;
		}
	}

	private Room createCustomRoom() {
		if (!roomNameExists() && !customRoomName.equals("")) {
			Room room = new Room(customRoomName);
			roomService.persist(room);
			return room;
		} else {
			generateMessage(FacesMessage.SEVERITY_ERROR,
					messages.getString("lobby.chatcreation.namingError.summary"),
					messages.getString("lobby.chatcreation.namingError.detail"));
			return null;
		}
	}

	public boolean roomNameExists() {
		return roomService.findById(customRoomName) != null;
	}

	private void setSessionParameters() {
		ChatSession chatSession = ChatSession.getInstance();
		chatSession.setUser(currentUser);
		chatSession.setRoom(selectedRoom);
	}

	private void generateMessage(Severity severity, String summary,
			String detail) {
		FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage("form:growl", message);
	}

}
