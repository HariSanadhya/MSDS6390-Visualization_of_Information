import java.io.PrintWriter;

import processing.core.*;
import processing.data.*;

boolean expOccured = false;
String expText = "";
Engine e;
Chart c;
BarGraph bg;
PieChart pc;
LineGraph lg;
BoxPlot bp;


public void settings() {
  size(700, 700, P3D);
}

public void setup() {
  fill(120, 50, 240);
  noStroke();
    Chart[] cs = new Chart[4];
  
  cs[0] = new BarGraph();
  cs[1] = new PieChart();
  cs[2] = new LineGraph();
  cs[3] = new BoxPlot();
  TimeSeriesTable.sketchDataPath = dataPath("");
  print(dataPath(""));
  
  e = new Engine(cs);

  try {
    //Table[] dataFile = new TimeSeriesTable[9];
    //dataFile[0] = loadTable("file3.csv", "header", 0, 2, 1);
    //dataFile[1] = loadTable("file4.csv", "header");
    //dataFile[2] = loadTable("file3.csv", "header", 0, 2);
    //dataFile[3] = loadTable("file4.csv", "header", 0, 1);
    //dataFile[4] = loadTable("file2.csv", "header", 0, 2, 1);
    //dataFile[5] = loadTable("file2.csv", "header", 0, 2);
    //dataFile[6] = loadTable("file5.csv", "header", 0, 2, 1);
    //dataFile[7] = loadTable("file5.csv", "header", 0, 2);
    //dataFile[8] = loadTable("user_elite_review_final.csv", "header", 27, 26, 13);
    Table[] dataFile = new TimeSeriesTable[2];
    dataFile[0] = loadTable("file3.csv", "header", 0, 2, 1);
    dataFile[1] = loadTable("file4.csv", "header");
    for (int i = 0; i < dataFile.length; i++) {
      ((TimeSeriesTable) dataFile[i]).summarizeData("");
    }
  } 
  catch (Exception exp) {
    expOccured = true;
    expText = exp.getMessage();
  }
  if (expOccured)
    noLoop();
}

public void draw() {
  if (expOccured) {
    textAlign(CENTER, CENTER);
    text(expText, width / 2, height / 2, 0);
  } else {
    translate(width / 2, height / 2);
    smooth();
    lights();
    sphere(second());
  }
}

public TimeSeriesTable loadTable(String fileName) throws InvalidTableException {
  Table temp = super.loadTable(fileName);
  TimeSeriesTable temp1 = new TimeSeriesTable();
  try {
    temp1.addColumn(temp.getColumnTitle(0), temp.getColumnType(0));
    temp1.addColumn(temp.getColumnTitle(1), temp.getColumnType(1));
    temp1.addColumn(temp.getColumnTitle(2), temp.getColumnType(2));
    for (TableRow c : temp.rows()) {
      if (c.getInt(1) == 0)
        continue;
      TableRow row = temp1.addRow();
      row.setString(0, c.getString(0));
      row.setInt(1, c.getInt(1));
      row.setString(2, c.getString(2));
    }
  } 
  catch (Exception E) {
    throw new InvalidTableException();
  }
  return temp1;
}

public TimeSeriesTable loadTable(String fileName, String options) throws InvalidTableException {
  Table temp = super.loadTable(fileName, options);
  TimeSeriesTable temp1 = new TimeSeriesTable();
  try {
    temp1.addColumn(temp.getColumnTitle(0), temp.getColumnType(0));
    temp1.addColumn(temp.getColumnTitle(1), temp.getColumnType(1));
    temp1.addColumn(temp.getColumnTitle(2), temp.getColumnType(2));
    for (TableRow c : temp.rows()) {
      if (c.getInt(1) == 0)
        continue;
      TableRow row = temp1.addRow();
      row.setString(0, c.getString(0));
      row.setInt(1, c.getInt(1));
      row.setString(2, c.getString(2));
    }
  } 
  catch (Exception E) {
    throw new InvalidTableException();
  }
  return temp1;
}

public TimeSeriesTable loadTable(String fileName, int indexCategory, int indexValue) throws InvalidTableException {
  Table temp = super.loadTable(fileName);
  TimeSeriesTable temp1 = new TimeSeriesTable();
  try {
    temp1.addColumn(temp.getColumnTitle(indexCategory), temp.getColumnType(indexCategory));
    temp1.addColumn(temp.getColumnTitle(indexValue), temp.getColumnType(indexValue));
    for (TableRow c : temp.rows()) {
      if (c.getInt(indexValue) == 0)
        continue;
      TableRow row = temp1.addRow();
      row.setString(0, c.getString(indexCategory));
      row.setInt(1, c.getInt(indexValue));
    }
  } 
  catch (Exception E) {
    throw new InvalidTableException();
  }
  return temp1;
}

public TimeSeriesTable loadTable(String fileName, String options, int indexCategory, int indexValue)
  throws InvalidTableException {
  Table temp = super.loadTable(fileName, options);
  TimeSeriesTable temp1 = new TimeSeriesTable();
  try {
    temp1.addColumn(temp.getColumnTitle(indexCategory), temp.getColumnType(indexCategory));
    temp1.addColumn(temp.getColumnTitle(indexValue), temp.getColumnType(indexValue));
    for (TableRow c : temp.rows()) {
      if (c.getInt(indexValue) == 0)
        continue;
      TableRow row = temp1.addRow();
      row.setString(0, c.getString(indexCategory));
      row.setInt(1, c.getInt(indexValue));
    }
  } 
  catch (Exception E) {
    throw new InvalidTableException();
  }
  return temp1;
}

public TimeSeriesTable loadTable(String fileName, int indexCategory, int indexValue, int indexHue)
  throws InvalidTableException {
  Table temp = super.loadTable(fileName);
  TimeSeriesTable temp1 = new TimeSeriesTable();
  try {
    temp1.addColumn(temp.getColumnTitle(indexCategory), temp.getColumnType(indexCategory));
    temp1.addColumn(temp.getColumnTitle(indexValue), temp.getColumnType(indexValue));
    temp1.addColumn(temp.getColumnTitle(indexHue), temp.getColumnType(indexHue));
    for (TableRow c : temp.rows(new int[] { indexCategory, indexValue, indexHue })) {
      if (c.getInt(indexValue) == 0)
        continue;
      temp1.addRow(c);
    }
  } 
  catch (Exception E) {
    throw new InvalidTableException();
  }
  return temp1;
}

public TimeSeriesTable loadTable(String fileName, String options, int indexCategory, int indexValue, int indexHue)
  throws InvalidTableException {
  Table temp = super.loadTable(fileName, options);
  TimeSeriesTable temp1 = new TimeSeriesTable();
  try {
    temp1.addColumn(temp.getColumnTitle(indexCategory), temp.getColumnType(indexCategory));
    temp1.addColumn(temp.getColumnTitle(indexValue), temp.getColumnType(indexValue));
    temp1.addColumn(temp.getColumnTitle(indexHue), temp.getColumnType(indexHue));
    for (TableRow c : temp.rows()) {
      if (c.getInt(indexValue) == 0)
        continue;
      TableRow row = temp1.addRow();
      row.setString(0, c.getString(indexCategory));
      row.setInt(1, c.getInt(indexValue));
      row.setString(2, c.getString(indexHue));
    }
  } 
  catch (Exception E) {
    throw new InvalidTableException();
  }
  return temp1;
}
