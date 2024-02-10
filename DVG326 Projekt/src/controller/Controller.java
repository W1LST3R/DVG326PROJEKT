package controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

import mainModel.MainModel;

@SuppressWarnings("deprecation")
public class Controller {
	
	private MainModel model;
	
	public Controller(MainModel model) {
		this.model = model;
		this.model.loadUsers();
	}
	
	public void registerToUserManager(Observer o) {
		model.addObserver(o);
	}
	
	public boolean verifyUser(String username, String password) {
		return model.verifyUser(username, password);
	}
	
	public boolean addUser(HashMap<String, String> userInfo) {
		return model.addUser(userInfo);
	}
	
	
	public String getActiveUser() {
		return model.getActiveUser();
	}
	
	public void userLogout() {
		model.userLogout();
	}
	
	public boolean userHasActivities() {
		return model.userHasActivities();
	}
	
	public ArrayList<String> getAllUsers() {
		return model.getAllUsers();
	}
	
	public boolean removeUser(String username) {
		return model.removeUser(username);
	}
	
	public void changeFolderName(String oldName, String newName) {
		model.changeFolderName(oldName, newName);
	}
	
	public void changeActivityName(String oldName, String newName) {
		model.changeActivityName(oldName, newName);
	}
	
	public HashMap<String, ArrayList<String>> getUserActivities() {
		return model.getUserActivities();
	}
	
	public HashMap<String, String> getUserInfo() {
		return model.getUserInfo();
	}
	
	public HashMap<String, HashMap<String, String>> getActivityData(String username, String folderName, String activityName){
        return model.getActivityData(username, folderName, activityName);
    }

    public HashMap<String, ArrayList<String>> getArrayData(String username, String folderName, String activityName){
        return model.getArrayData(username, folderName, activityName);
    }
    
    public BufferedImage getMapImage(String username, String folder){
        return model.getMapImage(username,folder);
    }
	
	public ArrayList<ArrayList<String>> synchronizeActivities() {
		return model.synchronizeActivities();
	}
}