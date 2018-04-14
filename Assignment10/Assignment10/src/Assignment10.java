import processing.core.*;
import processing.data.*;

public class Assignment10 extends PApplet {
	static DataImport importObject;
	static Cylinder textured;
	static Cylinder coloured;
	PImage textureImg;

	public void setup() {
		noLoop();
		coloured = new Cylinder(this);
		textured = new Cylinder(this,
				"https://img00.deviantart.net/c00a/i/2013/020/7/e/w__disney_texture_stock_by_redwolf518stock-d5s70kx.jpg");
		importObject = new DataImport(false);
	}

	public void settings() {
		size(1000, 800, P3D);
	}

	public void draw() {
		noStroke();
		lights();
		background(100, 150, 150);
		int recordCount = 0;
		for (TableRow row : importObject.dataToPlot.rows()) {
			if (row.getInt(1) == 0) {
				fill(0);
				text(row.getString(0), 200, 700 / 4, -(recordCount * 300));
			} else {
				fill(0);
				text(row.getString(0), 200, 700 * 3 / 4, -((recordCount - 1) * 300));
			}

			for (int i = 2; i <= 14; i++) {
				pushMatrix();
				float rad = map(row.getInt(i), importObject.minReviews, importObject.maxReviews, 0, 27);

				if (row.getInt(1) == 0) {
					translate(300 + ((i - 2) * 56), 700 / 4, -(recordCount * 300));
					coloured.drawCylinder(this, rad, rad*2, color(100, 100, 0), Direction.POSITIVE);
					fill(255);
					text(row.getColumnTitle(i).substring(row.getColumnTitle(i).length() - 4), 0 - 15, 0 - 15, 0);
				} else {
					translate(300 + ((i - 2) * 56), 700 * 3 / 4, -((recordCount - 1) * 300));
					textured.drawCylinder(this, rad, rad * 2, Direction.NEGATIVE);
					fill(255);
					text(row.getColumnTitle(i).substring(row.getColumnTitle(i).length() - 4), 0 - 15, 0 + 15, 0);
				}	
				popMatrix();
			}
			recordCount++;
		}
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "Assignment10" };

		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}