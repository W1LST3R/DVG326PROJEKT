package activity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ActivityManager {
	
	private ArrayList<ActivityFolder> activityFolders;
	private String username;
	
	//Variables used for storing activities persistently
	private HashMap<String, ArrayList<String>> dirWithFiles = new HashMap<>();
	private String currentDir;
	private ArrayList<String> tempArr;
	
	public ActivityManager(String username) {
		activityFolders = new ArrayList<ActivityFolder>();
		this.username = username;
		loadActivities();
	}
	
	public void loadActivities() {
		File dir = new File("./userData/" + username + "/activities/");
		
		//if user has synchronized before, load those files into system
		if(dir.exists()) getFiles(dir.listFiles());
	}
	
	public void synchronizeActivities() {
		//this will be the file path from where activities get synchronized
		File dir = new File("./csv");
		
		//get files from csv dir
		getFiles(dir.listFiles());
		
		//Copy files from csv dir into a new directory for user who synchronizes
		for(String dirName : dirWithFiles.keySet()) {
			createDir(dirName);
			ArrayList<String> value = dirWithFiles.get(dirName);
			for(int i = 0; i < value.size(); i++) {
				String fileName = value.get(i).substring(0, value.get(i).indexOf(":"));
				String originalFilePath = value.get(i).substring(value.get(i).indexOf(":")+1);
				
				createFileInFrom(fileName, dirName, originalFilePath);
			}
		}
	}
	
	public synchronized void getFiles(File[] files) {
		
        for (File file : files) {
            if (file.isDirectory()) {
            	if(!file.getName().equals(".DS_Store")) {
            		//add activity folder to user locally
            		addActivityFolder(file.getName());
            		
            		//prepare for storing activities persistently 
            		currentDir = file.getName();

            		tempArr = new ArrayList<>();
            		dirWithFiles.put(currentDir, tempArr);
            	}
                getFiles(file.listFiles()); //calls same method again
            } else {
            	if(!file.getName().equals(".DS_Store")) {
            		String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            		
            		//add activity to user locally
            		activityFolders.get(activityFolders.size()-1).addActivity(fileName,file.getAbsolutePath());
            		
            		//prepare persistent storage of activity
            		tempArr.add(fileName + ".csv:" + file.getAbsolutePath());            		
            	}
            }
        }
	}
	
	public void createDir(String dirName) {
		File dir = new File("./userData/" + username + "/activities");
		if(dir.mkdir()) System.out.println("ActivityManager: 'activities' directory created successfully for '" + username + "'");
//		else System.out.println("ActivityManager: activities dir for '" + username + "' already exist");
		
		File actDir = new File("./userData/" + username + "/activities/" + dirName);
		if(actDir.mkdir()) System.out.println("ActivityManager: Directory '" + dirName + "' created successfully for '" + username + "'");
		else System.out.println("ActivityManager: Directory '" + dirName + "' for '" + username + "' already exist");
	}
	
	public void createFileInFrom(String fileName, String dirName, String originalFilePath) {
		//Create file which matches original name of activity file
		File file = new File("./userData/" + username + "/activities/" + dirName + "/" + fileName);

		try {
			if(file.createNewFile()) {
				//Potentially add code which creates a new thread for the writeToFile to run on.
				System.out.println("ActivityManager: File '" + file.getName() + "' created successfully for '" + username + "'");
				writeToFile(file, originalFilePath);
			}
			else System.out.println("ActivityManager: File '" + file.getName() + "' already exist for '" + username + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void writeToFile(File file, String originalFilePath) {
		FileWriter fw = null;
		Scanner scanner = null;
		
		try {
			fw = new FileWriter(file);
			
			//Copy original file into new file
			String content = "";
			
			scanner = new Scanner(new File(originalFilePath));
//			scanner.useDelimiter("");
			
			while(scanner.hasNext()) content += scanner.nextLine() + "\n";
			
			scanner.close();
			
			fw.write(content);
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
	
	public void changeFileName(String folderName, String oldName, String newName) {
//		System.out.println("changeFileName" + folderName);
		File oldFileName = new File("./userData/" + username + "/activities/" + folderName + "/"+ oldName + ".csv");
		File newFileName = new File("./userData/" + username + "/activities/" + folderName + "/"+ newName + ".csv");
		
		oldFileName.renameTo(newFileName);
	}
	
	public void changeDirName(String oldName, String newName) {
//		System.out.println("changeDirName");
		File oldDirName = new File("./userData/" + username + "/activities/" + oldName);
		File newDirName = new File("./userData/" + username + "/activities/" + newName);
		
		oldDirName.renameTo(newDirName);
	}
	
	public void changeFolderName(String oldName, String newName) {
		for(ActivityFolder actF : activityFolders) {
			if(actF.getFolderName().equals(oldName)) {
				changeDirName(oldName, newName);
				actF.setFolderName(newName);
			}
		}
	}
	
	public void changeActivityName(String oldName, String newName) {
		for(ActivityFolder actF : activityFolders) {
			for(Activity act : actF.getActivities())
				if(act.getActivityName().equals(oldName)) {
					changeFileName(actF.getFolderName(), oldName, newName);
					act.setActivityName(newName);
				}
		}
	}
	
	public void addActivityFolder(String name) {
		activityFolders.add(new ActivityFolder(name));
	}
	
	public void addActivityFolder(String name, String filePath) {
		activityFolders.add(new ActivityFolder(name, filePath));
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
	
	public HashMap<String, ArrayList<Activity>> getActivities() {
		HashMap<String, ArrayList<Activity>> hashMap = new HashMap<String, ArrayList<Activity>>();
		
		for(ActivityFolder aFolder : getActivityFolders()) {
			hashMap.put(aFolder.getFolderName(), aFolder.getActivities());
		}
		
		return hashMap;
	}
	
	public Activity getActivityByFoldername(String folderName, String activityName) {
		for(Activity a : getActivities().get(folderName)) {
			if(a.getActivityName().equals(activityName)) return a;
		}
		return null;
	}
	
	public HashMap<String, HashMap<String, String>> getRelevantData(Activity activityName){
        return activityName.getRelevantData();
    }

    public HashMap<String, ArrayList<String>> getArrayData(Activity activityName){
        return activityName.getArrayData();
    }
    
    public BufferedImage getMapImage(String user,String folder){
        return new ActivityImage(user,folder).getMapImage();
    }
}