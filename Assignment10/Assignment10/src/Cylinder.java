import processing.core.*;

public class Cylinder {
	private int tubeRes = 36;
	private float[] tubeX = new float[tubeRes];
	private float[] tubeY = new float[tubeRes];
	private PImage img;
	String textureImage;
	int colour;
	private boolean isTexture;

	public Cylinder(PApplet obj, String imagePath) {
		_init(obj, imagePath);
		isTexture = true;
	}

	public Cylinder(PApplet obj, int colour) {
		_init(obj);
		isTexture = false;
		this.colour = colour;
	}

	public Cylinder(PApplet obj) {
		_init(obj);
		isTexture = false;
		this.colour = 255;
	}

	void _init(PApplet obj, String imagePath) {
		_init(obj);
		img = obj.loadImage(imagePath);
	}

	void _init(PApplet obj) {
		float angle = 360.0f / tubeRes;
		for (int i = 0; i < tubeRes; i++) {
			tubeX[i] = PApplet.cos(PApplet.radians(i * angle));
			tubeY[i] = PApplet.sin(PApplet.radians(i * angle));
		}
	}

	void drawCylinder(PApplet obj, float radius, float height, int colour, Direction posNeg) {
		createCylinder(obj, radius, height, colour, posNeg);
	}

	void drawCylinder(PApplet obj, float radius, float height, Direction posNeg) {
		createCylinder(obj, radius, height, colour, posNeg);
	}

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
