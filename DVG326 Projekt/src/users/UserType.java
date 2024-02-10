package users;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import activity.Activity;
import activity.ActivityFolder;

public interface UserType {
	public void setActive(boolean bool);
	public boolean isActive();
	public void setUsername(String username);
	public String getUsername();
	public void setPassword(String password);
	public String getPassword(String masterkey);
	public boolean verifyPassword(String password);
	public HashMap<String, String> getUserInfo();
	public void remove();
	public void deleteDirectoryRecursionJava6(File file) throws Exception;
	public void changeFolderName(String oldName, String newName);
	public void changeActivityName(String oldName, String newName);
	public boolean userHasActivities();
	public void synchronizeActivities();
	public void addActivityFolder(String folderName);
	public void addActivity(String folderName, String activityName);
	public ArrayList<ActivityFolder> getActivityFolders();
	public HashMap<String, ArrayList<Activity>> getActivities();
	public Activity getActivityByFoldername(String folderName, String activityName);
	public HashMap<String, HashMap<String, String>> getRelevantData(Activity activityName);
    public HashMap<String, ArrayList<String>> getArrayData(Activity activityName);
    public BufferedImage getMapImage(String user,String folder);
}
