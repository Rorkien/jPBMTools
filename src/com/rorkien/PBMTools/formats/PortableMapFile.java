package com.rorkien.PBMTools.formats;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

public abstract class PortableMapFile implements Cloneable {
	protected int imageType;
	protected int width, height, maxval;
	protected int[] data;
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setMaxval(int maxval) {
		this.maxval = maxval;
	}
	
	public void setData(int[] data) {
		this.data = data;
	}
	
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getMaxval() {
		return maxval;
	}
	
	public int[] getData() {
		return data;
	}
	
	public int getImageType() {
		return imageType;
	}
	
	/**
	 * Clones this object into a new instance
	 * 
	 * @return The cloned object
	 */
	public PortableMapFile clone() {
		PortableMapFile cloned = null;
		
		try {
			cloned = (PortableMapFile) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Error: Could not clone object");
		}
		
		cloned.data = this.data.clone();
		return cloned;
	}
	
	/**
	 * Builds a BufferedImage from the object
	 * 
	 * @return The built BufferedImage
	 */
	public BufferedImage buildImage() {		
		//Generates a BufferedImage on the correct size and format
		BufferedImage output = new BufferedImage(getWidth(), getHeight(), this.imageType);
		
		//Transfers the pixel matrix to the object
		output.getRaster().setPixels(0, 0, getWidth(), getHeight(), getData());
		
		return output;
	}
	
	/**
	 * Builds a map containing the X and Y histogram axes
	 * 
	 * @return The generated histogram
	 */
	public Map<Integer, Integer> buildHistogram() {
		Map<Integer, Integer> output = new TreeMap<Integer, Integer>();

		//Loops through the X axis, which will contain the pixel values
		for (int x = 0; x < getMaxval(); x++) {
			int y = 0;
			
			//Loops through the Y axis, which will contain the pixel quantity
			for (int i = 0; i < getData().length; i++) if (getData()[i] == x) y++;
			
			//Puts the X and Y values on the map
			output.put(x, y);
		}			
		
		return output;
	}
}
