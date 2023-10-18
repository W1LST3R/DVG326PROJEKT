package users;

import java.util.ArrayList;

/**
 * 
 * @author simon
 * @info this class purpose is to manage all "normal" users in the system
 * @version 2023-10-17
 * 
 */

public class UserManager {
	
	ArrayList<NormalUser> users;
	
	public UserManager() {
		users = new ArrayList<NormalUser>();
	}
	
	public void addUser(String username, String password) {
		users.add(new NormalUser(username, password));
	}
	public ArrayList<NormalUser> getUsers() {
		return users;
	}
	//Purpose of this function is to eventually handle persistent save file of user data
	public void loadUsers() {
		
	}
}
