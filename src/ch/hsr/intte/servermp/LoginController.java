package ch.hsr.intte.servermp;

import java.util.Collection;

import javax.faces.application.FacesMessage;
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

	private String username;
	private String password;

	private User currentUser;
	private Collection<Room> availableRooms;
	private Room selectedRoom;
	private String customRoomName;
	private boolean customRoom = false;

	private RoomService roomService = RoomService.getInstance();

	public LoginController() {
		availableRooms = roomService.findAll();
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
		if (inputIsVerified() && roomIsAvailable()) {
			setSessionParameters();
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

	private Room createCustomRoom() {
		if (!roomNameExists()) {
			Room room = new Room(customRoomName);
			roomService.persist(room);
			return room;
		} else {
			generateErrorMessage("Chatroom Name existiert bereits: ", 
					"Bitte wählen sie einen anderen Namen.");
			return null;
		}
	}

	private boolean roomNameExists() {
		return roomService.findById(customRoomName) != null;
	}

	private boolean inputIsVerified() {
		if (userExists() && passwordMatches()) {
			return true;
		} else {
			generateErrorMessage("Username oder Passwort falsch: ",
					"Falls sie noch kein Konto haben müssen sie sich registrieren");
			return false;
		}
	}

	private boolean userExists() {
		UserService userService = UserService.getInstance();
		currentUser = userService.findById(username);
		return currentUser != null;
	}

	private boolean passwordMatches() {
		return currentUser.getPassword().equals(password);
	}

	private void setSessionParameters() {
		ChatSession chatSession = ChatSession.getInstance();
		chatSession.setUser(currentUser);
		chatSession.setRoom(selectedRoom);
	}

	private void generateErrorMessage(String summary, String detail) {
		FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, errorMsg);
	}
		

}
