package activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * @author William Pettersson
 * @info operations for refineing of data
 * @version 2023-10-27
 *
 */

public class ActivityCalculator {
	private ArrayList<HashMap<String,String>> activityData;
	
	public ActivityCalculator(ArrayList<HashMap<String,String>> data) {
		activityData = data;
	}
	public String startValue(String column){
		return activityData.get(0).get(column);
	}
	
	public String startValue(ArrayList<String> list){
		return list.get(0);
	}
	
	public String endValue(String column){
		return activityData.get(activityData.size()-1).get(column);
	}
	
	public String endValue(ArrayList<String> list){
		return list.get(list.size()-1);
	}
	
	public String maxValue(String column) {
		double maxValue = Double.parseDouble(activityData.get(0).get(column));
		for(int i = 0; i < activityData.size(); i++) {
			if(maxValue < Double.parseDouble(activityData.get(i).get(column))) {
				maxValue = Double.parseDouble(activityData.get(i).get(column));
			}
		}
		return Double.toString(maxValue);
	}
	
	public String maxValue(ArrayList<String> list) {
		double maxValue = Double.parseDouble(list.get(0));
		for(int i = 0; i < list.size(); i++) {
			if(maxValue < Double.parseDouble(list.get(i))) {
				maxValue = Double.parseDouble(list.get(i));
			}
		}
		return Double.toString(maxValue);
		
	}
	
	public String minValue(String column) {
		double minValue = Double.parseDouble(activityData.get(0).get(column));
		for(int i = 0; i < activityData.size(); i++) {
			if(minValue > Double.parseDouble(activityData.get(i).get(column))) {
				minValue = Double.parseDouble(activityData.get(i).get(column));
			}
		}
		return Double.toString(minValue);
	}
	
	public String minValue(ArrayList<String> list) {
		double minValue = Double.parseDouble(list.get(0));
		for(int i = 0; i < list.size(); i++) {
			if(minValue > Double.parseDouble(list.get(i))) {
				minValue = Double.parseDouble(list.get(i));
			}
		}
		return Double.toString(minValue);
	}
	
	public String meanValue(String column) {
		double meanValue = 0;
		for(int i = 0; i < activityData.size(); i++) {
			meanValue += Double.parseDouble(activityData.get(i).get(column));
		}
		return Double.toString(meanValue/activityData.size());
	}
	
	public String meanValue(ArrayList<String> list) {
		double meanValue = 0;
		for(int i = 0; i < list.size(); i++) {
			meanValue += Double.parseDouble(list.get(i));
		}
		return Double.toString(meanValue/list.size());
	}
	
	public String distanceUp() {
		double distanceUp = 0;
		for (int i = 1; i < activityData.size(); i++ ) {
			if(Double.parseDouble(activityData.get(i-1).get("Altitude"))<Double.parseDouble(activityData.get(i).get("Altitude"))) {
				distanceUp += Double.parseDouble(activityData.get(i).get("Distance"))-Double.parseDouble(activityData.get(i-1).get("Distance"));
			}
		}
		return Double.toString(distanceUp);
	}
	
	public String distanceDown() {
		double distanceDown = 0;
		for (int i = 1; i < activityData.size(); i++ ) {
			if(Double.parseDouble(activityData.get(i-1).get("Altitude"))>Double.parseDouble(activityData.get(i).get("Altitude"))) {
				distanceDown += Double.parseDouble(activityData.get(i).get("Distance"))-Double.parseDouble(activityData.get(i-1).get("Distance"));
			}
		}
		return Double.toString(distanceDown);
	}
	
	public String meanSpeedVerticalUp() {
		ArrayList<String> speedUp = new ArrayList<String>();
		for (int i = 1; i < activityData.size(); i++ ) {
			if(Double.parseDouble(activityData.get(i-1).get("Altitude"))<Double.parseDouble(activityData.get(i).get("Altitude"))) {
				speedUp.add(activityData.get(i).get("Speed"));
			}
		}
		return meanValue(speedUp);
	}
	
	public String meanSpeedVerticalDown() {
		ArrayList<String> speedDown = new ArrayList<String>();
		for (int i = 1; i < activityData.size(); i++ ) {
			if(Double.parseDouble(activityData.get(i-1).get("Altitude"))>Double.parseDouble(activityData.get(i).get("Altitude"))) {
				
				speedDown.add(activityData.get(i).get("Speed"));
			}
		}
		return meanValue(speedDown);
	}
	
