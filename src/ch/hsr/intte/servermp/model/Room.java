package ch.hsr.intte.servermp.model;

import java.util.Collection;
import java.util.HashSet;

import ch.hsr.intte.servermp.ServiceFactory;
import ch.hsr.intte.servermp.services.UserService;

public class Room {

	private String name;

	private UserService userService = ServiceFactory.getUserService();

	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Collection<User> getUsers() {
		Collection<User> users = new HashSet<User>();
		for (User user : userService.findAll())
			if (user.getRoom().equals(this))
				users.add(user);

		return users;
	}

}
