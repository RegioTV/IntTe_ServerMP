package ch.hsr.intte.servermp.model;

public class User {

	private String username;
	private String password;

	private Room room;

	public User(String username, String password) {
		this.username = username;
		this.password = hash(password);
	}

	private String hash(String password) {
		return Integer.toString(password.hashCode());
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Room getRoom() {
		return room;
	}

	public void enterRoom(Room room) {
		this.room = room;
	}

	public void leaveRoom() {
		room = null;
	}

	public String toString() {
		return "user " + username + " in " + room;
	}

	public boolean equals(Object object) {
		return object instanceof User && equals((User) object);
	}

	public boolean equals(User user) {
		return username.equals(user.username) && password.equals(user.password) && room.equals(user.room);
	}

}
