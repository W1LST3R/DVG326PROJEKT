package controller;


import java.util.ArrayList;
import java.util.HashMap;

public class Stats{
	private HashMap<String, HashMap<String, String>> attributes; 
	private HashMap<String, ArrayList<String>> dataArray; 
	
	public Stats(Controller controller, String user,String folder,String activity) {
		attributes = controller.getActivityData(user, folder,activity);
		dataArray = controller.getArrayData(user, folder, activity);
	}
	
	public String getMaxValue(String column){
		return attributes.get(column).get("maxValue").intern();
	}
	
	public String getMinValue(String column){
		return attributes.get(column).get("minValue").intern();
	}
	
	public String getMeanValue(String column){
		return attributes.get(column).get("meanValue").intern();
	}
	
	public String getEndValue(String column){
		return attributes.get(column).get("endValue").intern();
	}
	
	public String getStartValue(String column){
		return attributes.get(column).get("startValue").intern();
	}
	
	public ArrayList<String> getArray(String column){
		return dataArray.get(column);
	}
	
	public String getTotalTimeForActivity() {
		return attributes.get("ElapsedTime").get("totalTimeForActivity").intern();
	}
	
	public String getDistanceUp() {
		return attributes.get("Unique").get("distanceUp").intern();
	}
	
	public String getDistanceDown() {
		return attributes.get("Unique").get("distanceDown").intern();
	}
	
	public String getMeanSpeedVerticalUp() {
		return attributes.get("Unique").get("meanSpeedVerticalUp").intern();
	}
	
	public String getMeanSpeedVerticalDown() {
		return attributes.get("Unique").get("meanSpeedVerticalDown").intern();
	}
	
	public String getFastestKm() {
		return attributes.get("Unique").get("fastestKm").intern();
	}
	
	public String getSlowestKm() {
		return attributes.get("Unique").get("slowestKm").intern();
	}
	
	public String getKmMaxValue(String column){
		return attributes.get(column).get("KmMaxValue").intern();
	}
	
	public String getKmMinValue(String column){
		return attributes.get(column).get("KmMinValue").intern();
	}
	
	public String getKmMeanValue(String column){
		return attributes.get(column).get("KmMeanValue").intern();
	}
	
	public String getLowestInclination() {
		return attributes.get("Unique").get("lowestInclination").intern();
	}
	
	public String getHighestInclination() {
		return attributes.get("Unique").get("highestInclination").intern();
	}
	
	public String getHighestTimeInZone() {
		return attributes.get("TimeInZone").get("highestTimeInZone").intern();
	}
	
	public String getLowestTimeInZone() {
		return attributes.get("TimeInZone").get("lowestTimeInZone").intern();
	}
	
	public String getMeanTempo() {
		return attributes.get("Tempo").get("meanTempo").intern();
	}
	

}
