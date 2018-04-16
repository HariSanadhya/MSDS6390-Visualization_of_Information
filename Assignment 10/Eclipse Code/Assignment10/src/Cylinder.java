
/*
 * Class that can be used to create a 3D cylinder
 * Cylinder can be created with some specific color or using an image which will be applied to it as a texture
 * Cylinder design implemented using reference at processing site - https://processing.org/examples/texturecylinder.html
 */

import processing.core.*;

public class Cylinder {
	// tubeRes is the number sides of the polygon that is used to create the
	// Cylinder, the higher it is, shape of cylinder would be more precisely
	// circular.
	private int tubeRes = 36;
	// tubeX is the angular displacement along the x axis to form the polygon
	private float[] tubeX = new float[tubeRes];
	// tubeY is the angular displacement along the y axis to form the polygon
	private float[] tubeY = new float[tubeRes];
	// store the image that is to be used as the texture of the cylinder
	private PImage img;
	// Path of the image to be used as the texture
	String textureImage;
	// color of the cylinder in case texture image is not provided
	int colour;

	// boolean variable that specifies if Cylinder needs to be created with texture
	// or with some color
	private boolean isTexture;

	// Constructor to initialize Cylinder object with texture
	public Cylinder(PApplet obj, String imagePath) {
		_init(obj, imagePath);
		isTexture = true;
	}

	// Constructor to initialize Cylinder object with a specific color
	// This color can be overridden when drawing the Cylinder
	public Cylinder(PApplet obj, int colour) {
		_init(obj);
		isTexture = false;
		this.colour = colour;
	}

	// Constructor to initialize Cylinder object with default setting - white color
	public Cylinder(PApplet obj) {
		_init(obj);
		isTexture = false;
		this.colour = 255;
	}

	// Initialize the tubeX and tubeY arrays
	void _init(PApplet obj) {
		float angle = 360.0f / tubeRes;
		for (int i = 0; i < tubeRes; i++) {
			tubeX[i] = PApplet.cos(PApplet.radians(i * angle));
			tubeY[i] = PApplet.sin(PApplet.radians(i * angle));
		}
	}

	// Initialize the tubeX and tubeY arrays and load the image to be used as
	// texture
	void _init(PApplet obj, String imagePath) {
		_init(obj);
		img = obj.loadImage(imagePath);
	}

	/*
	 * Function that creates the Cylinder. 
	 * Parameters are: the PApplet object which specifies the canvas at which the cylinder needs to be created. 
	 * Radius of the cylinder that decides its thickness. 
	 * Height specifies the height of the Cylinder.
	 * Color specifies the color of the Cylinder. 
	 * posNeg specifies the direction of the Cylinder with respect to the current position on the sketch - can be POSITIVE, NEGATIVE or null.
	 * NOTE - If the Cylinder object contains texture, specifying color while drawing the Cylinder will not impact the Cylinder
	 */
	void drawCylinder(PApplet obj, float radius, float height, int colour, Direction posNeg) {
		createCylinder(obj, radius, height, colour, posNeg);
	}

	/*
	 * Function that creates the Cylinder. 
	 * Parameters are: the PApplet object which specifies the canvas at which the cylinder needs to be created. 
	 * Radius of the cylinder that decides its thickness. 
	 * Height specifies the height of the Cylinder.
	 * posNeg specifies the direction of the Cylinder with respect to the current position on the sketch - can be POSITIVE, NEGATIVE or null.
	 * NOTE - If the Cylinder object contains texture, specifying color while drawing the Cylinder will not impact the Cylinder
	*/
	void drawCylinder(PApplet obj, float radius, float height, Direction posNeg) {
		createCylinder(obj, radius, height, colour, posNeg);
	}

	// Function that actually draws the Cylinder on the Sketch
	private void createCylinder(PApplet obj, float radius, float height, int colour, Direction posNeg) {
		float x, y, z, u, v;

		if (img == null)
			v = 0;
		else
			v = img.height;
		
		// Create Cylindrical pipe
		obj.beginShape(PConstants.QUAD_STRIP);
		if (isTexture) {
			obj.texture(img);
		} else {
			obj.fill(colour);
		}
		obj.noStroke();
		for (int i = 0; i < tubeRes; i++) {
			x = tubeX[i] * radius;
			z = tubeY[i] * radius;
			if (img != null)
				u = img.width / tubeRes * i;
			else
				u = 0;
			if (posNeg == null) {
				obj.vertex(x, -height / 2, z, u, 0);
				obj.vertex(x, height / 2, z, u, v);
			} else if (posNeg == Direction.NEGATIVE) {
				obj.vertex(x, -height, z, u, 0);
				obj.vertex(x, 0, z, u, v);
			} else {
				obj.vertex(x, 0, z, u, 0);
				obj.vertex(x, height, z, u, v);
			}
		}
		x = tubeX[0] * radius;
		z = tubeY[0] * radius;
		if (posNeg == null) {
			obj.vertex(x, -height / 2, z, 0, 0);
			obj.vertex(x, height / 2, z, 0, v);
		} else if (posNeg == Direction.NEGATIVE) {
			obj.vertex(x, -height, z, 0, 0);
			obj.vertex(x, 0, z, 0, v);
		} else {
			obj.vertex(x, 0, z, 0, 0);
			obj.vertex(x, height, z, 0, v);
		}
		obj.endShape();

		// Cover the cylindrical pipe
		obj.beginShape();
		if (isTexture) {
			obj.texture(img);
		} else {
			obj.fill(colour);
		}
		for (int i = 0; i < tubeRes; i++) {
			x = tubeX[i] * radius;
			z = tubeY[i] * radius;
			if (img != null)
				u = img.width / tubeRes * i;
			else
				u = 0;
			if (posNeg == null) {
				obj.vertex(x, -height / 2, z, u, 0);
			} else if (posNeg == Direction.NEGATIVE) {
				obj.vertex(x, -height, z, u, 0);
			} else {
				obj.vertex(x, 0, z, u, 0);
			}

		}
		x = tubeX[0] * radius;
		z = tubeY[0] * radius;
		if (posNeg == null) {
			obj.vertex(x, -height / 2, z, 0, 0);
		} else if (posNeg == Direction.NEGATIVE) {
			obj.vertex(x, -height, z, 0, 0);
		} else {
			obj.vertex(x, 0, z, 0, 0);
		}
		obj.endShape();
		obj.beginShape();
		if (isTexture) {
			obj.texture(img);
		} else {
			obj.fill(colour);
		}
		obj.texture(img);
		for (int i = 0; i < tubeRes; i++) {
			x = tubeX[i] * radius;
			z = tubeY[i] * radius;
			if (img != null)
				u = img.width / tubeRes * i;
			else
				u = 0;
			if (posNeg == null) {
				obj.vertex(x, height / 2, z, u, v);
			} else if (posNeg == Direction.NEGATIVE) {
				obj.vertex(x, 0, z, u, v);
			} else {
				obj.vertex(x, height, z, u, v);
			}
		}
		x = tubeX[0] * radius;
		z = tubeY[0] * radius;
		if (posNeg == null) {
			obj.vertex(x, height / 2, z, 0, v);
		} else if (posNeg == Direction.NEGATIVE) {
			obj.vertex(x, 0, z, 0, v);
		} else {
			obj.vertex(x, height, z, 0, v);
		}
		obj.endShape();
	}

}
