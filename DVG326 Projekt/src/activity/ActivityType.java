package activity;

import java.util.ArrayList;
import java.util.HashMap;

public interface ActivityType {
	public ArrayList<HashMap<String, String>> getActivityData();
	public String getActivityName();
	public String csvToString();
	public HashMap<String, HashMap<String, String>> getRelevantData();
	public HashMap<String, ArrayList<String>> getArrayData();
	public void setActivityName(String activityName);
}
