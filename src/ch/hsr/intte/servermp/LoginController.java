package ch.hsr.intte.servermp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import ch.hsr.intte.servermp.model.Room;
import ch.hsr.intte.servermp.model.User;
import ch.hsr.intte.servermp.service.RoomService;
import ch.hsr.intte.servermp.service.UserService;
import ch.hsr.intte.servermp.util.ChatSession;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;

	private User currentUser;
	private Room selectedRoom;
	private String customRoom;
	private boolean createRoom = false;

	private RoomService roomService = RoomService.getInstance();
	private UserService userService = UserService.getInstance();
	private ChatSession chatSession = ChatSession.getInstance();

	private Collection<Room> availableRooms;

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
	
	public boolean getCreateRoom() {
		return createRoom;
	}
	
	public void setCreateRoom(boolean createRoom){
		this.createRoom = createRoom;
	}

	public int getRoomCount() {
		return roomService.findAll().size();
	}

	public Collection<Room> getAvailableRooms() {
		
		//fake some rooms
		availableRooms = new ArrayList<Room>();
		for (int i = 0; i < 10; i++) {
			availableRooms.add(new Room("Test-Room " + i));
		}
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
	
	public String getCustomRoom() {
		return customRoom;
	}
	
	public void setCustomRoom(String createdRoom) {
		this.customRoom = createdRoom;
	}
	
	public void createRoom(ActionEvent actionEvent) {
		if(!roomNameExists()) {
			
			//TODO: move to roomServer ? 
			Room room = new Room(customRoom);
			System.out.println(room.getName() + " room created");
			availableRooms.add(room);
//			roomService.persist(room);
		}
		else {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Chatroom Name existiert bereits: ",
					"Bitte wählen sie einen anderen Namen.");
			addMessage(message);
		}
	}

	private boolean roomNameExists() {
		if(roomService.findById(customRoom) == null) {
			return false;
		}
		return true;
	}

	public String enter() {
		if (userIsPermitted() && chatroomSelected()) {
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
	
	public String register() {
		return "registration.xhtml";
	}

	private boolean chatroomSelected() {
		if (selectedRoom != null) {
			return true;
		}
		return false;
	}

	public boolean userIsPermitted() {
		if (userExists() && passwordMatches()) {
			return true;
		}
		return false;
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

	private void setSessionParameters() {
		chatSession.setUser(currentUser);
		chatSession.setRoom(selectedRoom);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
