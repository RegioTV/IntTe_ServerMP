package ch.hsr.intte.servermp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	public String currentUsername;
	public String password;

	public ChatApplicationBean() {
		loadRoomList();
		loadUserList();
	}

	private void loadUserList() {
		try {
			File file = new File(USER_LIST_FILENAME);
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
			File file = new File(ROOM_LIST_FILENAME);
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

	private UserBean findUser(String userName) {
		for (int i = 0; i < userBeans.size(); i++) {
			if (userBeans.get(i).getName().equals(userName))
				return userBeans.get(i);
		}
		return null;
	}

	public String enterChat() {
		UserBean user = findUser(currentUsername);
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

}
