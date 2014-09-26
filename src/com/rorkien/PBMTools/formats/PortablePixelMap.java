package com.rorkien.PBMTools.formats;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PortablePixelMap extends PortableMapFile {
	
	public PortablePixelMap() {
		this.imageType = BufferedImage.TYPE_INT_RGB;
	}		
	
	/**
	 * Builds a single histogram from a pixelmap
	 * The pixelmap contains 3 color channels (red, green and blue), so generate a single
	 * histogram, the image must be desaturated beforehand.
	 * 
	 * @return The generated histogram
	 */
	@Override
	public Map<Integer, Integer> buildHistogram() {
		Map<Integer, Integer> output = new TreeMap<Integer, Integer>();
		
		//Desaturates the image
		int[] grayscaleData = new int[data.length / 3];
		for (int i = 0; i < grayscaleData.length; i++) grayscaleData[i] = (int)(data[i * 3] * 0.3D + data[i * 3 + 1] * 0.59D + data[i * 3 + 2] * 0.11D);

		//Loops through the X axis, which will contain the pixel values
		for (int x = 0; x < getMaxval(); x++) {
			int y = 0;
			
			//Loops through the Y axis, which will contain the pixel quantity
			for (int i = 0; i < grayscaleData.length; i++) if (grayscaleData[i] == x) y++;
			
			//Puts the X and Y values on the map
			output.put(x, y);
		}
		
		return output;
	}

	/**
	 * Builds a histogram for every color channel in the pixelmap
	 * 
	 * @return The list of the built histograms
	 */
	public List<Map<Integer, Integer>> buildRGBHistogram() {
		List<Map<Integer, Integer>> output = new ArrayList<Map<Integer, Integer>>();
		Map<Integer, Integer> red = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> green = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> blue = new TreeMap<Integer, Integer>();
		
		//Loops through the X axis, which will contain the pixel values
		for (int x = 0; x < getMaxval(); x++) {
			int r = 0, g = 0, b = 0;
			
			//Loops through the Y axis, which will contain the pixel quantity
			for (int i = 0; i < data.length; i++) {
				if (data[i] == x) {
					if (i % 3 == 0) r++;
					else if (i % 3 == 1) g++;
					else if (i % 3 == 2) b++;
				}
			}
			
			//Puts the X and Y values on the map
			red.put(x, r);
			green.put(x, g);
			blue.put(x, b);
		}
		
		output.add(red);
		output.add(green);
		output.add(blue);
		
		return output;
	}
	
}	


