package ch.hsr.intte.servermp.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String ATTRIBUTE_SEPERATOR = ":";
	private static final Room NO_ROOM = new Room("no room");

	private String username;
	private String password;

	private Room room = NO_ROOM;

	public static String serialize(User user) {
		return user.username + ATTRIBUTE_SEPERATOR + user.password;
	}

	public static User deserialize(String line) {
		String[] tokens = line.split(ATTRIBUTE_SEPERATOR);
		if (tokens.length == 2)
			return create(tokens[0], tokens[1]);
		else
			throw new IllegalArgumentException();
	}

	private static User create(String username, String password) {
		User user = new User();

		user.username = username;
		user.password = password;

		return user;
	}

	private User() {}

	public User(String username, String password) {
		this.username = username;
		this.password = hash(password);
	}

	public String getUsername() {
		return username;
	}

	public Room getRoom() {
		return room;
	}

	public void enterRoom(Room room) {
		this.room = room;
	}

	public void leaveRoom() {
		room = NO_ROOM;
	}

	public boolean verify(String password) {
		return hash(password).equals(this.password);
	}

	private String hash(String password) {
		return Integer.toString(password.hashCode());
	}

	public String toString() {
		return "user " + username + " in " + room;
	}

	public boolean equals(Object object) {
		return object != null && object instanceof User && equals((User) object);
	}

	private boolean equals(User user) {
		return username.equals(user.username) && password.equals(user.password) && room.equals(user.room);
	}

}
