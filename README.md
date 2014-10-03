jPBMTools
=========

Java parser and analyzer of the Netpbm file formats.


Examples
=========

Reading a PBM file:

```java
PortableMapFile file = Parser.parse(filename);
```

The pixel matrix is acessible via the *file.getData()* method, which returns an array of *width * height* Integers

Exporting a *PortableMapFile* object to a real image:

```java
Utils.exportImage(file.buildImage(), "png", "file_parsed.png");
```

Plotting and exporting the histogram:

```java
Map<Integer, Integer> histogram = file.buildHistogram();

//Exports the histogram as a bar graph
Utils.buildHistogramImage(histogram, file.getImageType(), "histogram.png");

//Exports the histogram as a comma-separated-value (CSV)
Utils.exportCSV(histogram, "histogram.csv");
```


Advanced Functions
=========

There is still no support for custom functions, but since you have full access to the pixel matrix, you can iterate through the entire array

For instance, you can apply simple functions:

```java
for (int i = 0; i < image.getData().length; i++) {
  //Makes the image 50% darker
  image.getData()[i] = (int) (0.5 * image.getData()[i]);
  
  //Makes the image 100% brighter
  image.getData()[i] = (int) (2 * image.getData()[i]);
  
  //Negates the image
  image.getData()[i] = (int) (image.getMaxVal() -  image.getData()[i]);
  
  //Assures there won't be underflows and overflows
  if (image.getData()[i] < 0) image.getData()[i] = 0;
  else if (image.getData()[i] > image.getMaxval()) image.getData()[i] = image.getMaxval();
}
```

You can also apply segmentation:

```java
//Since the image will only have 2 colors, we need to set up when we want to export the segmented image
image.setImageType(BufferedImage.TYPE_BYTE_BINARY);

//We want a threshold of 50
for (int i = 0; i < image.getData().length; i++) {
  if (image.getData()[i] >= 50) image.getData()[i] = 0;
  else image.getData()[i] = 1;
}
```

