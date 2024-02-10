package inputData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * @author simon hamner
 * @info This class is used for converting CSV files to suitable objects
 * @version 2023-10-16
 */

public class CSVData{

	private ArrayList<HashMap<String, String>> data;
	private String[] keys = {"Date","Time","ElapsedTime","Longitude","Latitude",
			"Altitude","Distance","HeartRate","Speed","Cadence"};
	
	public CSVData(String filePath) {
		//read file and grab information
		data = new ArrayList<HashMap<String, String>>();
		File fileObject = new File(filePath);
		Scanner fileScan = null;
		
		try {
			fileScan = new Scanner(fileObject);
		} catch (NullPointerException | IOException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		
		try {
		
			fileScan.nextLine();
			
			while(fileScan.hasNextLine()) {
				int col = 0;
				
				String row = fileScan.nextLine();
				
				Scanner scanRow = new Scanner(row);
		
				scanRow.useDelimiter(";");
				
				HashMap<String, String> rowData = new HashMap<String, String>();
				
				while(scanRow.hasNext()) {
					String s = scanRow.next().replace(",", ".");
					rowData.put(keys[col++], s);
				}
				data.add(rowData);
				scanRow.close();
			}
		
		} catch(NullPointerException e) {
			System.err.print(e);
			e.getStackTrace();
		}
		
		fileScan.close();
	}
	public ArrayList<HashMap<String,String>> getData() {
		return data;
	}
	
	@Override
	public String toString() {
		String s = "";
		int col = 0;
		for(int i = 0; i < data.size(); i++) {
			if(col == 10) {
				col = 0;
				s += "\n";
			}
			while(col < 10) s += data.get(i).get(keys[col++]) + " | ";
			s += "row: " + (i+1);
		}
		return s;
	}
}
