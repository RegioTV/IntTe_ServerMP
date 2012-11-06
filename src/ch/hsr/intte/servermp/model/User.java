package ch.hsr.intte.servermp.model;

public class User {
	
	private String username;
	private String password;

	public User (String name, String password) {
		
	}
	
	public String getName() {
		return username;
	}
	
	public boolean validate(String password) {
		return this.password.equals(password);
	}

}
