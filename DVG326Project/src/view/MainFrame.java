package view;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Controller;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private Controller controller;
	
	public MainFrame(Controller controller) {
		this.controller = controller;
		
		//Set default window options
		System.out.println("MainFrame called");
		setSize(1200,1200);		
		setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//For testing
		ArrayList<ArrayList<String>> arr = controller.synchronizeActivities();
		JPanel activityFolders = new JPanel();
		for(int i = 0; i < arr.size(); i++ ) {
			ArrayList<String> currentFolder = arr.get(i);
			JComboBox<Object> jcb;
			String[] content = new String[currentFolder.size()-1];
			for(int k = 0; k < currentFolder.size(); k++) {
				if(k == 0) activityFolders.add(new JButton(currentFolder.get(0)));
				else content[k-1] = currentFolder.get(k); 
			}
			jcb = new JComboBox<Object>(content);
			activityFolders.add(jcb);
		}
		
		add(activityFolders);
	}
}
