package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

import controller.Stats;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * 
 * @author William Pettersson
 * @info operations for making the presentation for each activity
 * @version 2023-10-27
 *
 */

public class ActivityView extends JTabbedPane{
	
	private Stats stats;
	private int width;
	private int height;
	BufferedImage image;
	String activityName;
	
	public ActivityView(Stats stats,String activityName,int width, int height,BufferedImage image) {
		this.stats = stats;
		this.width = width;
		this.height = height;
		this.image = image;
		//makeSpeedPane();
		this.activityName = activityName;
		setPreferredSize(new Dimension(width, height));
		//setLayout(new GridLayout(1, 1));
		makeRoutePane();
		makeSpeedPane();
		makeCadencePane();
		makeHeartRatePane();
		makeAltitudePane();
		makeTempoPane();
		makePerKmPane();
		Timer timer = new Timer(0, k -> makeTimeInHeartPane());
        timer.setRepeats(false);
        timer.start();
	}
	
	public void makeSpeedPane(){
		JPanel speedPanel = new JPanel(new GridLayout(2,1));
		JPanel superPanel = new JPanel(new GridLayout(2,1));
		JPanel infoPanel = new JPanel(new GridLayout(1,3));
		JPanel info2Panel = new JPanel(new GridLayout(1,2));
		superPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		speedPanel.setName("Speed");
		speedPanel.setPreferredSize(new Dimension(width, height));
		superPanel.setPreferredSize(new Dimension(width, height/2));  
		speedPanel.add(new Graph(stats,"Speed",Color.GREEN,width,height/2,stats.getStartValue("Date"),activityName));
		infoPanel.add(new JLabel("Max speed for activity "+String.format("%.2f",Double.parseDouble(stats.getMaxValue("Speed")))+" km/h"));
		infoPanel.add(new JLabel("Min speed for activity "+String.format("%.2f",Double.parseDouble(stats.getMinValue("Speed")))+" km/h"));
		infoPanel.add(new JLabel("Mean speed for activity "+String.format("%.2f",Double.parseDouble(stats.getMeanValue("Speed")))+" km/h"));
		info2Panel.add(new JLabel("Mean speed while going upwards for activity "+String.format("%.2f",Double.parseDouble(stats.getMeanSpeedVerticalUp()))+" km/h"));
		info2Panel.add(new JLabel("Mean speed while going downwards for activity "+String.format("%.2f",Double.parseDouble(stats.getMeanSpeedVerticalDown()))+" km/h"));
		superPanel.add(infoPanel);
		superPanel.add(info2Panel);
		speedPanel.add(superPanel);
		speedPanel.repaint();
		add(speedPanel);
	}
	
	public void makeCadencePane(){
		JPanel cadencePanel = new JPanel(new GridLayout(2,1));
		JPanel infoPanel = new JPanel(new GridLayout(1,3));
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cadencePanel.setName("Cadence");
		cadencePanel.setPreferredSize(new Dimension(width, height));
		infoPanel.setPreferredSize(new Dimension(width, height/4)); 
		cadencePanel.add(new Graph(stats,"Cadence",Color.GREEN,width,height/2,stats.getStartValue("Date"),activityName));
		infoPanel.add(new JLabel("Max cadence for activity "+String.format("%.2f",Double.parseDouble(stats.getMaxValue("Cadence")))+" SPM"));
		infoPanel.add(new JLabel("Min cadence for activity "+String.format("%.2f",Double.parseDouble(stats.getMinValue("Cadence")))+" SPM"));
		infoPanel.add(new JLabel("Mean cadence for activity "+String.format("%.2f",Double.parseDouble(stats.getMeanValue("Cadence")))+" SPM"));
		cadencePanel.add(infoPanel);
		cadencePanel.repaint();
		add(cadencePanel);
	}
	
