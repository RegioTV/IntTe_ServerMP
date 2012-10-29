package ch.hsr.intte.servermp;

public class UserBean {
	
	private String username;
	private String password;

	UserBean (String name, String password) {
		
	}
	
	public String getName() {
		return username;
	}
	
	public boolean validate(String password) {
		return this.password.equals(password);
	}

}
