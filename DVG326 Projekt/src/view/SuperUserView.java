package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;

@SuppressWarnings({ "serial", "deprecation" })
public class SuperUserView extends JPanel implements Observer {
	
	private Controller controller;
	private LoginView loginView;
	private JButton newUserBtn;
	private JButton removeUserBtn;
//	private JButton logoutBtn;
	private JComboBox<Object> userList;

	public SuperUserView(Controller controller, int width, int height) {
		
		this.controller = controller;
//		this.loginView = loginView;
		
		controller.registerToUserManager(this);
		
		setLayout(new GridLayout(2,2));
		setPreferredSize(new Dimension(width/4, height/4));
		setSize(new Dimension(width/4, height/4));
		
		this.setVisible(true);
		
//		logoutBtn = new JButton("Logout");
//		logoutBtn.addActionListener(e -> toggleVisible());
		
		userList = new JComboBox<>();
		updateUserList();
		
		newUserBtn = new JButton("Add new user");
		addUser();
		
		removeUserBtn = new JButton("Remove user");
		
		removeUserBtn.addActionListener(e -> removeUser(userList.getSelectedItem().toString()));
		JLabel infoText = new JLabel();
		infoText.setText("<html><body>Choose existing user from list<br>and then click 'Remove user' to remove them<br>, or click 'Add new user'<br>to create a new user</body></html>");
		add(infoText);
		add(userList);
		add(newUserBtn);
		add(removeUserBtn);
		
	}
	
	
	public void toggleVisible() {
//		System.out.println(loginView.isVisible() + " " + this.isVisible());
		if(!loginView.isVisible()) {
			this.setVisible(false);
			loginView.setVisible(true);
		}
		else {
			this.setVisible(true);
			loginView.setVisible(false);
//			System.out.println(loginView.isVisible() + " " + this.isVisible());
		}
		
		revalidate();
		repaint();
		
	}
	
	public void updateUserList() {
		
		userList.removeAllItems();
		revalidate();
		
		ArrayList<String> users = controller.getAllUsers();
		
		for(String user : users) userList.addItem(user);
		
	}
	
	public void addUser() {
		
		JPanel dialog = new JPanel(new GridLayout(6,1));
		
		JTextField newUsername = new JTextField();
		JTextField newPassword = new JTextField();
		JTextField newName = new JTextField();
		JTextField newSurname = new JTextField();
		JTextField newAge = new JTextField();
		JTextField newWeight = new JTextField();
		
		dialog.add(new JLabel("Username"));
		dialog.add(newUsername);
		dialog.add(new JLabel("Password"));
		dialog.add(newPassword);
		dialog.add(new JLabel("Name"));
		dialog.add(newName);
		dialog.add(new JLabel("Surname"));
		dialog.add(newSurname);
		dialog.add(new JLabel("Age"));
		dialog.add(newAge);
		dialog.add(new JLabel("Weight (kg)"));
		dialog.add(newWeight);
		
		newUserBtn.addActionListener(e -> {
			int dialogResult = JOptionPane.showConfirmDialog(
					null,
					dialog,
					"New user",
					 JOptionPane.OK_CANCEL_OPTION,
                     JOptionPane.PLAIN_MESSAGE
					);
			if(dialogResult == 0) {
				if(checkUserInfo(newUsername.getText(), newPassword.getText(), newName.getText(), newSurname.getText(), newAge.getText(), newWeight.getText())) {
					if(controller.addUser(createUserInfo(newUsername.getText(), newPassword.getText(), newName.getText(), newSurname.getText(), newAge.getText(), newWeight.getText()))== false) {
                        JOptionPane.showMessageDialog(null,
                                new JLabel("User creation failed, a user with username "+"'"+newUsername.getText()+"'"+" alredy exists")
                                );
                    }else {
                        updateUserList();
                    }
			}else {
					System.out.println("LoginView: illegal character detected");
					JOptionPane.showMessageDialog(
							null,
							new JLabel("User creation failed, a username and password is required! Refrain from using the following characters ': ; _'")
							//If possible, add code here that automatically open the user creation dialog again
							);
				}
			}
		});
	}
	
	public boolean checkUserInfo(String u, String p, String n, String s, String a, String w) {
		String combinedStr = u+p+n+s+a+w;
		Pattern pattern = Pattern.compile("[:;_]");
		Matcher matcher = pattern.matcher(combinedStr);
		
		if(matcher.find()) return false;
		if(u.isBlank()) return false;
		if(p.isBlank()) return false;
		
		return true;
	}
	
	public HashMap<String, String> createUserInfo(String u, String p, String n, String s, String a, String w) {
		HashMap<String, String> userInfo = new HashMap<>();
		userInfo.put("username", u);
		userInfo.put("password", p);
		userInfo.put("name", n);
		userInfo.put("surname", s);
		userInfo.put("age", a);
		userInfo.put("weight", w);
		return userInfo;
	}
	
	public void removeUser(String username) {
		if(controller.removeUser(username) == true) {
			updateUserList();
		}else {
			JOptionPane.showMessageDialog(
					null,
					new JLabel("Failed to remove user")
					);
		}
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updateUserList();
//		if(logoutBtn.isFocusOwner())toggleVisible();
	}
}