	public void makeAltitudePane(){
		JPanel cadencePanel = new JPanel(new GridLayout(2,1));
		JPanel superPanel = new JPanel(new GridLayout(2,1));
		JPanel infoPanel = new JPanel(new GridLayout(1,3));
		JPanel speedPanel = new JPanel(new GridLayout(2,2));
		superPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cadencePanel.setName("Altitude");
		cadencePanel.setPreferredSize(new Dimension(width, height));
		superPanel.setPreferredSize(new Dimension(width, height/2)); 
		cadencePanel.add(new Graph(stats,"Altitude",Color.GREEN,width,height/2,stats.getStartValue("Date"),activityName));
		infoPanel.add(new JLabel("Max Altitude for activity "+String.format("%.2f",Double.parseDouble(stats.getMaxValue("Altitude")))+" M"));
		infoPanel.add(new JLabel("Min Altitude for activity "+String.format("%.2f",Double.parseDouble(stats.getMinValue("Altitude")))+" M"));
		infoPanel.add(new JLabel("Mean Altitude for activity "+String.format("%.2f",Double.parseDouble(stats.getMeanValue("Altitude")))+" M"));
		speedPanel.add(new JLabel("Distance traveled upwards for activity "+String.format("%.2f",Double.parseDouble(stats.getDistanceUp()))+" M"));
		speedPanel.add(new JLabel("Distance traveled downwards for activity "+String.format("%.2f",Double.parseDouble(stats.getDistanceDown()))+" M"));
		speedPanel.add(new JLabel(stats.getHighestInclination()));
		speedPanel.add(new JLabel(stats.getLowestInclination()));
		superPanel.add(infoPanel);
		superPanel.add(speedPanel);
		cadencePanel.add(superPanel);
		cadencePanel.repaint();
		add(cadencePanel);
	}
	public void makeHeartRatePane(){
		JPanel heartRatePanel = new JPanel(new GridLayout(2,1));
		JPanel infoPanel = new JPanel(new GridLayout(1,3));
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		heartRatePanel.setName("Heart Rate");
		heartRatePanel.setPreferredSize(new Dimension(width, height));
		infoPanel.setPreferredSize(new Dimension(width, height/2)); 
		heartRatePanel.add(new Graph(stats,"HeartRate",Color.GREEN,width,height/2,stats.getStartValue("Date"),activityName));
		infoPanel.add(new JLabel("Max heart rate for activity "+String.format("%.2f",Double.parseDouble(stats.getMaxValue("HeartRate")))+" BPM"));
		infoPanel.add(new JLabel("Min heart rate for activity "+String.format("%.2f",Double.parseDouble(stats.getMinValue("HeartRate")))+" BPM"));
		infoPanel.add(new JLabel("Mean heart rate for activity "+String.format("%.2f",Double.parseDouble(stats.getMeanValue("HeartRate")))+" BPM"));
		heartRatePanel.add(infoPanel);
		heartRatePanel.repaint();
		add(heartRatePanel);
	}
	
	
	public void makeRoutePane(){
		JPanel routePanel = new JPanel(new GridLayout(2,1));
		JPanel infoPanel = new JPanel(new GridLayout(2,2));	
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		routePanel.setName("Route");
		routePanel.setPreferredSize(new Dimension(width, height));
		infoPanel.setPreferredSize(new Dimension(width, height/2)); 
		routePanel.add(new Plotter(stats,width,height/2,stats.getStartValue("Date"),image,activityName));
		infoPanel.add(new JLabel("Total distance of route "+String.format("%.2f",Double.parseDouble(stats.getEndValue("Distance")))+" M"));
		infoPanel.add(new JLabel("Start time for activity "+stats.getStartValue("Time")));
		infoPanel.add(new JLabel("End time for activity "+stats.getEndValue("Time")));
		infoPanel.add(new JLabel("Total time for activity "+stats.getTotalTimeForActivity()));
		routePanel.add(infoPanel);
		routePanel.repaint();
		add(routePanel);
	}
	
