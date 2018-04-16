DataImport importObject; // Object of DataImport class
// Cylinder objects to create textured and colored cylinders 
Cylinder textured;
Cylinder coloured;

float initialAngle = 0; 
float angularSpeed = PI / 500;
float canvasX = 700, canvasY = 700; // 3D shapes will be created within this region
boolean combined = false; // Variable that stores whether to display elite and non-elite together or separately

Menu menu1, menu2; // Two menus used

int bgCol = 0xff113326; // BackGround Color

// Function that performs all the initialization required for the sketch
public void setup() {
  int[] states = { 0xff66bf99, bgCol, 0xffeeffef, 0xffffaa66 }; //States(colors) used within the menus
  // offState, onState, overState, pressState

  // Instantiate the Cylinder class object
  coloured = new Cylinder(this);
  textured = new Cylinder(this, "https://img00.deviantart.net/c00a/i/2013/020/7/e/w__disney_texture_stock_by_redwolf518stock-d5s70kx.jpg");

  // Instantiate the Data Import Object, try to read from MySQL as the datastore.
  importObject = new DataImport(this, true);

  // Instantiate menus, First menu is based of the data to to plot, second menu have a single button - labeled Combine 
  menu1 = new Menu(this, importObject.dataToPlot, new PVector(180, 650), states, new PVector(10, 120));
  menu2 = new Menu(this, new String[] { "Combine" }, new PVector(180, 50), states, new PVector(10, 30));
}

public void settings() {
  // Sketch Dimension
  size(1100, 800, P3D);
}

public void draw() {
  noStroke();
  lights();
  background(bgCol);
  includeText(); // Add the sketch label and heading
  plotData(); // Call function to plot the data
  menu2.display(this); // display menu2
  menu1.display(this); // display menu1
}

private void plotData() {
  boolean menu1Status = menu1.isMenuStatusChanged(); // Check if menu1 status is changed
  boolean menu2Status = menu2.isMenuStatusChanged(); // check if menu2 status is changed

  boolean menu1NoneSelected = menu1.isMenuNoSelected(); // check if none of the buttons on menu1 is selected
  Boolean[] status = menu1.getSelected(); // Get the selection status of all the buttons on menu1

  // Since the data is sorted by user_creation_year and corresponding to each year, we have two records,
  //  check the buttons selected in menu1 and get the record number corresponding to those years in 
  //  the ArrayList
  ArrayList<Integer> recordCorrespToSelection = new ArrayList<Integer>();
  for (int i = 0; i < status.length; i++) {
    if (status[i]) {
      recordCorrespToSelection.add(2 * i);
      recordCorrespToSelection.add(2 * i + 1);
    }
  }

  // If any record found, then call the filter function that will summarize the records for the selected years
  //  which will be the new dataset that will be plotted
  if (recordCorrespToSelection.size() > 0)
    importObject.filterData(recordCorrespToSelection.toArray(new Integer[0]));
  else
    // reload the dataset if the menu2Status is changed or if menu1Status is changed and none of the buttons in
    //   menu1 is selected.
    if ((menu1NoneSelected && menu1Status) || menu2Status)
      importObject.restoreData();

  // if menu2 button is selected, then draw combined data else draw splitted data
  if (menu2.getSelected()[0]) {
    combined = true;
    // Combine the elite and non-elite records for individual years only when 
    // menu2Status is changed or none of menu1 buttons is selected
    if (menu2Status || !menu1NoneSelected)
      importObject.combineData();
    drawCombined();
  } else {
    combined = false;
    drawSplitted();
  }
}

// Function to plot the combined data
private void drawCombined() {
  int recordCount = 0;

  for (TableRow row : importObject.dataToPlot.rows()) {
    // Rotation speed adjustment
    if (recordCount == 0) {
      initialAngle += angularSpeed;
      if (initialAngle > 0.2 * QUARTER_PI || initialAngle < -0.5 * QUARTER_PI)
        angularSpeed = -angularSpeed;
    }

    // Add label having user creation year
    fill(0xffcc9977);
    if (row.getInt(0) != 9999)
      text(row.getString(0), 200, canvasY * 0.3f + recordCount * 100, -(recordCount * 400));

    // Plot the data
    for (int i = 2; i <= 14; i++) {
      pushMatrix();
      float rad = map(row.getInt(i + 13), importObject.minReviews, importObject.maxReviews, 0, 27);
      translate(300 + ((i - 1) * 56), canvasY * 0.3f + recordCount * 100, -(recordCount * 400));

      // Rotation of the front record
      if (recordCount == 0) {
        rotateY(initialAngle);
        rotateX(initialAngle * 2);
        rotateZ(-initialAngle);
      }
      coloured.drawCylinder(this, rad, rad * 10, color(25, 100, 100), Direction.POSITIVE);

      // Add year above the cylinder
      fill(0xff99aacc);
      text(row.getColumnTitle(i + 13).substring(row.getColumnTitle(i + 13).length() - 4), 0 - 15, 0 - 5, 0);

      // Add the physical value beneath the cylinder
      if (recordCount == 0 && !row.getString(i).equals("0"))
        text(row.getString(i), 0 - 15, rad * 10 + 15, 0);

      popMatrix();
    }
    recordCount++;
  }
}

