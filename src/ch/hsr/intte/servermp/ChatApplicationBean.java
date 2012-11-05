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

@ManagedBean
@SessionScoped
public class ChatApplicationBean {

	private final static String ROOM_LIST_FILENAME = "roomBeans.txt";
	private final static String USER_LIST_FILENAME = "users.txt";

	private List<UserBean> userBeans;
	private List<RoomBean> roomBeans;

	private String name;
	private String username;
	private String password;

	public ChatApplicationBean() {
		loadRoomList();
		loadUserList();
	}

	private void loadUserList() {
		try {
			userBeans = new ArrayList<UserBean>();
			File file = new File(USER_LIST_FILENAME);
			file.createNewFile();

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				String[] credentials = line.split(":");
				userBeans.add(new UserBean(credentials[0], credentials[1]));
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
			roomBeans = new ArrayList<RoomBean>();
			File file = new File(ROOM_LIST_FILENAME);
			file.createNewFile();

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				roomBeans.add(new RoomBean(line));
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

	private UserBean findUser(String username) {
		for (int i = 0; i < userBeans.size(); i++) {
			if (userBeans.get(i).getName().equals(username))
				return userBeans.get(i);
		}
		return null;
	}

	public String enterChat() {
		UserBean user = findUser(username);
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
		System.out.println(userBeans == null);
		return userBeans.size();
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

	public List<RoomBean> getRoomBeans() {
		return roomBeans;
	}

	public String getNewChat() {
		return "chat";
	}

}
