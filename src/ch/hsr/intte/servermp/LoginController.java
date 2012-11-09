package ch.hsr.intte.servermp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

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
	private Room selectedRoom;
	

	private RoomService roomService = RoomService.getInstance();
	private UserService userService = UserService.getInstance();
	private ChatSession chatSession = ChatSession.getInstance();

	private Collection<Room> availableRooms = new ArrayList<Room>();

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

	public int getRoomCount() {
		return roomService.findAll().size();
	}
	
	public Collection<Room> getAvailableRooms() {
		availableRooms.add(new Room("Test-Room"));
		return availableRooms;
	}
	
	public void setAvailableRooms(Collection<Room> availableRooms) {
		this.availableRooms = availableRooms;
	}
	
	public Room getSelectedRoom() {
		return selectedRoom;
	}
	
	public void setSelectedRoom(Room selectedRoom) {
		this.selectedRoom = selectedRoom;
	}

	public String login() {
		if (userIsPermitted()) {
			setSessionParameters();
			return "room.xhtml";
		} else {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Username oder Passwort falsch: ",
					"Falls sie noch kein Konto haben müssen sie sich registrieren");
			addMessage(message);
			return "login.xhtml";
		}
	}

	public boolean userIsPermitted() {
		if (userExists() && passwordMatches()) {
			return true;
		}
		return false;
	}

	private void setSessionParameters() {
		chatSession.setUser(currentUser);
		chatSession.setRoom(selectedRoom);
	}

	private boolean userExists() {
		if ((currentUser = userService.findById(username)) != null) {
			return true;
		}
		return false;
	}

	private boolean passwordMatches() {

		if (currentUser.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
