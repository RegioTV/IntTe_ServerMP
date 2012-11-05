package ch.hsr.intte.servermp;

public class User {
	
	private String username;
	private String password;

	User (String name, String password) {
		
	}
	
	public String getName() {
		return username;
	}
	
	public boolean validate(String password) {
		return this.password.equals(password);
	}

}
