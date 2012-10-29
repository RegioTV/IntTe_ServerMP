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
public class ChatBean {

	private final static String ROOM_LIST_FILENAME = "rooms.txt";
	private final static String USER_LIST_FILENAME = "users.txt";

	private List<UserBean> userBeans;
	private List<ChatRoomBean> rooms;
	private String userName;

	public ChatBean() {
		loadRoomList();
		loadUserList();
	}

	private void loadUserList() {
		try {
			File file = new File(USER_LIST_FILENAME);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			do {
				line = reader.readLine();
				String[] credentials = line.split(":");
				userBeans.add(new UserBean(credentials[0], credentials[1]));
			} while (line != null);
			
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
		// TODO Auto-generated method stub

	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void enterChat() {

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
