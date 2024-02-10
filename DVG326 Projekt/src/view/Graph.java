package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import controller.Stats;
/**
 * 
 * @author William Pettersson
 * @info operations for makeing a graph
 * @version 2023-10-27
 *
 */


public class Graph extends JPanel{
		private static final long serialVersionUID = 1L;
		private int width;
		private int height;
		private double totalElapsedTime; // related to width
		private double minDataValue; // related to height
		private double maxDataValue; // related to height
		private int[] xArray;
		private int[] yArray; 
		private String column;
		private Color color;
		private Stats stats;

		public Graph(Stats stats,String column,Color color,int width, int height, String date,String activityName) {
			this.stats = stats;
			this.color = color;
			this.column = column;
			setPreferredSize(new Dimension(width, height));
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createTitledBorder(activityName+" Date for activity "+date));
		}

		private void findLimitsInData(){
			minDataValue = Double.parseDouble(stats.getMinValue(column));
			maxDataValue = Double.parseDouble(stats.getMaxValue(column));
			
			totalElapsedTime = Double.parseDouble(stats.getEndValue("ElapsedTime"));
			
		}
		
		private void createArrays(){
			if (stats.getArray(column) != null && stats.getArray(column).size() > 1){
					findLimitsInData();
					width = getWidth();
					height = getHeight();
					yArray = new int[width];
					xArray = new int[width];
					double timeStep = totalElapsedTime / width;
					double yVariation = maxDataValue - minDataValue;
					double yScale = height / yVariation;
					int j = 0;
					for(int x = 0; x < width; x++) {
						double time = x * timeStep;
						while(Double.parseDouble(stats.getArray("ElapsedTime").get(j)) < time) 
							j++;
						double value = Double.parseDouble(stats.getArray(column).get(j));
						value = value - minDataValue;
						yArray[x] = height - (int)(0.5+yScale*value);
						xArray[x] = x;
					}
					
			}
		}

		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			createArrays();
			g.setColor(color);
			g.drawPolyline(xArray, yArray, xArray.length);
		}
}

