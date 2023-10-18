package mainModel;

import java.util.ArrayList;
import java.util.HashMap;

import activity.Activity;
import activity.ActivityFolder;
import users.NormalUser;
import users.UserManager;

public class MainModel {
	
	private UserManager userManager;
	
	public MainModel() {
		userManager = new UserManager();
		//For testing
		userManager.addUser("ExampleUser", "Password");
	}
	//Add functionality that specifies which user the system should synchronize for
	public ArrayList<ArrayList<String>> synchronizeActivities(String username) {
		//add tracked data to ExampleUser
		//TODO: Utilize username parameter to get specific user to synchronize	
		NormalUser user = userManager.getUsers().get(0);		
		
		user.synchronizeActivities();
		
		//This code block prepares the ArrayList that should be returned 
		
		ArrayList<ActivityFolder> a = user.getActivityFolders();
		HashMap<String, ArrayList<Activity>> ac = user.getActivities();
		
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		
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
	}
}
