package activity;

import inputData.CSVData;

public class Activity implements ActivityType {
	
	private String activityName;
	private CSVData csvData;
	
	public Activity(String activityName) {
		this.activityName = activityName.substring(activityName.lastIndexOf("\\")+1);
		csvData = new CSVData(activityName);
	}
	
	public Activity(String activityName, String filePath) {
		this.activityName = activityName;
		csvData = new CSVData(filePath);
	}
	
	public String getActivityName() {
		return activityName;
	}
	
	public String csvToString() {
		return csvData.toString();
	}
}
