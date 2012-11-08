package ch.hsr.intte.servermp.model;

public class User {

	private String username;
	private String password;

	private Room room;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public boolean validate(String password) {
		return this.password.equals(password);
	}

	public String toString() {
		return username + ":" + password;
	}

	public void enterRoom(Room room) {
		this.room = room;
	}

	public void leaveRoom() {
		room = null;
	}

	public Room getRoom() {
		return room;
	}

}
