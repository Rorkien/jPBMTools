package com.rorkien.PBMTools.formats;

import java.awt.image.BufferedImage;

public class PortableGrayscaleMap extends PortableMapFile {
	
	public PortableGrayscaleMap() {
		this.imageType = BufferedImage.TYPE_BYTE_GRAY;
	}		
}