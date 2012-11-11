package ch.hsr.intte.servermp.service;

import ch.hsr.intte.servermp.model.User;

public class UserService extends AbstractService<User> {

	private static final String USER_FILENAME = "users";

	private static UserService instance;

	public static UserService getInstance() {
		if (instance != null)
			return instance;
		else
			return instance = new UserService(USER_FILENAME);
	}

	private UserService(String filename) {
		super(filename);
	}

	@Override
	String getId(User user) {
		return user.getUsername();
	}

	@Override
	User deserialize(String line) {
		return User.deserialize(line);
	}

	@Override
	String serialize(User user) {
		return User.serialize(user);
	}

}
