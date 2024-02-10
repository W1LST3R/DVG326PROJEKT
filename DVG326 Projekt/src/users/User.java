package users;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import activity.Activity;
import activity.ActivityFolder;
import activity.ActivityManager;
import inputData.UserData;

public abstract class User implements UserType {

	private ActivityManager activityManager;
	private UserData userData;
	private boolean active;
	
	public User(HashMap<String, String> userInfo) {
		userData = new UserData(userInfo);
		activityManager = new ActivityManager(userInfo.get("username"));
		setActive(false);
	}
	
	//Constructor for generating user in save files into system
	public User(String username) {
		userData = new UserData(username);
		activityManager = new ActivityManager(username);
		setActive(false);
	}
	
	public void setActive(boolean bool) {
		active = bool;
	}
	
	public boolean isActive() {
		return active;
	}

	@Override
	public void setUsername(String username) {
		if(!username.isEmpty()) userData.setUsername(username);
	}

	@Override
	public String getUsername() {
		return userData.getUsername();
	}

	@Override
	public void setPassword(String password) {
		userData.setPassword(password);
	}
	
	@Override
	public String getPassword(String masterkey) {
		if(masterkey.equals("brandbil123")) return userData.getPassword();
		else return null;
	}
	
	public boolean verifyPassword(String password) {
		return userData.getPassword().equals(password);
	}
	
	public HashMap<String, String> getUserInfo() {
		return userData.getUserInfo();
	}
	
	public boolean userHasActivities() {
		if(activityManager.getActivityFolders().size() != 0) return true;
		else return false;
	}
	
	public void remove() {
		
		String username = getUsername();
		
		File path = new File("./userData/" + username);
		
		try {
			deleteDirectoryRecursionJava6(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.out.println("NormalUser: user '" + username + "' was successfully deleted");
	}
	
	//Method taken from: https://softwarecave.org/2018/03/24/delete-directory-with-contents-in-java/ 2023-10-27
	public void deleteDirectoryRecursionJava6(File file) throws IOException {
		  if (file.isDirectory()) {
			    File[] entries = file.listFiles();
			    if (entries != null) {
			      for (File entry : entries) {
			        deleteDirectoryRecursionJava6(entry);
			      }
			    }
		  }
		  if (!file.delete()) {
			  throw new IOException("Failed to delete " + file);
		  }
	}
	
	public void changeFolderName(String oldName, String newName) {
		activityManager.changeFolderName(oldName, newName);
	}
	
	public void changeActivityName(String oldName, String newName) {
		activityManager.changeActivityName(oldName, newName);
	}
	
	public void synchronizeActivities() {
		activityManager.synchronizeActivities();
	}
	public void addActivityFolder(String folderName) {
		activityManager.addActivityFolder(folderName);
	}
	
	public void addActivity(String folderName, String activityName) {
		activityManager.addActivity(folderName, activityName);
	}
	public ArrayList<ActivityFolder> getActivityFolders() {
		return activityManager.getActivityFolders();
	}
	public HashMap<String, ArrayList<Activity>> getActivities() {
		return activityManager.getActivities();
	}
	
	public Activity getActivityByFoldername(String folderName, String activityName) {
		return activityManager.getActivityByFoldername(folderName, activityName);
	}
	
	public HashMap<String, HashMap<String, String>> getRelevantData(Activity activityName){
        return activityManager.getRelevantData(activityName);
    }

    public HashMap<String, ArrayList<String>> getArrayData(Activity activityName){
        return activityManager.getArrayData(activityName);
    }
//    @Override
    public BufferedImage getMapImage(String user,String folder){
        return activityManager.getMapImage(user,folder);
    }
}