	public String highestInclination() {
		double upX1 = 0; 
		double upX2 = 0; 
		double upY1 = 0; 
		double upY2 = 0; 
		double downoX1 = 0; 
		double downoX2 = 0; 
		double downoY1 = 0; 
		double downoY2 = 0;
		double upTemp = 0;
		double downTemp = Double.parseDouble(activityData.get(activityData.size()-1).get("Altitude"));
		for (int i = 1; i < activityData.size(); i++ ) {
			if(Double.parseDouble(activityData.get(i-1).get("Altitude"))<Double.parseDouble(activityData.get(i).get("Altitude"))) {
				if(upTemp < Double.parseDouble(activityData.get(i).get("Altitude"))-Double.parseDouble(activityData.get(i-1).get("Altitude"))) {
					upTemp = Double.parseDouble(activityData.get(i).get("Altitude"))-Double.parseDouble(activityData.get(i-1).get("Altitude"));
					upY1 = Double.parseDouble(activityData.get(i-1).get("Altitude"));
					upY2 = Double.parseDouble(activityData.get(i).get("Altitude"));
					upX1 = Double.parseDouble(activityData.get(i-1).get("Distance"));
					upX2 = Double.parseDouble(activityData.get(i).get("Distance"));
				}
			}else if(Double.parseDouble(activityData.get(i-1).get("Altitude"))>Double.parseDouble(activityData.get(i).get("Altitude"))) {
				if(downTemp > Double.parseDouble(activityData.get(i).get("Altitude"))-Double.parseDouble(activityData.get(i-1).get("Altitude"))) {
					downTemp = Double.parseDouble(activityData.get(i).get("Altitude"))-Double.parseDouble(activityData.get(i-1).get("Altitude"));
					downoY1 = Double.parseDouble(activityData.get(i-1).get("Altitude"));
					downoY2 = Double.parseDouble(activityData.get(i).get("Altitude"));
					downoX1 = Double.parseDouble(activityData.get(i-1).get("Distance"));
					downoX2 = Double.parseDouble(activityData.get(i).get("Distance"));
				}
			}
		}
		double upSideA = upY2-upY1;
		double upSideB = upX2 - upX1;
		double upSideCHypotenuse = Math.pow(upSideA, 2)+ Math.pow(upSideB, 2);
		double upSideC = Math.sqrt(upSideCHypotenuse);
		double upSin = Math.sin(upSideA/upSideC);
		double upDegrees = Math.toDegrees(upSin);
		
		double downSideA = downoY2-downoY1;
		double downSideB = downoX2 - downoX1;
		double downSideCHypotenuse = Math.pow(downSideA, 2)+ Math.pow(downSideB, 2);
		double downSideC = Math.sqrt(downSideCHypotenuse);
		double downSin = Math.sin(downSideA/downSideC);
		double downDegrees =  Math.toDegrees(downSin);
		
		if(upDegrees> downDegrees*-1) {
			return "The highest iclanation is "+String.format("%.2f", upDegrees)+" degrees upwards";
		}else {
			return "The highest iclanation is "+String.format("%.2f", downDegrees*-1)+" degrees downwards";
		}
	}
	
