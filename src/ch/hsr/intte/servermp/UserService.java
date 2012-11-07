package ch.hsr.intte.servermp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import ch.hsr.intte.servermp.model.User;

public class UserService {

	private Set<User> users;
	private final String USER_DB = "user-list";

	public UserService() {
		loadDB();
	}

	private synchronized void loadDB() {
		try {
			users = new TreeSet<User>();
			File file = new File(USER_DB);
			file.createNewFile();

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				String[] credentials = line.split(":");
				users.add(new User(credentials[0], credentials[1]));
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

	private synchronized void updateDB(User user) {
		try {
			File file = new File(USER_DB);
			file.createNewFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(user.toString() + "\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized boolean isUsernameUnique(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username))
				return false;
		}
		return true;
	}

	public User validateUser(String username, String password) {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.validate(password))
				return user;
		}
		return null;
	}

	public synchronized User createUser(String username, String password) {
		User user = new User(username, password);
		users.add(user);
		updateDB(user);
		return user;
	}
}