// Function to plot the splitted data
private void drawSplitted() {
  int recordCount = 0;
  for (TableRow row : importObject.dataToPlot.rows()) {

    // Add label showing the user creation uear and user type (elite/non-elite)
    if (row.getInt(0) != 9999) {
      if (row.getInt(1) == 0) {
        fill(0xffcc9977);
        text(row.getString(0) + "\nNon-Elite", 200, canvasY * 0.1f, -(recordCount * 400));
      } else {
        fill(0xffcc9977);
        text(row.getString(0) + "\nElite", 200, canvasY, -((recordCount - 1) * 400));
      }
    }

    // Rotation speed adjustment
    if (recordCount == 0) {
      initialAngle += angularSpeed;
      if (initialAngle > 0.2 * QUARTER_PI || initialAngle < -0.5 * QUARTER_PI)
        angularSpeed = -angularSpeed;
    }

    // read the data to plot
    for (int i = 2; i <= 14; i++) {
      pushMatrix();
      float rad = map(row.getInt(i + 13), importObject.minReviews, importObject.maxReviews, 0, 27);

      if (row.getInt(1) == 0) { // non-elite user
        translate(300 + ((i - 2) * 56), canvasY * 0.1f, -(recordCount * 400));

        // Rotation of the front record
        if (recordCount <= 1) {
          rotateY(initialAngle);
          rotateX(initialAngle * 2);
          rotateZ(-initialAngle);
        }
        coloured.drawCylinder(this, rad, rad * 8, color(100, 100, 0), Direction.POSITIVE);

        // Add year above the cylinder
        fill(0xff99aacc);
        text(row.getColumnTitle(i + 13).substring(row.getColumnTitle(i + 13).length() - 4), 0 - 15, 0 - 5, 0);

        // Add the physical value beneath the cylinder
        if (recordCount <= 1 && !row.getString(i).equals("0"))
          text(row.getString(i), 0 - 15, rad * 8 + 15, 0);
      } else { // elite user
        translate(300 + ((i - 2) * 56), canvasY, -((recordCount - 1) * 400));

        // Rotation of the front record
        if (recordCount <= 1) {
          rotateY(initialAngle - PI);
          rotateX(initialAngle * 2);
          rotateZ(-initialAngle);
        }
        textured.drawCylinder(this, rad, rad * 8, Direction.NEGATIVE);

        // Add the physical value on the top of the cylinder
        fill(0xff99aacc);
        if (recordCount <= 1) {
          rotateY(PI);
          if (!row.getString(i).equals("0"))
            text(row.getString(i), 0 - 15, -rad * 8 - 5, 0);
        }
        // Add year beneath the cylinder
        text(row.getColumnTitle(i + 13).substring(row.getColumnTitle(i + 13).length() - 4), 0 - 15, 0 + 15, 0);
      }
      popMatrix();
    }
    recordCount++;
  }
}

// Function to add labels in the chart
public void includeText() {
  fill(255);
  textSize(30);
  text("Assignment 10 - 3D plot", width/2 - 100, 20, 0);
  textSize(20);
  text("Submitted By: Jack, Ireti and Hari", width-300, 770, 0);
  textSize(18);
  fill(0xff99aacc);
  text("Number of reviews by Year  ------->", width/2 - 100, 740, 0);
  if (menu1.isMenuNoSelected()) {
    pushMatrix();
    textSize(20);
    translate(150, canvasY/2-100, 0);
    rotateZ(QUARTER_PI * 0.9f);
    rotateY(QUARTER_PI);
    rotateX(QUARTER_PI/8);
    fill(0xffcc9977);
    text("User Creation Year  ------->", 0, 0, 0);
    popMatrix();
  }
  textSize(15);
}

// Function to capture the mouse wheel usage event
public void mouseWheel(MouseEvent event) {
  float e = event.getCount();
  // Adjust the plotting data as per the mouse wheel direction
  importObject.rollPlotData(e, combined);
}

// Function to check the mouse click event 
public void mouseClicked() {
  // Check if the click was performed on any button in menu1
  for (int i = 0; i < menu1.buttons.length; i++) {
    if (menu1.buttons[i].isHit(this)) {
      menu1.buttons[i].labelCol = menu1.states[3];
      menu1.buttons[i].labelTextCol = 0xff766676;
      if (menu1.buttons[i].isSelected) {
        menu1.buttons[i].isSelected = false;
      } else {
        menu1.buttons[i].isSelected = true;
      }
    }
  }

  // Check if the click was performed on any button in menu2
  for (int i = 0; i < menu2.buttons.length; i++) {
    if (menu2.buttons[i].isHit(this)) {
      menu2.buttons[i].labelCol = menu2.states[3];
      menu2.buttons[i].labelTextCol = 0xff766676;
      if (menu2.buttons[i].isSelected) {
        menu2.buttons[i].isSelected = false;
      } else {
        menu2.buttons[i].isSelected = true;
      }
    }
  }
}