	public String lowestInclination() {
		double upX1 = 0; 
		double upX2 = 0; 
		double upY1 = 0; 
		double upY2 = 0; 
		double downoX1 = 0; 
		double downoX2 = 0; 
		double downoY1 = 0; 
		double downoY2 = 0;
		double upTemp = 0;
		double downTemp = Double.parseDouble(activityData.get(activityData.size()-1).get("Altitude"));
		for (int i = 1; i < activityData.size(); i++ ) {
			if(Double.parseDouble(activityData.get(i-1).get("Altitude"))<Double.parseDouble(activityData.get(i).get("Altitude"))) {
				if(upTemp > Double.parseDouble(activityData.get(i).get("Altitude"))-Double.parseDouble(activityData.get(i-1).get("Altitude"))) {
					upTemp = Double.parseDouble(activityData.get(i).get("Altitude"))-Double.parseDouble(activityData.get(i-1).get("Altitude"));
					upY1 = Double.parseDouble(activityData.get(i-1).get("Altitude"));
					upY2 = Double.parseDouble(activityData.get(i).get("Altitude"));
					upX1 = Double.parseDouble(activityData.get(i-1).get("Distance"));
					upX2 = Double.parseDouble(activityData.get(i).get("Distance"));
				}
			}else if(Double.parseDouble(activityData.get(i-1).get("Altitude"))>Double.parseDouble(activityData.get(i).get("Altitude"))) {
				if(downTemp < Double.parseDouble(activityData.get(i).get("Altitude"))-Double.parseDouble(activityData.get(i-1).get("Altitude"))) {
					downTemp = Double.parseDouble(activityData.get(i).get("Altitude"))-Double.parseDouble(activityData.get(i-1).get("Altitude"));
					downoY1 = Double.parseDouble(activityData.get(i-1).get("Altitude"));
					downoY2 = Double.parseDouble(activityData.get(i).get("Altitude"));
					downoX1 = Double.parseDouble(activityData.get(i-1).get("Distance"));
					downoX2 = Double.parseDouble(activityData.get(i).get("Distance"));
				}
			}else{
				return "The lowest iclanation is 0.00 degrees";
			}
		}
			double upSideA = upY2-upY1;
			double upSideB = upX2-upX1;
			double upSideCHypotenuse = Math.pow(upSideA, 2)+ Math.pow(upSideB, 2);
			double upSideC = Math.sqrt(upSideCHypotenuse);
			double upSin = Math.sin(upSideA/upSideC);
			double upDegrees = Math.toDegrees(upSin);
			
			double downSideA = downoY2-downoY1;
			double downSideB = downoX2-downoX1;
			double downSideCHypotenuse = Math.pow(downSideA, 2)+ Math.pow(downSideB, 2);
			double downSideC = Math.sqrt(downSideCHypotenuse);
			double downSin = Math.sin(downSideA/downSideC);
			double downDegrees =  Math.toDegrees(downSin);
			
			if(upDegrees> downDegrees*-1) {
				return "The lowest iclanation is "+String.format("%.2f", upDegrees)+" degrees upwards";
			}else {
				return "The lowest iclanation is "+String.format("%.2f", downDegrees*-1)+" degrees downwards";
			}
	}
	
	public String totalTimeForActivity() {
		int time = Integer.parseInt(endValue("ElapsedTime"));
		int seconds = time%60;
		int minutes = (time%3600)/60;
		int hours = time/3600;
		return hours+":"+minutes+":"+seconds;
	}
	
	//while loop för att jämföra 1 km och total distance

	public ArrayList<String> tempoArray(){
		int distance = 0;
		ArrayList<String> tempo = new ArrayList<String>();
		//to know where the new km started/ which index
		int t = 0;
		int time = 0;
		int i = 1;
		for(int j = 1; j < activityData.size(); j++) {
			distance += Double.parseDouble(activityData.get(j).get("Distance"))-Double.parseDouble(activityData.get(j-1).get("Distance"));
			if(i == (int)distance/1000) {
				time = Integer.parseInt(activityData.get(j).get("ElapsedTime"))-Integer.parseInt(activityData.get(t).get("ElapsedTime"));
				tempo.add(Integer.toString(time));
				i++;
				t = j;
			}
		}
		return tempo;
	}
	
	public ArrayList<String> perKm(String column){
		int distance = 0;
		ArrayList<String> perKm = new ArrayList<String>();
		//to know where the new km started/ which index
		double oneKm = 0;
		int i = 1;
		int t = 0;
		for(int j = 1; j < activityData.size(); j++) {
			distance += Double.parseDouble(activityData.get(j).get("Distance"))-Double.parseDouble(activityData.get(j-1).get("Distance"));
			oneKm += Double.parseDouble(activityData.get(j-1).get(column));
			t++;
			if(i == (int)distance/1000) {
				perKm.add(Double.toString(oneKm/t));
				oneKm = 0;
				t=0;
				i++;
			}
		}
		return perKm;
	}
	
	
	public ArrayList<String> tempo(){
		double distance = 0;
		ArrayList<String> tempo = new ArrayList<String>();
		//to know where the new km started/ which index
		int time = 0;
		tempo.add(Integer.toString(0));
		for(int j = 1; j < activityData.size(); j++) {
			distance = Double.parseDouble(activityData.get(j).get("Distance"))-Double.parseDouble(activityData.get(j-1).get("Distance"));
			time = Integer.parseInt(activityData.get(j).get("ElapsedTime"))-Integer.parseInt(activityData.get(j-1).get("ElapsedTime"));
			tempo.add(Double.toString(distance/time));
			tempo.size();
		}
		return tempo;
	}
	public String fastestKm() {
		int time = 0;
		int fastestKm = 0;
		for(int i = 0; i < tempoArray().size(); i++) {
			if (time > Integer.parseInt(tempoArray().get(i))||time == 0){
				time = Integer.parseInt(tempoArray().get(i));
				fastestKm = i+1;
			}
		}
		int minutes = (time%3600)/ 60;
		int seconds = time%60;
		return "Fastest kilometer was "+fastestKm+" whith a time of " +minutes+":"+seconds+" minutes";
	}
	
