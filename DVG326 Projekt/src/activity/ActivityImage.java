package activity;

import java.awt.image.BufferedImage;
import inputData.ImageData;

public class ActivityImage {
	ImageData image;
	
	public ActivityImage(String user,String folder){
		image = new ImageData(user,folder);
	}
	
	public BufferedImage getMapImage() {
		return image.getMapImage();
	}
}
