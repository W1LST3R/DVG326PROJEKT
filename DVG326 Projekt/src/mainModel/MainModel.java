package mainModel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import activity.Activity;
import activity.ActivityFolder;
import users.User;
import users.UserManager;

@SuppressWarnings("deprecation")
public class MainModel extends Observable {
	
	private UserManager userManager;
	
	public MainModel() {
		userManager = new UserManager();
	}
	
	public void loadUsers() {
		userManager.loadUsers();
	}
	
	public boolean addUser(HashMap<String, String> userInfo) {
		return userManager.addUser(userInfo);
		// verifyUser(userInfo.get("username"), userInfo.get("password"));
	}
	

	public boolean verifyUser(String username, String password) {
		if(userManager.verifyUser(username, password)) {update(); return true;}
		else return false;
	}
	
	public void userLogout() {
		userManager.userLogout();
		update();
	}
	
	public boolean userHasActivities() {
		return userManager.userHasActivities();
	}
	
	public String getActiveUser() {
		return userManager.getActiveUser();
	}
	
	public HashMap<String, String> getUserInfo() {
		return userManager.getUserInfo();
	}
	
	public ArrayList<String> getAllUsers() {
		return userManager.getAllUsers();
	}
	
	public boolean removeUser(String username) {
		return userManager.removeUser(username);
		//update();
	}
	
	public ArrayList<ArrayList<String>> synchronizeActivities() {
		
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		
		try {
			//Adds activities based on active user
			User user = userManager.getUserByUsername(getActiveUser());
			
			//Since all "known" activities get synchronized, this allows a user to synchronize only once per session
			if(user.getActivities().isEmpty()) user.synchronizeActivities();
			
			//This code block prepares the ArrayList that should be returned 
			
			ArrayList<ActivityFolder> a = user.getActivityFolders();
			HashMap<String, ArrayList<Activity>> ac = user.getActivities();
			
			for(int i = 0; i < a.size(); i++) {
				String currentFolder = a.get(i).getFolderName();
				ArrayList<String> strArr = new ArrayList<String>();
				strArr.add(currentFolder);
				
				for(int k = 0; k < ac.get(currentFolder).size(); k++) {
					strArr.add(ac.get(currentFolder).get(k).getActivityName());
				}
				arr.add(strArr);
			}
			
			//End code block	
			return arr;
			
		} catch(NullPointerException e) {
			System.out.println("MainModel: synchronizeActivites() got cancelled, probably because getActiveUser() returned '" + getActiveUser() + "'");
			return arr;
		}
	}
	
	public HashMap<String, ArrayList<String>> getUserActivities() {
		
		HashMap<String, ArrayList<String>> arr = new HashMap<>();
		
		if(getActiveUser() != null) {
			HashMap<String, ArrayList<Activity>> arrAct = userManager.getUserByUsername(getActiveUser()).getActivities();
			
			arrAct.forEach((key, value) -> {
				ArrayList<String> actNames = new ArrayList<>();
				for(Activity act : value) actNames.add(act.getActivityName());
				arr.put(key, actNames);
			});
		}
		
		return arr;
	}
	
	public void changeFolderName(String oldName, String newName) {
		if(userHasActivities()) {
			userManager.getUserByUsername(getActiveUser()).changeFolderName(oldName, newName);
			update();
		}
	}
	
	public void changeActivityName(String oldName, String newName) {
		if(userHasActivities()) {
			userManager.getUserByUsername(getActiveUser()).changeActivityName(oldName, newName);
			update();
		}
	}
	
	public BufferedImage getMapImage(String username, String folder){
        User tempUser = userManager.getUserByUsername(username);
       return tempUser.getMapImage(username,folder);
   }
	
	public HashMap<String, HashMap<String, String>> getActivityData(String username, String folderName, String activityName) {
        User tempUser = userManager.getUserByUsername(username);
        Activity tempActivity = tempUser.getActivityByFoldername(folderName,activityName);
        return tempUser.getRelevantData(tempActivity);
    }

    public HashMap<String, ArrayList<String>> getArrayData(String username, String folderName, String activityName) {
        User tempUser = userManager.getUserByUsername(username);
        Activity tempActivity = tempUser.getActivityByFoldername(folderName,activityName);
        return tempUser.getArrayData(tempActivity);
    }
	
	public void update() {
		setChanged();
		notifyObservers();
	}
}