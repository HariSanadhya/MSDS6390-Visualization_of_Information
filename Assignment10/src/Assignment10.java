import processing.core.*;
import processing.data.Table;
import processing.data.TableRow;

public class Assignment10 extends PApplet {
	DataImport importObject;
	private static boolean drawFirstTime = true;
	
	public void setup() {
		noLoop();
		
	}

	public void settings() {
		size(1000, 800, P3D);
	}

	public void draw() {
		if(drawFirstTime) {
			try {
		importObject = new DataImport();
		drawFirstTime = false;
		}
			catch(Exception exp) {
				Table data = loadTable("/media/hkns/myDrive/Eclipse_workspace/Assignment10/data/user_comments_agreegate.csv", "header");
//				importObject.dataToPlot = data;
				data.print();
			}
		}
		background(100, 150, 150);
		int recordCount = 0;
		for (TableRow row : importObject.dataToPlot.rows()) {
			if (row.getInt(1) == 0)
				fill(100, 100, 0);
			else
				fill(50, 150, 50);
			for (int i = 2; i <= 14; i++) {
				pushMatrix();
				translate(200 + ((i - 1) * 54 / 2), ((i - 1) * 54 / 2), recordCount * 54);
				sphere(map(row.getInt(i), importObject.minReviews, importObject.maxReviews, 0, 54));
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