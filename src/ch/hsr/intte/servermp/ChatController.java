package ch.hsr.intte.servermp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ch.hsr.intte.servermp.model.Room;
import ch.hsr.intte.servermp.model.User;

@ManagedBean
@SessionScoped
public class ChatController {

	private final static String ROOM_LIST_FILENAME = "rooms.txt";
	private final static String USER_LIST_FILENAME = "users.txt";

	private List<User> users;
	private List<Room> rooms;

	private String name;
	private String username;
	private String password;

	public ChatController() {
		loadRoomList();
		loadUserList();
	}

	private void loadUserList() {
		try {
			users = new ArrayList<User>();
			File file = new File(USER_LIST_FILENAME);
			file.createNewFile();

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				String[] credentials = line.split(":");
				users.add(new User(credentials[0], credentials[1]));
				line = reader.readLine();
			}
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadRoomList() {
		try {
			rooms = new ArrayList<Room>();
			File file = new File(ROOM_LIST_FILENAME);
			file.createNewFile();

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				rooms.add(new Room(line));
				line = reader.readLine();
			}
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private User findUser(String username) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getName().equals(username))
				return users.get(i);
		}
		return null;
	}

	public String enterChat() {
		User user = findUser(username);
		if (user != null) {
			if (!user.validate(password))
				return "wrong.xhtml";
		}
		return "lobby.xhtml";
	}

	private void invalidateSession() {
		System.out.println("Session invalidiert");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(
				false);
		session.invalidate();
	}

	public String giveup() {
		System.out.println("giveup() aufgerufen");
		invalidateSession();
		return "cancelled.xhtml";
	}

	public Integer getCount() {
		System.out.println(users == null);
		return users.size();
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<Room> getRoomBeans() {
		return rooms;
	}

	public String getNewChat() {
		return "chat";
	}

}
