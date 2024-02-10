package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Controller;

@SuppressWarnings({ "serial", "deprecation" })
public class LoginView extends JPanel implements Observer {
	
	private Controller controller;
	
	public LoginView(Controller controller, int width, int height) {
		this.controller = controller;
		controller.registerToUserManager(this);
//		this.setVisible(false);
//		setLayout(new GridLayout(2,1));
		setSize(new Dimension(width, height));
		setBorder(BorderFactory.createLineBorder(Color.yellow));
		
		
		JPanel credentialsPanel = new JPanel(new GridLayout(2, 1));
		credentialsPanel.setPreferredSize(new Dimension(width/2, height/2));
		JTextField usernameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
		passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
		credentialsPanel.add(usernameField);
		credentialsPanel.add(passwordField);
		add(credentialsPanel);
		
		JPanel actionPanel = new JPanel(new GridLayout(1,3));
		actionPanel.setPreferredSize(new Dimension(width/2, height/2));
		JButton newUserBtn = new JButton("New user");
		JButton loginBtn = new JButton("Login");
		JButton superUserLoginBtn = new JButton("Super User login");
		
		actionPanel.add(newUserBtn);
		actionPanel.add(loginBtn);
		actionPanel.add(superUserLoginBtn);
		add(actionPanel);
		
		loginBtn.addActionListener(e -> {
            if(!this.controller.verifyUser(usernameField.getText(), passwordField.getText())) {
                JOptionPane.showMessageDialog(null,
                        new JLabel("Login failed, wrong username or password")
                        );
            }
		});
		
		superUserLoginBtn.addActionListener(e -> {
			if(usernameField.getText().equals("SuperUser") && passwordField.getText().equals("brandbil123")) {
				 JOptionPane.showMessageDialog(null,
	                        new SuperUserView(controller, width, height)
	                        );
			}else {
				JOptionPane.showMessageDialog(null, new JLabel("Access denied"));
			}
		});
		
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
                                //If possible, add code here that automatically open the user creation dialog again
                                );
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
	
	public void toggleVisible() {
		if(isVisible()) setVisible(false);
		else setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
//		toggleVisible();
	}
}