package view;

import javax.swing.JFrame;

import controller.Controller;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private Controller controller;
	
	public MainFrame(Controller controller) {
		this.controller = controller;
		
		//Set default window options
		setSize(1400,800);		
		setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		LoginView loginView = new LoginView(this.controller, getWidth(), getHeight());
		MainAppView mainAppView = new MainAppView(this.controller, getWidth(), getHeight(), loginView);
//		SuperUserView superUserView = new SuperUserView(this.controller, getWidth(), getHeight(), loginView);
		
		add(loginView);
		add(mainAppView);
//		add(superUserView);
	}
}