	public String meanTempo() {
		//to know where the new km started/ which index
		int time = 0;
		for(int i = 0; i < tempoArray().size(); i++) {
			time += Integer.parseInt(tempoArray().get(i));
		}
		time = time/tempoArray().size();
		int minutes = (time%3600)/ 60;
		int seconds = time%60;
		return "Mean tempo for activity was whith a time of " +minutes+":"+seconds+" minutes";
	}
	public String slowestKm() {
		int time = 0;
		int slowestKm = 0;
		for(int i = 0; i < tempoArray().size(); i++) {
			if (time < Integer.parseInt(tempoArray().get(i))||time == 0){
				time = Integer.parseInt(tempoArray().get(i));
				slowestKm = i+1;
			}
		}
		int minutes = (time%3600)/ 60;
		int seconds = time%60;
		return "Slowest kilometer was "+slowestKm+" whith a time of " +minutes+":"+seconds+" minutes";
	}
	
	public ArrayList<String> uniqueValues(String column){
		ArrayList<String> uniqueValues = new ArrayList<String>();
		HashSet<String> uniqueSet = new HashSet<>();
		for(int i = 0; i < activityData.size();i++) {
			String valueToCheck = activityData.get(i).get(column) ;
			if (!uniqueSet.contains(valueToCheck)) {
	            uniqueSet.add(valueToCheck);
	            uniqueValues.add(valueToCheck);
	        }
			/*for(int j = 0; j <  activityData.size();j++ ) {
				if(valueToCheck.equals(activityData.get(j).get(column))&&i!=j) continue;
				if(j== activityData.size()-1&&!uniqueValues.contains(valueToCheck)) uniqueValues.add(valueToCheck);
			}*/
		}
		Collections.sort(uniqueValues);
		return uniqueValues;
	}
	
	public ArrayList<String> timeInZone(String column){
		ArrayList<String> uniqueValues = uniqueValues(column);
		ArrayList<String> timeInZone = new ArrayList<String>();
		for(int i = 0; i < uniqueValues.size();i++) {
			double time = 0;
			double valueToCheck = Double.parseDouble(uniqueValues.get(i));
			for(int j = 1; j <  activityData.size();j++ ) {
				if(valueToCheck == Double.parseDouble(activityData.get(j-1).get("HeartRate"))) {
				time += Double.parseDouble(activityData.get(j).get("ElapsedTime"))-Integer.parseInt(activityData.get(j-1).get("ElapsedTime"));
				}
			}
			timeInZone.add(Double.toString(time));
		}
		return timeInZone;
	}
	
	public String highestTimeInZone(){
		ArrayList<String> timeInZone = timeInZone("HeartRate");
		String value = maxValue(timeInZone);
		int index = 0;
		for(int i = 0; i < timeInZone.size();i++) {
			if(value.equals(timeInZone.get(i))) {
				index = i;
			}
		}
		return "Longest time in one zone was "+timeInZone.get(index) +"S where the heart rate was "+uniqueValues("HeartRate").get(index)+" BPM";
	}
	
	public String lowestTimeInZone(){
		ArrayList<String> timeInZone = timeInZone("HeartRate");
		String value = minValue(timeInZone);
		int index = 0;
		for(int i = 0; i < timeInZone.size();i++) {
			if(value.equals(timeInZone.get(i))) {
				index = i;
			}
		}
		return "Least time in one zone was "+timeInZone.get(index) +"S where the heart rate was "+uniqueValues("HeartRate").get(index)+" BPM";
	}
	
	public HashMap<String, ArrayList<String>> getArrayData(){
		String [] primaryKeys = {"Date","Time","ElapsedTime","Longitude","Latitude","Altitude",
				"Distance","HeartRate","Speed","Cadence","Tempo"};
		HashMap<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
		for(int i = 0; i < primaryKeys.length-1; i++) {
			ArrayList<String> dataArray = new ArrayList<>();
			for(int j = 0; j < activityData.size();j++) {
				dataArray.add(activityData.get(j).get(primaryKeys[i]));
			}
			data.put(primaryKeys[i], dataArray);
		}
		data.put("Tempo", tempo());
		data.put("HeartZone", uniqueValues("HeartRate"));
		data.put("TimeInZone", timeInZone("HeartRate"));
		return data;
	}
	
