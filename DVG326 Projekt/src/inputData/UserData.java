package inputData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UserData{
	
	private String username;
	private String password;
	private String filename;
	
	//Constructor for generating UserData objects from save file
	public UserData(String username) {
		
		this.username = username;
		setFilename();
		this.password = getValueByKey("password");
		
	
	}
	
	private HashMap<String, String> userInfo;
	
	public UserData(HashMap<String, String> userInfo) {
		
		setUsername(userInfo.get("username"));
		setPassword(userInfo.get("password"));
		this.userInfo = userInfo;
		
		setFilename();
		createUserDataDir();
		createUserDataFile();
	
	}
	
	public void createUserDataDir() {
		
		File path = new File("./userData/");
		
		//For the first time a user is created, create a directory inside userData with the name of the user
		
		File dir = new File(path.getAbsolutePath() + "/" + getUsername());
		
		if(dir.mkdir()) System.out.println("UserData: dir created successfully for '" + getUsername() + "'");
		else System.out.println("UserData: dir for '" + getUsername() + "' already exist");
		
//		System.out.println(dir.getAbsolutePath());
	}
	
	public void createUserDataFile() {
		
		File file = getPath();
		
		try {
			if(file.createNewFile()) {
				System.out.println("UserData: file creation succeded for '" + getUsername() + "'");
				//When file is created for the first time, write user info to that file
				initialWriteToSaveFile();
			}
			else System.out.println("UserData: '" + getFilename() + "' already exist");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//These variables are used to write to save file
	private String saveFileContent;
	private int index;
	
	public void initialWriteToSaveFile() {

		saveFileContent = "";
		index = 0;
			
		userInfo.forEach((key, value) -> {
			if(index++ == userInfo.size()-1) saveFileContent += key + ":" + value;
			else saveFileContent += key + ":" + value + ";";
			});
		
		writeToSaveFile(saveFileContent);
	}
	
	public void writeToSaveFile(String s) {
		
		FileWriter fw = null;

		try {
			
			fw = new FileWriter(getPath());

			fw.write(s);
		
			fw.close();
			
			System.out.println("UserData: successfully wrote '" + s + "' to save file");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void overwriteSaveFile(String key, String newData) {
		Scanner scanner = new Scanner(getSaveFileContent());
		scanner.useDelimiter(";");
		
		String newText = "";
		
		while(scanner.hasNext()) {
			String currentCol = scanner.next();
			String currentKey = currentCol.substring(0, currentCol.indexOf(":"));
			String oldData = currentCol.substring(currentCol.indexOf(":")+1);
			if(currentKey.equals(key)) newText = getSaveFileContent().replace(oldData, newData);
		}
		
		scanner.close();
		
		writeToSaveFile(newText);
		
	}
	
	//Setters
	public void setFilename() {
		filename = getUsername() + "_userdata.csv";
	}

	public void setUsername(String username) {
		this.username = username;
		//add code that writes to save file
		//if(getPath().exists()) overwriteSaveFile("username", username);
	}
	
	public void setPassword(String password) {
		this.password = password;
		//add code that writes to save file
		//if(getPath().exists()) overwriteSaveFile("password", password);
	}
	
	//Getters
	public String getValueByKey(String key) {
		Scanner scanner = new Scanner(getSaveFileContent());
		
		scanner.useDelimiter(";");
		String keyValue = "";
		
		while(scanner.hasNext()) {
			String currentCol = scanner.next();
			String currentKey = currentCol.substring(0, currentCol.indexOf(":"));
			keyValue = currentCol.substring(currentCol.indexOf(":")+1);
			
			if(currentKey.equals(key)){
				scanner.close();
				return keyValue;
			}
			
		}
		
		scanner.close();
		
		return null;
	}
	 
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFilename() {
		return filename;
	}
		
	public File getPath() {
		return new File("./userData/" + getUsername() + "/" + getFilename());
	}
	
	public String getSaveFileContent() {
		//Gets everything written in the save file
		String saveFileContent = "";
		try {
			Scanner scanner = new Scanner(getPath());
			scanner.useDelimiter("");
			saveFileContent = "";
			while(scanner.hasNext()) saveFileContent += scanner.next();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return saveFileContent;
	}
	
	public HashMap<String, String> getUserInfo() {
		return generateUserInfo();
	}
	
	public HashMap<String, String> generateUserInfo() {
		HashMap<String, String> userInfo = new HashMap<>();
		
		String saveFileContent = getSaveFileContent();
		
		Scanner scanner = new Scanner(saveFileContent);
		
		scanner.useDelimiter(";");
		
		while(scanner.hasNext()) {
			String currentCol = scanner.next();
			
			String key = currentCol.substring(0, currentCol.indexOf(":"));
			String value = currentCol.substring(currentCol.indexOf(":")+1);
			
			userInfo.put(key, value);
		}
		
		scanner.close();
		
		//this line is not really needed
		this.userInfo = userInfo;
		
		return userInfo;
 	}
}
