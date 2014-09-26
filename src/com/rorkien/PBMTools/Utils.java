package com.rorkien.PBMTools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.imageio.ImageIO;

public class Utils {
	//Whitespace characters on the Netpbm project: Space, TAB, LF, VT, FF, CR
	static final char[] WHITESPACE_CHARACTERS = { 32, 9, 10, 11, 12, 13 };
	static final char LINEFEED_CHARACTER = 10;
	static final char COMMENTARY_CHARACTER = '#';
	
	/**
	 * Tests if the desired character is a whitespace character
	 * 
	 * @param character Character as integer that will be tested
	 * @return true if the character is a whitespace character
	 */
	public static boolean isWhitespace(int character) {
		for (int i = 0; i < WHITESPACE_CHARACTERS.length; i++) {
			if ((char)character == WHITESPACE_CHARACTERS[i]) return true;
		}
		return false;
	}
	
	/**
	 * Exports a BufferedImage to to a image in the desired format.
	 * 
	 * @param image A BufferedImage to be exported
	 * @param imageType The desired file format
	 * @param filename Path to the exported file
	 */
	public static void exportImage(BufferedImage image, String imageType, String filename) {
		try {
			File outputfile = new File(filename);
			ImageIO.write(image, imageType, outputfile);
		} catch (IOException e) {
			System.out.println("Error: Could not write file: " + e.getMessage());
		}
	}
	
	/**
	 * Exports a map to Comma Separated Values format
	 * 
	 * @param map Map containing the values to be exported
	 * @param filename Path to the exported file
	 */
	public static void exportCSV(Map<?, ?> map, String filename) {
		try {
			PrintWriter writer = new PrintWriter(filename);
			for (Map.Entry<?, ?> entry : map.entrySet()) writer.println(entry.getKey() + ";" + entry.getValue());
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not write file: " + e.getMessage());
		}
	}
	
	/**
	 * Exports a histogram map to a image
	 * 
	 * @param histogram Map containing the histogram to be exported
	 * @param imageType The desired file format
	 * @param filename Path to the exported file
	 */
	public static void buildHistogramImage(Map<Integer, Integer> histogram, int imageType, String filename) {
		final int size = histogram.keySet().size();
		final int margin = 24;
		final int width = size + 2;
		final int height = 320;
		
		//Searches for the highest value in the histogram. This will be used to normalize the values according to the image height
		int maxHistogramValue = 0;
		for (Integer i : histogram.values()) if (i > maxHistogramValue) maxHistogramValue = i;
		
		//Creates the image
		BufferedImage image = new BufferedImage(width, height, imageType);
		Graphics graphics = image.getGraphics();
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		
		//Plots the base lines
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, height - margin, width - 1, 5);
		
		//Plots the histogram columns
		for (int x = 0; x < size; x++) {
			int y = (int) ((1D * histogram.get(x) / maxHistogramValue) * (height - margin));
			
			graphics.setColor(new Color(x, x, x));
			graphics.drawLine(x + 1, height - margin, x + 1, height - margin + 4);
			
			graphics.setColor(Color.BLACK);			
			graphics.drawLine(x + 1, height - margin, x + 1, height - margin - y);
		}
		 
		//Prints some informations
		String info = "Maior Y: " + maxHistogramValue;
		graphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		graphics.drawString(info, (width / 2) - (info.length() * 6 / 2), height - 5);
		
		//Saves the file
		exportImage(image, "png", filename);
	}
}
