import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class DataImport {
	private MySQLConnect mysqlConnect = new MySQLConnect();
	private PApplet object = new PApplet();
	public Table actualData, dataToPlot;
	public float minReviews = 1000;
	public float maxReviews = 0;

	private void setMaxMinReviews() {
		for (TableRow row : dataToPlot.rows()) {
			for (int i = 2; i <= 14; i++) {
				if (minReviews > row.getFloat(i))
					minReviews = row.getFloat(i);
				if (maxReviews < row.getFloat(i))
					maxReviews = row.getFloat(i);
			}
		}
		PApplet.println("minReviews, maxReviews", minReviews, maxReviews);
	}

	public DataImport() {
		// default is to read from csv file
		actualData = readDataFromCSV();
		dataToPlot = logScaleConversion(actualData);
		setMaxMinReviews();
	}

	private Table logScaleConversion(Table data) {
		Table logData = data.copy();
		for (TableRow row : logData.rows()) {
			for (int i = 2; i <= 14; i++)
				row.setFloat(i, (row.getFloat(i) == 0) ? 0 : PApplet.log(row.getFloat(i)) / PApplet.log(4));
		}
		logData.print();
		return logData;
	}

	//
	public DataImport(boolean mySql) {
		// if mySql, then read data from mySQL database else read data from csv file
		if (mySql) {
			actualData = readDataFromMySQL();
		} else {
			actualData = readDataFromCSV();
		}
		dataToPlot = logScaleConversion(actualData);
		setMaxMinReviews();
	}

	private Table readDataFromMySQL() {
		Table data = new Table();
		String sql = "SELECT * FROM yelp_db.user_comments_agreegate";
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			String[] columnName = { "user_creation_year", "elite_flag", "TotalReviews_2005", "TotalReviews_2006",
					"TotalReviews_2007", "TotalReviews_2008", "TotalReviews_2009", "TotalReviews_2010",
					"TotalReviews_2011", "TotalReviews_2012", "TotalReviews_2013", "TotalReviews_2014",
					"TotalReviews_2015", "TotalReviews_2016", "TotalReviews_2017" };

			for (String column : columnName) {
				data.addColumn(column, Table.FLOAT);
			}
			System.out.println(columnName.length);
			while (rs.next()) {
				TableRow row = data.addRow();
				row.print();
				for (int i = 0; i < columnName.length; i++) {
					row.setFloat(i, rs.getFloat(i + 1));
				}
				row.print();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlConnect.disconnect();
		}
		return data;
	}

	private Table readDataFromCSV() {
		PApplet.print("Reading CSV file");
		String path = object.sketchPath();
		Table data = object.loadTable(path + "/src/data/user_comments_agreegate.csv", "header");
		return data;
	}

}
