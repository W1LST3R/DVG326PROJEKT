package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import controller.Stats;
/**
 * 
 * @author William Pettersson
 * @info operations for makeing a plotted map of path
 * @version 2023-10-27
 *
 */

public class Plotter extends JPanel {
	private static final long serialVersionUID = 1L;
	private double minLong; // related to height
	private double maxLong; // related to height
	private double minLat; // related to height
	private double maxLat; // related to height
	private int[] xArray;
	private int[] yArray;
	private Stats stats;
	private BufferedImage image;
	
	public Plotter(Stats stats,int width, int height,String date, BufferedImage image,String activityName) {
		this.stats = stats;
		this.image = image;
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createTitledBorder(activityName+" Date for activity "+date));
	}
	

	private void findPlotLimit(){
		minLat = Double.parseDouble(stats.getMinValue("Latitude"));
		maxLat = Double.parseDouble(stats.getMaxValue("Latitude"));
		minLong = Double.parseDouble(stats.getMinValue("Longitude"));
		maxLong = Double.parseDouble(stats.getMaxValue("Longitude"));
	}
	
	private void plotterMap() {
		if (stats.getArray("Latitude") != null && stats.getArray("Latitude").size() > 1){
			Iterator<String> latCopy = stats.getArray("Latitude").iterator();
			Iterator<String> longCopy = stats.getArray("Longitude").iterator();
			findPlotLimit();
			xArray = new int[stats.getArray("Latitude").size()];
			yArray = new int[stats.getArray("Latitude").size()];
			int x = 0;
			while (latCopy.hasNext()&& latCopy.hasNext() &&x < stats.getArray("Latitude").size()) {
			    yArray[x] = getYPixValue(Double.parseDouble(latCopy.next()));
			    xArray[x] = getXPixValue(Double.parseDouble(longCopy.next()));
			    x++;
			    
			}
		}
	}
	
	//Read Longitude value from TrackPoint tp and transform it to suitable pixel value in x-direction
	private int getXPixValue(double point) {
		int xPix = (int)(((point - minLong) / (maxLong - minLong)) * getWidth());
		return xPix;
	
	}
	//Read Latitude value from TrackPoint tp and transform it to suitable pixel value in y-direction
	private int getYPixValue(double point) {
		int yPix = (int)(((point - minLat) / (maxLat - minLat)) * getHeight());
		yPix = getHeight()-yPix; //To adjust for y-axis going "downwards" in graphics
		return yPix;
		
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image,0,0,getWidth(),getHeight(),this);
		plotterMap();
		g.setColor(Color.BLUE);
		g.drawPolyline(xArray, yArray, xArray.length);
	}
}


