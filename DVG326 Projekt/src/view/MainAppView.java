package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.Controller;
import controller.Stats;

@SuppressWarnings({ "serial", "deprecation" })
public class MainAppView extends JPanel implements Observer {
	
	private Controller controller;
	private LoginView loginView;
	private int width;
	private int height;
	
	private JPanel actFolders;
	private JPanel actData;
	private UserPanel userPanel;
	private boolean hasActiveUser;
	
	//Possibly add "LoginView" as parameter and add method which makes sure only LoginView or MainAppView is visible at certain times
	public MainAppView(Controller controller, int width, int height, LoginView loginView) {
		
		this.setVisible(false);
		
		this.controller = controller;
		this.loginView = loginView;
		this.width = width;
		this.height = height;
		
		controller.registerToUserManager(this);
		
		setSize(new Dimension(this.width, this.height));
//		setBorder(BorderFactory.createLineBorder(Color.yellow));
		
		JPanel banner = new JPanel(new GridLayout(1, 0));
		banner.setPreferredSize(new Dimension(width, height/4));
		banner.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel body = new JPanel(new GridLayout(1, 2));
		body.setPreferredSize(new Dimension(width, height*3/4));
		body.setBorder(BorderFactory.createLineBorder(Color.black));
		
		actFolders = new JPanel(new GridLayout(0, 2));
		actFolders.setPreferredSize(new Dimension((int)Math.round(width*0.2), body.getHeight()));
		actFolders.setSize(new Dimension((int)Math.round(width*0.2), body.getHeight()));
		actFolders.setBorder(BorderFactory.createLineBorder(Color.green));
		
		actData = new JPanel();
		actData.setPreferredSize(new Dimension((int)Math.round(width*0.8), height*3/4));
		actData.setSize(new Dimension((int)Math.round(width*0.8), height*3/4));
		actData.setBorder(BorderFactory.createLineBorder(Color.red));
		
//		System.out.println((int)Math.round(width*0.8) + " " + height*3/4 + " " + actData.getWidth() + " " + actData.getHeight());
		userPanel = new UserPanel(controller, width, height);
		banner.add(userPanel);
		
		//button for synchronizing gizmo2020
		JButton syncActBtn = new JButton("Synchronize Gizmo2020");
		syncActBtn.addActionListener(e -> {
			System.out.println("MainAppView: synchronizeActivites() started");
			Timer timer = new Timer(0, k -> displayFolders());
			timer.setRepeats(false);
			timer.start();
			});
		banner.add(syncActBtn);
		
		body.add(actFolders);
		body.add(actData);
		
		add(banner);
		add(body);
		
		hasActiveUser = false;
	}

	public void toggleVisible() {
		if(!loginView.isVisible()) {
			this.setVisible(false);
			loginView.setVisible(true);
		}
		else {
			this.setVisible(true);
			loginView.setVisible(false);
			hasActiveUser = true;
		}
	}
	
	public void removeActOrUpdateActDisplay() {
		if(loginView.isVisible()) {
			actFolders.removeAll();
			actData.removeAll();
		} else updateActDisplay();
	}
	
	public void updateActDisplay() {
		if(controller.userHasActivities()) displayFolders();
	}
	
	public String getUser() {
		return controller.getActiveUser();
	}
	
	public ArrayList<ArrayList<String>> synchronizeActivities() {
		return controller.synchronizeActivities();
	}
	
	public void displayFolders() {
		ArrayList<ArrayList<String>> arr = synchronizeActivities();
		
		System.out.println("MainAppView: synchronizeActivities() completed");
		
		actFolders.removeAll();
		//Only add components if there is none displayed
		if(actFolders.getComponentCount() == 0) {
			
			for(int i = 0; i < arr.size(); i++ ) {
				
				ArrayList<String> currentFolder = arr.get(i);
				JComboBox<Object> jcb = new JComboBox<Object>();
				jcb.setVisible(false);
				
				String[] content = new String[currentFolder.size()-1];
				
				for(int k = 0; k < currentFolder.size(); k++) {
					
					if(k == 0) {
						JButton jb = new JButton(currentFolder.get(0));
						
						jb.addActionListener(e -> {
							
							//displayActivity(jb.getText(), jcb.getSelectedItem().toString());
							
							if(jcb.isVisible()) jcb.setVisible(false);
							else jcb.setVisible(true);
						});
						jcb.addActionListener(e -> {
							
							if(jcb.isVisible()) displayActivity(jb.getText(), jcb.getSelectedItem().toString());
							});
						actFolders.add(jb);
					}
					else {
						content[k-1] = currentFolder.get(k);
						
					}
				}
				for(String s : content) jcb.addItem(s);
				
				actFolders.add(jcb);
			}
		}
		revalidate();
		repaint();
	}
	
	public void displayActivity(String folder, String activity) {
        actData.removeAll();
        actData.revalidate();
        actData.repaint();
        actData.add(new ActivityView(new Stats(controller,getUser(),folder,activity),activity,actData.getWidth(),actData.getHeight(),controller.getMapImage(getUser(),folder)));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if(getUser() == null) hasActiveUser = false;
		if(!hasActiveUser)toggleVisible();

		removeActOrUpdateActDisplay();
		
	}
	
}