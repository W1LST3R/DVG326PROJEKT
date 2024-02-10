package app;

import javax.swing.SwingUtilities;

import controller.Controller;
import mainModel.MainModel;
import view.MainFrame;

public class AppPrototype {

	public static void main(String[] args) {
		MainModel mainModel = new MainModel();
		Controller controller = new Controller(mainModel);
		SwingUtilities.invokeLater(() -> new MainFrame(controller));
	}

}
