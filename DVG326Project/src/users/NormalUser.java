package users;

import java.util.ArrayList;
import java.util.HashMap;

import activity.Activity;
import activity.ActivityFolder;
import activity.ActivityManager;

public class NormalUser implements UserType {
	
	private String username;
	private String password;
	private ActivityManager activityManager;
	
	public NormalUser(String username, String password) {
		setUsername(username);
		setPassword(password);
		activityManager = new ActivityManager();
	}
	
	public void addActivityFolder(String folderName) {
		activityManager.addActivityFolder(folderName);
	}
	
	public void addActivity(String folderName, String activityName) {
		activityManager.addActivity(folderName, activityName);
	}

	@Override
	public void setUsername(String username) {
		if(!username.isEmpty()) this.username = username;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getPassword(String masterkey) {
		if(masterkey.equals("brandbil123")) return password;
		else return null;
	}
	
	public boolean verifyPassword(String password) {
		return this.password.equals(password);
	}
	public void synchronizeActivities() {
		activityManager.synchronizeActivities();
	}
	public ArrayList<ActivityFolder> getActivityFolders() {
		return activityManager.getActivityFolders();
	}
	public HashMap<String, ArrayList<Activity>> getActivities() {
		HashMap<String, ArrayList<Activity>> hashMap = new HashMap<String, ArrayList<Activity>>();
		
		for(ActivityFolder aFolder : getActivityFolders()) {
			hashMap.put(aFolder.getFolderName(), aFolder.getActivities());
		}
		
		return hashMap;
	}
	
}
