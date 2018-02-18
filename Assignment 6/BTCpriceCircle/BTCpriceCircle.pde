// Assignment 6: CryptoCurrency Data Display 
// Team Members: Hari Narayan Sanadhya, Ireti Fasere, Jack Nelson


int outerR; //radius of outer circle
int innerR; //radius of inner circle
float theta; //angle of rotation for each month
int numMonths; //number of months in data
int numNewYears; //number of Dec-Jan crossings
int startMonth; //numberic month number of first month
PGraphics graphic;
PImage image;
float clickX, clickY;
float rotationAngle = 0;
PVector a, b;
int[] indexToExclude = {5, 18, 31, 44, 57, 70, 83};
Data btc; //object from the Data class housing cryptocurrency price data

void setup() {
  smooth();
  size(1000, 700);
  //initialize chart parameters
  outerR = round(height/2 * 0.9);
  innerR = round(height/2 * 0.2);
  numMonths = 77;
  numNewYears = 7;
  startMonth = 9;

  theta = TWO_PI / (numMonths + numNewYears + 5);   //leaving gap for y axis labels
  try {
    btc = new Data("BTC_data2.txt");
  } 
  catch(Exception obj) {
    print("Error reading the data file: ", obj.getMessage());
    System.exit(0);
  }

  noLoop();
}

void draw() {
  try {
    background(180, 190, 180);
    fill(0);
    textOnSide();
    translate(width/2-150, height/2);
    pushMatrix();
    monthLabels(rotationAngle); //plots month labels
    popMatrix();

    pushMatrix();
    yearLabels(rotationAngle); //plots year labels
    popMatrix();
    //print(btc.price_high.length);
    
    //plotting price variables from Data object
    pushMatrix();
    plotPrice(btc.price_high, color(35, 95, 15), rotationAngle); //green
    popMatrix();
    pushMatrix();
    plotPrice(btc.price_low, color(110, 0, 50), rotationAngle); //red
    popMatrix();
    pushMatrix();
    plotPrice(btc.price_open, color(50, 10, 255), rotationAngle); //blue
    popMatrix();
    pushMatrix();
    plotPrice(btc.price_close, color(164, 42, 42), rotationAngle); //brown
    popMatrix();
    pushMatrix();
    priceScale(); //plots y axis scale
    popMatrix();
  } 
  catch(Exception E) {
  }
}