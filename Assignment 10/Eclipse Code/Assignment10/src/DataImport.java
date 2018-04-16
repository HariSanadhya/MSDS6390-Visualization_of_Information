/*
 * Class that connects to the data source and transforms the data into the format used in the visualization
 */

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class DataImport {
	
	private MySQLConnect mysqlConnect = new MySQLConnect();
	// Actual data is the raw data read from the data source, dataToPlot is the transformed data. 
	public Table actualData, dataToPlot;
	// transformedDataBackup is the backup of the transformed data.
	private Table transformedDataBackup;
	// Minimum and maximum values of the plotting data.
	public float minReviews = 1000;
	public float maxReviews = 0;
	
	/*
	 * Function to restore data in dataToPlot table using the data from transformedDataBackup
	 *  and then recompute the maximum and minimum values
	 */
	public void restoreData() {
		dataToPlot.clearRows();
		dataToPlot = transformedDataBackup.copy();
		setMaxMinReviews();
	}
	
	// Set the miximum and minimum values in the data thats getting plotted
	private void setMaxMinReviews() {
		minReviews = 1000;
		maxReviews = 0;
		for (TableRow row : dataToPlot.rows()) {
			for (int i = 15; i <= 27; i++) {
				if (minReviews > row.getFloat(i))
					minReviews = row.getFloat(i);
				if (maxReviews < row.getFloat(i))
					maxReviews = row.getFloat(i);
			}
		}
	}
	
	// Constructor to read data using default data source which is from CSV file
	public DataImport(PApplet object) {
		actualData = readDataFromCSV(object);
		dataToPlot = transformData(actualData, true);
		transformedDataBackup = dataToPlot.copy();
		setMaxMinReviews();
	}
	
	/*
	 * Constructor to read data using either MySQL RDBMS or CSV file - based of the mySql boolean parameter
	 *  Data will be read from CSV if for any reason, data cannot be fetched from MySQL RDBMS. 
	 */
	public DataImport(PApplet object, boolean mySql) {
		// if mySql, then read data from mySQL database else read data from csv file
		if (mySql) {
			actualData = readDataFromMySQL(object);
		} else {
			actualData = readDataFromCSV(object);
		}
		dataToPlot = transformData(actualData, true);
		transformedDataBackup = dataToPlot.copy();
		setMaxMinReviews();
	}
	
	/*
	 * Function to read the data from MySQL Database 
	 */
	private Table readDataFromMySQL(PApplet object) {
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
				data.addColumn(column, Table.INT);
			}
			while (rs.next()) {
				TableRow row = data.addRow();
				for (int i = 0; i < columnName.length; i++) {
					row.setInt(i, rs.getInt(i + 1));
				}
			}

		} catch (Exception e) {
			PApplet.println("Exception occured when fetching the data from MySQL, reading data from CSV file");
			data = readDataFromCSV(object);
		} finally {
			// Close the connection to the MySQL server. 
			mysqlConnect.disconnect();
		}
		return data;
	}
	
	/*
	 * Function to read data from CSV file
	 */
	private Table readDataFromCSV(PApplet object) {
		PApplet.print("Reading CSV file");
		Table data = object.loadTable("user_comments_agreegate.csv", "header");
		return data;
	}
	
	/*
	 * Transform the data by taking forth root of the data to plot
	 * addColumns specify if the column to store the transformed information is to be created or not.
	 *  Columns will not be created if they are already present in the dataset and the function is called only
	 *  to re-transform the data as there might be some change in the dataset
	 */
	private Table transformData(Table data, boolean addColumns) {
		Table transformedData = data.copy();
		if(addColumns) {
		String[] columnName = { "transformed_2005", "transformed_2006", "transformed_2007", "transformed_2008",
				"transformed_2009", "transformed_2010", "transformed_2011", "transformed_2012", "transformed_2013",
				"transformed_2014", "transformed_2015", "transformed_2016", "transformed_2017" };
		for (String column : columnName) {
			transformedData.addColumn(column, Table.FLOAT);
		}
		}
		for (TableRow row : transformedData.rows()) {
			for (int i = 2; i <= 14; i++)
				row.setFloat(i + 13, (row.getFloat(i) == 0) ? 0 : PApplet.pow(row.getFloat(i), 0.25f));
		}
		return transformedData;
	}
	
	// Function called to merge the elite and non-elite information rows into a single row 
	public void combineData() {
		int recordCount = 0;
		Table combined = dataToPlot.copy();
		combined.clearRows();
		for (TableRow row : dataToPlot.rows()) {
			recordCount = (recordCount >= 2) ? 0 : recordCount;
			if (recordCount == 0) {
				TableRow newRow = combined.addRow();
				for (int i = 0; i < row.getColumnCount(); i++) {
					if (i <= 14)
						newRow.setInt(i, row.getInt(i));
					else
						newRow.setFloat(i, row.getFloat(i));
				}
			} else {
				int combinedIntVal;
				float combinedFloatVal;
				TableRow newRow = combined.getRow(combined.getRowCount() - 1);
				for (int i = 0; i < row.getColumnCount(); i++) {
					if (i <= 14 && i >= 2) {
						combinedIntVal = row.getInt(i) + newRow.getInt(i);
						newRow.setInt(i, combinedIntVal);
					} else if (i > 14) {
						newRow.setFloat(i, row.getFloat(i));
						combinedFloatVal = row.getFloat(i) + newRow.getFloat(i);
						if (combinedFloatVal > maxReviews)
							maxReviews = combinedFloatVal;
						if (combinedFloatVal > minReviews)
							minReviews = combinedFloatVal;
						newRow.setFloat(i, combinedFloatVal);
					}
				}

			}
			recordCount++;
		}
		dataToPlot.clearRows();
		dataToPlot = combined.copy();
		dataToPlot = transformData(dataToPlot, false);
		setMaxMinReviews();
	}
	
	/*
	 * Function to fetch only subset of the original dataset and group all elite together and non-elite together
	 * This function is called when the user selects specific years on the sketch
	 */
	public void filterData(Integer[] array) {
		Table data = transformedDataBackup.copy();
		Table data1 = transformedDataBackup.copy();
		data.clearRows();
		int[][] groupedIntData = new int[2][13];
		for(int i =0; i<groupedIntData.length; i++)
			for(int j=0; j<groupedIntData[i].length; j++)
				groupedIntData[i][j] = 0;
		for(int i:array) {
			TableRow record = data1.getRow(i);
			for(int j=2; j<=14; j++) {
				groupedIntData[record.getInt(1)][j-2] += record.getInt(j);
			}
		}
		for(int count: new int[]{0,1}){
		TableRow row = data.addRow();
		row.setInt(0, 9999);
		row.setInt(1, 0);
		if(count==1)
			row.setInt(1, 1);
		for(int j=2; j<=14; j++) {
			row.setInt(j, groupedIntData[count][j-2]);
		}}
		dataToPlot.clearRows();
		dataToPlot = data.copy();
		dataToPlot = transformData(dataToPlot, false);
		setMaxMinReviews();
	}
	
	/*
	 * Function to move the records in the dataset by one year up or down based of the direction.
	 * Its called when the mouse wheel is used.
	 * The dataset is sorted by user_creation_year. When the user uses the mouse wheel on the sketch,
	 * this function is called. If the mouse wheel is moved in positive direction, then the first year 
	 * record in the dataset is moved as the last record making the second year record to be the first one now.
	 * If the mouse wheel is moved in negative direction, then the last year record in the dataset is moved 
	 * as the fisrt record making the fisrt year record to be the second record now.
	 * direction parameter specifies whether mouse wheel is moved in positive or negative direction
	 * combined parameter specifies whether elite nonelite data are plotted separately or are merged and then plotted. 
	 */
	public void rollPlotData(float direction, boolean combined) {
		Table data = dataToPlot.copy();
		data.clearRows();
		if (direction > 0) { // positive direction
			int recordCount = 0;
			for (TableRow row : dataToPlot.rows()) {
				// If combined then only one record to more else 2 records as 
				//  elite and non-elite information is present in 2 records if not combined.
				if ((!combined && recordCount > 1) || (combined && recordCount>0)) {
					TableRow row1 = data.addRow();
					for (int i = 0; i < row.getColumnCount(); i++) {
						if (i < 15)
							row1.setInt(i, row.getInt(i));
						else
							row1.setFloat(i, row.getFloat(i));
					}
				}
				recordCount++;
			}
			int[] records;
			// if combined then one record else 2 records
			if(combined)
				records = new int[] {0};
			else
				records = new int[] {0,1};
			// moved records from front to end
			for (TableRow row : dataToPlot.rows(records)) {
				TableRow row1 = data.addRow();
				for (int i = 0; i < row.getColumnCount(); i++) {
					if (i < 15)
						row1.setInt(i, row.getInt(i));
					else
						row1.setFloat(i, row.getFloat(i));
				}
			}
		} else { // negative direction
			int[] records;
			// if combined then one record else 2 records
			if(combined)
				records = new int[] {dataToPlot.getRowCount() - 1 };
			else
				records = new int[] {dataToPlot.getRowCount() - 2, dataToPlot.getRowCount() - 1 };
			// move records from end to front
			for (TableRow row : dataToPlot.rows(records)) {
				TableRow row1 = data.addRow();
				for (int i = 0; i < row.getColumnCount(); i++) {
					if (i < 15)
						row1.setInt(i, row.getInt(i));
					else
						row1.setFloat(i, row.getFloat(i));
				}
			}
			int recordCount = 0;
			for (TableRow row : dataToPlot.rows()) {
				if (recordCount < dataToPlot.getRowCount() - records.length) {
					TableRow row1 = data.addRow();
					for (int i = 0; i < row.getColumnCount(); i++) {
						if (i < 15)
							row1.setInt(i, row.getInt(i));
						else
							row1.setFloat(i, row.getFloat(i));
					}
				}
				recordCount++;
			}
		}
		dataToPlot.clearRows();
		dataToPlot = data.copy();
		setMaxMinReviews();
	}

}
