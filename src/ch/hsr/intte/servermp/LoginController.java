package ch.hsr.intte.servermp;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ch.hsr.intte.servermp.model.Room;
import ch.hsr.intte.servermp.model.User;
import ch.hsr.intte.servermp.service.RoomService;
import ch.hsr.intte.servermp.service.UserService;
import ch.hsr.intte.servermp.util.ChatSession;

@ManagedBean
@ViewScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 2238062448502459051L;

	private String username = "";
	private String password = "";

	private User currentUser;
	private Room selectedRoom;

	private String customRoomName = "";
	private boolean customRoom = false;

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
		return RoomService.getInstance().findAll().size();
	}

	public Collection<Room> getAvailableRooms() {
		return RoomService.getInstance().findAll();
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
			ChatSession.getInstance().addMessage(FacesMessage.SEVERITY_INFO, "lobby.success.summary", "lobby.success.detail");
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
		if (UserService.getInstance().exists(username) && UserService.getInstance().findById(username).verify(password)) {
			return true;
		} else {
			ChatSession.getInstance().addMessage(FacesMessage.SEVERITY_ERROR, "lobby.login.userError.summary",
					"lobby.login.userError.detail");
			return false;
		}
	}

	private Room createCustomRoom() {
		if (!RoomService.getInstance().exists(customRoomName) && !customRoomName.isEmpty()) {
			Room room = new Room(customRoomName);
			RoomService.getInstance().persist(room);
			return room;
		} else {
			ChatSession.getInstance().addMessage(FacesMessage.SEVERITY_ERROR, "lobby.chatcreation.namingError.summary",
					"lobby.chatcreation.namingError.detail");
			return null;
		}
	}

	private void setSessionParameters() {
		ChatSession.getInstance().setUser(currentUser);
		currentUser.enterRoom(selectedRoom);
	}

}
