package activity;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ActivityFolderTest {
	
	private ArrayList<ActivityFolder> activityFolders;
	private ArrayList<String> fileNames = new ArrayList<String>();

	@Test
	void test() {
		
		activityFolders = new ArrayList<ActivityFolder>();
		
		File dir = new File("./csv");
		//System.out.println(dir.getAbsolutePath());
		showFiles(dir.listFiles());
//		showAct();
		
//		System.out.println(activityFolders.get(0).getActivities().get(fileNames.get(0)).csvToString());

	}
	
	public void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
//                System.out.println("Directory: " + file.getAbsolutePath());
            	if(!file.getName().equals(".DS_Store")) {
//            		System.out.println("<--->"+file.getName());
            		activityFolders.add(new ActivityFolder(file.getName()));
            	}
                showFiles(file.listFiles()); // Calls same method again.
            } else {
//                System.out.println("File: " + file.getAbsolutePath());
            	if(!file.getName().equals(".DS_Store")) {
//            		System.out.println(file.getName());
            		fileNames.add(file.getAbsolutePath());
            		activityFolders.get(activityFolders.size()-1).addActivity(file.getAbsolutePath());
            	}
            }
        }
	}
	
	public void showAct() {
//		System.out.println(fileNames);
		int x = 0;
		for(int i = 0; i < activityFolders.size(); i++) {
			System.out.println("\n" + activityFolders.get(i).getFolderName());
			for(int k = 0; k < activityFolders.get(i).getActivities().size(); k++) {
//				System.out.println( fileNames.get(x++) );
//				System.out.println( activityFolders.get(i).getActivities().get(fileNames.get(x++)).getActivityName() );
			}
		}
	}

}
