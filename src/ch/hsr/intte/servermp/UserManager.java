package ch.hsr.intte.servermp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import ch.hsr.intte.servermp.model.User;

public class UserManager {

	private Set<User> users;
	
	public UserManager() {
		loadDB();
	}
	
	private void loadDB() {
		try {
			users = new TreeSet<User>();
			File file = new File("user-list");
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
	public boolean isUsernameUnique(String name) {
		return false;
	}

	public User validateUser(String username, String password) {
		return null;
	}
	
	public User createUser(String username, String password) {
		return null;
	}
}