	public void makeTempoPane() {
		JPanel tempoPanel = new JPanel(new GridLayout(2,1));
		JPanel superPanel = new JPanel(new GridLayout(2,1));
		JPanel infoPanel = new JPanel(new GridLayout(1,1));
		JPanel speedPanel = new JPanel(new GridLayout(3,1));
		superPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		tempoPanel.setName("Tempo");
		tempoPanel.setPreferredSize(new Dimension(width, height));
		infoPanel.setPreferredSize(new Dimension(width, height/2)); 
		tempoPanel.add(new Graph(stats,"Tempo",Color.GREEN,width,height/2,stats.getStartValue("Date"),activityName));
		speedPanel.add(new JLabel(stats.getMeanTempo()));
		speedPanel.add(new JLabel(stats.getFastestKm()));
		speedPanel.add(new JLabel(stats.getSlowestKm()));
		superPanel.add(speedPanel);
		tempoPanel.add(superPanel);
		tempoPanel.repaint();
		add(tempoPanel);
	}
	
	public void makeTimeInHeartPane() {
		Timer timer = new Timer(1000, k -> repaint());
       // timer.setRepeats(false);
        timer.start();
		JPanel heartZonePanel = new JPanel(new GridLayout(2,1));
		JPanel infoPanel = new JPanel(new GridLayout(3,1));
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		heartZonePanel.setName("Heart Zone");
		heartZonePanel.setPreferredSize(new Dimension(width, height));
		infoPanel.setPreferredSize(new Dimension(width, height/2)); 
		heartZonePanel.add(new SpecialGraph(stats,"HeartZone","TimeInZone",Color.GREEN,width,height/2,stats.getStartValue("Date"),activityName));
		infoPanel.add(new JLabel("Activity zone is from "+String.format("%.2f",Double.parseDouble(stats.getMinValue("HeartZone")))+"-"+String.format("%.2f",Double.parseDouble(stats.getMaxValue("HeartZone")))+" BPM"));
		infoPanel.add(new JLabel(stats.getHighestTimeInZone()));
		infoPanel.add(new JLabel(stats.getLowestTimeInZone()));
		heartZonePanel.add(infoPanel);
		heartZonePanel.repaint();
		add(heartZonePanel);
	}
	
	public void makePerKmPane() {
		JPanel perKmPanel = new JPanel(new GridLayout(3,3));
		perKmPanel.setBorder(BorderFactory.createTitledBorder(activityName+" Date for activity "+stats.getStartValue("Date")));
		perKmPanel.setPreferredSize(new Dimension(width, height));
		perKmPanel.setName("Per Km");
		perKmPanel.add(new JLabel("Max Speed for activity "+String.format("%.2f",Double.parseDouble(stats.getKmMaxValue("Speed")))+" km/h"));
		perKmPanel.add(new JLabel("Min Speed for activity "+String.format("%.2f",Double.parseDouble(stats.getKmMinValue("Speed")))+" km/h"));
		perKmPanel.add(new JLabel("Mean Speed for activity "+String.format("%.2f",Double.parseDouble(stats.getKmMeanValue("Speed")))+" km/h"));
		perKmPanel.add(new JLabel("Max heart rate for activity "+String.format("%.2f",Double.parseDouble(stats.getKmMaxValue("HeartRate")))+" BPM"));
		perKmPanel.add(new JLabel("Min heart rate activity "+String.format("%.2f",Double.parseDouble(stats.getKmMinValue("HeartRate")))+" BPM"));
		perKmPanel.add(new JLabel("Mean heart rate activity "+String.format("%.2f",Double.parseDouble(stats.getKmMeanValue("HeartRate")))+" BPM"));
		perKmPanel.add(new JLabel("Max cadence for activity "+String.format("%.2f",Double.parseDouble(stats.getKmMaxValue("Cadence")))+" SPM"));
		perKmPanel.add(new JLabel("Min cadence for activity "+String.format("%.2f",Double.parseDouble(stats.getKmMinValue("Cadence")))+" SPM"));
		perKmPanel.add(new JLabel("Mean cadence for activity "+String.format("%.2f",Double.parseDouble(stats.getKmMeanValue("Cadence")))+" SPM"));
		perKmPanel.repaint();
		add(perKmPanel);
	}
}
