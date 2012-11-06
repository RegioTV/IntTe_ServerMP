package ch.hsr.intte.servermp.model;

public class User {
	
	private String username;
	private String password;

	public User (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getName() {
		return username;
	}
	
	public boolean validate(String password) {
		return this.password.equals(password);
	}

}