package ch.hsr.intte.servermp.model;

import java.util.Collection;
import java.util.HashSet;

import ch.hsr.intte.servermp.service.UserService;

public class Room {

	private String name;

	private UserService userService = UserService.getInstance();

	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Collection<User> getUsers() {
		Collection<User> users = new HashSet<>();
		for (User user : userService.findAll())
			if (user.getRoom().equals(this))
				users.add(user);

		return users;
	}

	public String toString() {
		return "room " + name;
	}

	public boolean equals(Object object) {
		return object instanceof Room && name.equals(((Room) object).name);
	}

}
