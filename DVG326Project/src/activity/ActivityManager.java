package activity;

import java.io.File;
import java.util.ArrayList;

public class ActivityManager {
	
	private ArrayList<ActivityFolder> activityFolders;
	
	public ActivityManager() {
		activityFolders = new ArrayList<ActivityFolder>();
	}
	
	public void synchronizeActivities() {
		//this will be the file path
		File dir = new File("./csv");
		
		addFiles(dir.listFiles());
	}
	
	public void addFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
            	if(!file.getName().equals(".DS_Store")) {
            		addActivityFolder(file.getName());
            	}
                addFiles(file.listFiles()); // Calls same method again.
            } else {
            	if(!file.getName().equals(".DS_Store")) {
            		activityFolders.get(activityFolders.size()-1).addActivity(file.getAbsolutePath());
            	}
            }
        }
	}
	
	public void addActivityFolder(String name) {
		activityFolders.add(new ActivityFolder(name));
	}
	
	public void addActivity(String folderName, String activityName) {
		int index = 0;
		
		for(ActivityFolder a : activityFolders) {
			if(a.getFolderName().equals(folderName)) {
				activityFolders.get(index).addActivity(activityName);
				break;
			}
			index++;
		}
	}
	
	public ArrayList<ActivityFolder> getActivityFolders() {
		return activityFolders;
	}
}
