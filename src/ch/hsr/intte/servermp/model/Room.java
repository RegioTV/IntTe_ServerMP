package ch.hsr.intte.servermp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.intte.servermp.service.UserService;

public class Room implements Serializable {

	private static final long serialVersionUID = 6206121114701018231L;

	private String name;

	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Collection<User> getUsers() {
		Collection<User> users = new ArrayList<>();
		for (User user : UserService.getInstance().findAll())
			if (user.getRoom().equals(this))
				users.add(user);

		return users;
	}

	public String toString() {
		return "room " + name;
	}

	public boolean equals(Object object) {
		return object != null && object instanceof Room && name.equals(((Room) object).name);
	}

}
