package users;

import java.util.HashMap;

public class NormalUser extends User {
	
	public NormalUser(HashMap<String, String> userInfo) {
		super(userInfo);
	}
	
	//Constructor for generating user in save files into system
	public NormalUser(String username) {
		super(username);
	}
}
