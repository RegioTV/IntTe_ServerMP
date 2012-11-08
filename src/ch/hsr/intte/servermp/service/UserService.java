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
	User convert(String line) {
		String[] tokens = line.split(":");
		return new User(tokens[0], tokens[1]);
	}

	@Override
	String convert(User user) {
		return user.getUsername() + ":" + user.getPassword();
	}

}