	public HashMap<String, HashMap<String, String>> getRelevantData(){
		String [] primaryKeys = {"Date","Time","ElapsedTime","Longitude","Latitude",
				"Altitude","Distance","HeartRate","Speed","Cadence",
				"Tempo","Unique","HeartZone","TimeInZone"};
		//,"HeartZone","TimeInZone"
		
		String [] seccondaryKeys = {"startValue","endValue","maxValue","minValue",
				"meanValue","meanTempo","totalTimeForActivity","KmMaxValue","KmMinValue","KmMeanValue","distanceUp",
				"distanceDown","meanSpeedVerticalUp","meanSpeedVerticalDown",
				"fastestKm","slowestKm","highestInclination","lowestInclination", "highestTimeInZone","lowestTimeInZone"};
		
		HashMap<String, HashMap<String, String>> temp = new HashMap<String, HashMap<String, String>>();
		
		for(int i = 0; i < primaryKeys.length; i++) {
				int x = 0;
				HashMap<String, String> data = new HashMap<String, String>();
				if(primaryKeys[i] == "Tempo"){
					ArrayList<String> tempo = tempo();
					data.put(seccondaryKeys[x++], startValue(tempo));
					data.put(seccondaryKeys[x++], endValue(tempo));
					data.put(seccondaryKeys[x++], maxValue(tempo));
					data.put(seccondaryKeys[x++], minValue(tempo));
					data.put(seccondaryKeys[x++], "0");
					data.put(seccondaryKeys[x++], meanTempo());
				}
				else if(primaryKeys[i] == "Unique") {
					data.put(seccondaryKeys[x++], "0");
					data.put(seccondaryKeys[x++], "0");
					data.put(seccondaryKeys[x++], "0");
					data.put(seccondaryKeys[x++], "0");
					data.put(seccondaryKeys[x++], "0");
					data.put(seccondaryKeys[x++], "0");
					data.put(seccondaryKeys[x++], "0");
					data.put(seccondaryKeys[x++],"0");
					data.put(seccondaryKeys[x++],"0");
					data.put(seccondaryKeys[x++],"0");
					data.put(seccondaryKeys[x++],distanceUp());
					data.put(seccondaryKeys[x++],distanceDown());
					data.put(seccondaryKeys[x++],meanSpeedVerticalUp());
					data.put(seccondaryKeys[x++],meanSpeedVerticalDown());
					data.put(seccondaryKeys[x++],fastestKm());
					data.put(seccondaryKeys[x++],slowestKm());
					data.put(seccondaryKeys[x++],highestInclination());
					data.put(seccondaryKeys[x++],lowestInclination());
				}else if(primaryKeys[i] == "HeartZone" || primaryKeys[i] == "TimeInZone") {
					data.put(seccondaryKeys[x++], startValue(getArrayData().get(primaryKeys[i])));
					data.put(seccondaryKeys[x++], endValue(getArrayData().get(primaryKeys[i])));
					data.put(seccondaryKeys[x++], maxValue(getArrayData().get(primaryKeys[i])));
					data.put(seccondaryKeys[x++], minValue(getArrayData().get(primaryKeys[i])));
					data.put(seccondaryKeys[x++], meanValue(getArrayData().get(primaryKeys[i])));
					if(primaryKeys[i] == "TimeInZone") {
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++], "0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++], highestTimeInZone());
						data.put(seccondaryKeys[x++], lowestTimeInZone());
					}
				}else {
					data.put(seccondaryKeys[x++], startValue(primaryKeys[i]));
					data.put(seccondaryKeys[x++], endValue(primaryKeys[i]));
					if(primaryKeys[i] == "Date" || primaryKeys[i] == "Time"){
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						data.put(seccondaryKeys[x++],"0");
						
					}else{
						data.put(seccondaryKeys[x++], maxValue(primaryKeys[i]));
						data.put(seccondaryKeys[x++], minValue(primaryKeys[i]));
						data.put(seccondaryKeys[x++], meanValue(primaryKeys[i]));
						if(primaryKeys[i] == "HeartRate"||primaryKeys[i] == "Speed"||primaryKeys[i] == "Cadence"){
							data.put(seccondaryKeys[x++],"0");
							data.put(seccondaryKeys[x++], "0");
							data.put(seccondaryKeys[x++], maxValue(perKm(primaryKeys[i])));
							data.put(seccondaryKeys[x++], minValue(perKm(primaryKeys[i])));
							data.put(seccondaryKeys[x++], meanValue(perKm(primaryKeys[i])));
						}
						if(primaryKeys[i] == "ElapsedTime") {
							data.put(seccondaryKeys[x++], "0");
							data.put(seccondaryKeys[x++],totalTimeForActivity());
						}
					}
				}
				temp.put(primaryKeys[i], data);
		}
		
		
		return temp;
	}
}