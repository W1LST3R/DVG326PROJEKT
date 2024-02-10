package inputData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
/**
 * 
 * @author William Pettersson
 * @info operations for loadíng png files
 * @version 2023-10-27
 *
 */

public class ImageData {
	
	private BufferedImage image;
	
	private ArrayList<String> folderNames;
	private ArrayList<Integer> folderSize;
	
	public ImageData(String user,String folder){
		getFolderSpecs(user);
		String photo = "gavle";
		for(int i = 0; i < folderSize.size();i++) {
			if(folderSize.get(i)== 1 && folderNames.get(i).equals(folder)) {
				photo = "sodermalm";
			}else if(folderSize.get(i)== 2 && folderNames.get(i).equals(folder)) {
				photo = "salenMora";
			}else if(folderSize.get(i)== 3 && folderNames.get(i).equals(folder)) {
				photo = "lidingo";
			}else if(folderSize.get(i)== 9 && folderNames.get(i).equals(folder)) {
				photo = "grekland";
			}else if(folderSize.get(i)== 14 && folderNames.get(i).equals(folder)){
				photo = "gavle";
			}
		}
		for(int i = 0; i < folderNames.size();i++) {
			if(folderNames.get(i).equals(folder)) {
				File path = new File("./Kartor/"+photo+".png");
				try {
					image = ImageIO.read(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void getFolderSpecs(String user) {
		folderNames = new ArrayList<>();
		folderSize = new ArrayList<>();
		//this will be the file path
		File dir = new File("./userData/"+user+"/activities/");
		getDir(dir.listFiles());
	}
		
	public void getDir(File[] files) {
        for (File dir : files) {
            if (dir.isDirectory()) folderNames.add(dir.getName());
            if (dir.isDirectory()) folderSize.add(dir.listFiles().length);
        }
    }
	
	public BufferedImage getMapImage() {
		return image;
	}
}



