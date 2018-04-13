import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import de.bezier.data.sql.MySQL;
import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class DataImport {
	private MySQL db;
	private PApplet object = new PApplet();
	public Table dataToPlot;
	public int minReviews = 1000;
	public int maxReviews = 0;

//	private class TableOne {
//		int user_creation_year;
//		int elite_flag;
//		int[] totalReviews = new int[13];
//	}

	private void setMaxMinReviews() {
		dataToPlot.print();
		for (TableRow row : dataToPlot.rows()) {
			for (int i = 2; i <= 14; i++) {
				if (minReviews > row.getInt(i))
					minReviews = row.getInt(i);
				if (maxReviews < row.getInt(i))
					maxReviews = row.getInt(i);
			}
			PApplet.print(minReviews, maxReviews);
			
		}
	}

	public DataImport() {
		// default is to read from csv file
		dataToPlot = readDataFromCSV();
		setMaxMinReviews();
	}

	public DataImport(boolean mySql) {
		// if mySql, then read data from mySQL database else read data from csv file
		if (mySql) {
			dataToPlot = readDataFromMySQL();
		} else {
			dataToPlot = readDataFromCSV();
		}
		setMaxMinReviews();
	}

	private Table readDataFromMySQL() {
		db = new MySQL(object, "localhost", "yelp_db", "hari", "test123"); // open database file
		db.setDebug(false);
		try {
			if (db.connect()) {

				db.query("SELECT * FROM user_comments_agreegate");
//				ArrayList<TableOne> record = new ArrayList<TableOne>();
				Table data = new Table();
				String[] columnName = { "user_creation_year", "elite_flag", "TotalReviews_2005", "TotalReviews_2006",
						"TotalReviews_2007", "TotalReviews_2008", "TotalReviews_2009", "TotalReviews_2010",
						"TotalReviews_2011", "TotalReviews_2012", "TotalReviews_2013", "TotalReviews_2014",
						"TotalReviews_2015", "TotalReviews_2016", "TotalReviews_2017" };
				for (String column : columnName) {
					data.addColumn(column, Table.INT);
				}
				while (db.next()) {
					TableRow row = data.addRow();
					// TableOne t = new TableOne();
					db.setFromRow(row);
					// record.add(t);
				}

				return data;
			} else {
				PApplet.print("Unable to connect to mySQL, reading CSV data");
				// If unable to connect to the database, then read data from csv file
				return readDataFromCSV();
			}

		} catch (Exception exp) {
			// If any exception occurs then read data from csv
			PApplet.print("Exception occured while reading data from mySQL, reading CSV data");
			PApplet.print(exp.getMessage());
			return readDataFromCSV();
		}
	}

	private Table readDataFromCSV() {
		Table data = object.loadTable("/media/hkns/myDrive/Eclipse_workspace/Assignment10/data/user_comments_agreegate.csv", "header");
		return data;
	}

}
