package model;

public class User {
	private String username;
	private String password; 
	
	public User() {
	}
	
	//Fully parameterized constructor
	public User(String userusername, String password) {
		
		this.username = userusername;
		this.password = password;
	}

	//Getter / Setter methods for each attribute of the class
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
	
}// end class
