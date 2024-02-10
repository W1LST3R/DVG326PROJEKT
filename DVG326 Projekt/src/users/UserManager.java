package users;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author simon
 * @info this class purpose is to manage all "normal" users in the system
 * @version 2023-10-17
 * 
 */

public class UserManager {
	
	ArrayList<User> users;
	
	public UserManager() {
		users = new ArrayList<User>();
	}
	public boolean addUser(HashMap<String, String> userInfo) {
		boolean flag = false;
        String username = userInfo.get("username");
        String password = userInfo.get("password");

        if(username.isBlank() || password.isBlank()) {
            System.out.println("UserManager: username '"+username+"' or password '" + password + "' is wrong or empty");
        }else {
            if(!userExist(username)){
                 users.add(new NormalUser(userInfo));
                 flag = true;
            }else {
            	System.out.println("UserManager: user with username '" + username + "' already exist");
            }
        }
        return flag;
	}
	
	//method for generating users into system from save file
	public void addUser(String username) {
		users.add(new NormalUser(username));
	}
	
	public String getActiveUser() {
		for(User user : users) {
//			System.out.println("UserManager: users [" + user.getUsername() + "] isActive: " + user.isActive());
			if(user.isActive()) return user.getUsername(); 
		}
		return null;
	}
	
	//Possibly make so that we return a copy instead of the actual users list
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public ArrayList<String> getAllUsers() {
		ArrayList<String> arr = new ArrayList<>();
		for(User user : getUsers()) {
			arr.add(user.getUsername());
		}
		return arr;
	}
	
	public boolean removeUser(String username) {
		if(userExist(username)){
			getUserByUsername(username).remove();
			for(int i = 0; i < getUsers().size(); i++) {
				if(getUsers().get(i).getUsername().equals(username)) {
					getUsers().remove(i);
					break;
				}
			}
			return true;
		}else{
			System.out.println("UserManager: User '" + username + "' can't be removed since they don't exist");
			return false;
		}
	}
	
	public boolean verifyUser(String username, String password) {
		
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				if(user.verifyPassword(password)) {userLogin(user); return true;}
				else System.out.println("UserManager: incorrect password for '" + username + "'");
			}
		}
		if(getUserByUsername(username) == null) System.out.println("UserManager: User with username '" + username + "' was not found");
		return false; 
	}
	
	public void userLogin(User user) {
		user.setActive(true);
	}
	
	public void userLogout() {
		getUserByUsername(getActiveUser()).setActive(false);
	}
	
	public boolean userExist(String username) {
		return getUserByUsername(username) != null;
	}
	
	public boolean userHasActivities() {
		if(getActiveUser() != null)
		return getUserByUsername(getActiveUser()).userHasActivities();
		else return false;
	}
	
	public User getUserByUsername(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public HashMap<String, String> getUserInfo() {
		if(getActiveUser() != null) return getUserByUsername(getActiveUser()).getUserInfo();
		else return null;
	}
	
	//Purpose of this function is to eventually handle persistent save file of user data
	public void loadUsers() {
		
		//this will be the file path
		File dir = new File("./userData/");
			
		getDir(dir.listFiles());
	}
		
	public void getDir(File[] files) {
		for (File dir : files) {
			if (dir.isDirectory()) addUser(dir.getName());
		}
	}
}