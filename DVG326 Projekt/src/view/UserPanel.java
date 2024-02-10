package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;

@SuppressWarnings({ "serial", "deprecation" })
public class UserPanel extends JPanel implements Observer {
	
//	private String username;
	private Controller controller;
	private JLabel usernameLabel;
	private JPanel userInfoPanel;
	private JPanel userActionPanel;
	private JButton logoutBtn;
	private JLabel filler;
	private JButton changeNamesBtn;
	
	public UserPanel(Controller controller, int width, int height) {
		
		controller.registerToUserManager(this);
		
		this.controller = controller;
		
		setLayout(new GridLayout(1, 2));
		
		logoutBtn = new JButton("Logout");
		
		usernameLabel = new JLabel("");
		
		logoutBtn.addActionListener(e -> controller.userLogout());
		
		userInfoPanel = new JPanel(new GridLayout(2,2));
		userInfoPanel.setPreferredSize(new Dimension(width/2, height));
		userInfoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		userActionPanel = new JPanel(new GridLayout(2,2));
		userActionPanel.setPreferredSize(new Dimension(width/2, height));
		userActionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//add support for modifying activities
		changeNamesBtn = new JButton("Modify synchronized activities");
		filler = new JLabel("");
		
		add(userInfoPanel, BorderLayout.CENTER);
		add(userActionPanel, BorderLayout.SOUTH);
		
//		add(usernameLabel);
//		add(logoutBtn);
		
		prepareChangeNameDialog();
		
	}
	
	//Variables used in prepareChangeNameDialog function
	private int actIndex;
	private int folderIndex;
	private JComboBox<Object> changeFoldernameBox;
	private JComboBox<Object> changeActivitynameBox;
	private JTextField newFoldernameField;
	private JTextField newActnameField;
	
	public void prepareChangeNameDialog() {
		JPanel dialog = new JPanel(new GridLayout(2,2));
		
		changeFoldernameBox = new JComboBox<>();
		changeActivitynameBox = new JComboBox<>();
		
		updateChangeNameDialog();
		
		newFoldernameField = new JTextField();
		newActnameField = new JTextField();
		
		dialog.add(changeFoldernameBox);
		dialog.add(newFoldernameField);
		dialog.add(changeActivitynameBox);
		dialog.add(newActnameField);
		
		changeNamesBtn.addActionListener(e -> {
			updateChangeNameDialog();
			
			int dialogResult = JOptionPane.showConfirmDialog(
					null,
					dialog,
					"Modify activities",
					 JOptionPane.OK_CANCEL_OPTION,
                     JOptionPane.PLAIN_MESSAGE
					);
			if(dialogResult == 0) {
				//Get selected and get new names
				
				String selectedFolder = "";
				String selectedActivity = "";
				String newFolderName = "";
				String newActName = "";
				
				try {
				selectedFolder = changeFoldernameBox.getSelectedItem().toString();
				selectedActivity = changeActivitynameBox.getSelectedItem().toString().substring(changeActivitynameBox.getSelectedItem().toString().indexOf(":")+2);
				
				newFolderName = newFoldernameField.getText();
				newActName = newActnameField.getText();
				} catch(NullPointerException ev) {
					System.out.println("UserPanel: containers are empty");
				} catch(StringIndexOutOfBoundsException s) {
					System.out.println("UserPanel: field(s) are empty");
				}
				
//				System.out.println(selectedActivity + " " + newActName);
				
				if(!newFolderName.isBlank() && !selectedFolder.isBlank()) controller.changeFolderName(selectedFolder ,newFolderName); 
				
				if(!newActName.isBlank() && !selectedActivity.isBlank()) controller.changeActivityName(selectedActivity, newActName);
				
				newFoldernameField.setText("");
				newActnameField.setText("");
				
			}
		});
	}
	
	public void updateChangeNameDialog() {
		changeFoldernameBox.removeAllItems();
		changeActivitynameBox.removeAllItems();
		
		folderIndex = 0;
		
		String[] foldersStr = new String[getUserActivities().size()];
		
		getUserActivities().forEach((key, value) -> {
			
			String[] activityStr = new String[value.size()];
			actIndex = 0;
			for(String act : value) activityStr[actIndex++] = act;
			
			foldersStr[folderIndex++] = key;
			
			
			for(String s : activityStr) changeActivitynameBox.addItem(key + ": " + s);
			
			if(folderIndex == getUserActivities().size()) {
				for(String s : foldersStr) changeFoldernameBox.addItem(s);
			}
		});
		
	}
	
	public HashMap<String, String> getUserInfo() {
		return controller.getUserInfo();
	}
	
	public void updateUserInfo() {
		usernameLabel.setText("User: " + controller.getActiveUser());
		userInfoPanel.removeAll();
		
		userActionPanel.add(usernameLabel);
		userActionPanel.add(logoutBtn);
		userActionPanel.add(filler);
		userActionPanel.add(changeNamesBtn);
		
		HashMap<String, String> userInfo = controller.getUserInfo();
		
		if(userInfo != null) {
			/*
			userInfo.forEach((key, value) -> {
				if(!key.equals("password") && !key.equals("username")) 
					userInfoPanel.add(new JLabel(key + ": " + value));
				
			});
			*/
			
			userInfoPanel.add(new JLabel("Name: " + userInfo.get("name")));
			userInfoPanel.add(new JLabel("Age: " + userInfo.get("age")));
			userInfoPanel.add(new JLabel("Surname: " + userInfo.get("surname")));
			
			userInfoPanel.add(new JLabel("Weight: " + userInfo.get("weight")));
		}
		
	}
	
	public HashMap<String, ArrayList<String>> getUserActivities() {
		return controller.getUserActivities();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updateUserInfo();
//		updateChangeNameDialog();
	}
}