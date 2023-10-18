package controller;

import java.util.ArrayList;

import mainModel.MainModel;

public class Controller {
	
	private MainModel model;
	
	public Controller(MainModel model) {
		this.model = model;
	}
	
	public ArrayList<ArrayList<String>> synchronizeActivities() {
		return model.synchronizeActivities("ExampleUser");
	}
}
