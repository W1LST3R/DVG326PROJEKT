package activity;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityFolder {
	
	private String folderName;
	private ArrayList<Activity> activities;
	
	public ActivityFolder(String folderName) {
		setFolderName(folderName);
		activities = new ArrayList<Activity>();
	}
	
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public String getFolderName() {
		return folderName;
	}
	
	public ArrayList<Activity> getActivities() {
		return activities;
	}
	
	public void addActivity(String activityName) {
		activities.add(new Activity(activityName));
	}
}
