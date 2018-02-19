// Function to display static text on the sides
void textOnSide() {
  textAlign(LEFT);
  textSize(15);
  text("ASSIGNMENT 6: BTC Price", 710, 20);
  text("Team: Jack, Hari, Ireti", 710, 40);

  text("Legend", 710, 80);
  textSize(13);
  fill(35, 95, 15);
  text("Highest price reached in the month", 710, 100);
  fill(110, 0, 50);
  text("Lowest price reached in the month", 710, 115);
  fill(50, 10, 255);
  text("Opening price for the month", 710, 130);
  fill(164, 42, 42);
  text("Closing proce of the month", 710, 145);
  fill(0);
  text("* NOTE: Data plotted on log10 scale", 710, 170);
  text(findRcdIndexBeneathPriceScale(), 710, 220);
}

//function to get the data to be displayed on the side corresponding to the selected month from the graph
String findRcdIndexBeneathPriceScale() {
  int index = -1;
  index = round((13*7-2) - (rotationAngle+ theta*3)*(13*7-2)/TWO_PI + 1);
  return displayRecordData(index);
}

String displayRecordData(int index) {
  if (index>0 && index<indexToExclude[0])
    return getData(index -1);
  else if (index>indexToExclude[0] && index<indexToExclude[1])
    return getData(index -2);
  else if (index>indexToExclude[1] && index<indexToExclude[2])
    return getData(index -3);
  else if (index>indexToExclude[2] && index<indexToExclude[3])
    return getData(index -4);
  else if (index>indexToExclude[3] && index<indexToExclude[4])
    return getData(index -5);
  else if (index>indexToExclude[4] && index<indexToExclude[5])
    return getData(index -6);
  else if (index>indexToExclude[5] && index<indexToExclude[6])
    return getData(index -7);
  else if (index>indexToExclude[6])
    return getData(index -8);
  return "";
}

String getData(int index) {
  //println("index: ", index, btc.time_period_start.size());
  if (index < btc.price_high.length) {
    return ("time_period_start: \n" + btc.time_period_start.get(index) + "\ntime_period_end: \n" + btc.time_period_end.get(index) + "\ntime_open: \n" + btc.time_open.get(index) + "\ntime_close: \n" + 
      btc.time_close.get(index) + "\nprice_open: \n" + btc.price_open[index] + "\nprice_high: \n" + btc.price_high[index] + "\nprice_low: \n" + btc.price_low[index] + "\nprice_close: \n" + 
      btc.price_close[index] + "\nvolume_traded: \n" + btc.volume_traded[index] + "\ntrades_count: \n" + btc.trades_count[index]);
  }
  return "Click and Drag the image to view \n monthly data";
}