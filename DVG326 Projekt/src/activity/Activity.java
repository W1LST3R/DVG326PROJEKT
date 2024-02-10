package activity;

import java.util.ArrayList;
import java.util.HashMap;

import inputData.CSVData;

public class Activity implements ActivityType {
	
	private String activityName;
	private CSVData csvData;
	private ActivityCalculator activityCalculator;
	
	public Activity(String activityName) {
		setActivityName(activityName.substring(activityName.lastIndexOf("\\")+1));
		csvData = new CSVData(activityName);
	}
	
	public Activity(String activityName, String filePath) {
		setActivityName(activityName);
		csvData = new CSVData(filePath);
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String getActivityName() {
		return activityName;
	}
	public ArrayList<HashMap<String,String>> getActivityData() {
		return csvData.getData();
	}
	public String csvToString() {
		return csvData.toString();
	}
	
	public HashMap<String, HashMap<String, String>> getRelevantData(){
        activityCalculator = new ActivityCalculator(getActivityData());
        return activityCalculator.getRelevantData();
    }

    public HashMap<String, ArrayList<String>> getArrayData(){
        activityCalculator = new ActivityCalculator(getActivityData());
        return activityCalculator.getArrayData();
    }
}