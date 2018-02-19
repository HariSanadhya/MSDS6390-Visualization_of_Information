
void monthLabels(float rotationAngle) {    
  int index=getHighlightIndex();
  String[] months = {" ", "J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"};
  textSize(10);
  stroke(0);
  fill(0, 0, 0);
  textAlign(CENTER);
  rotate(theta*2+rotationAngle); //rotate to starting point

  for (int i = 0; i < numMonths + numNewYears; i++) {
    strokeWeight(1.0);
    rotate(theta);
    text(months[(i+startMonth) % 13], 0, -outerR-(height/2*0.02)); //draw month label with buffer
    // Highlight the label which is beneath the price scale
    if(i==index){
      strokeWeight(3.0);
      stroke(255, 200, 50);
    }
    else{
      strokeWeight(1.0);
      stroke(0);
    }
    if (months[(i+startMonth) % 13] == " ") { //draw thicker line between years
      strokeWeight(2.0);
      line(0, round(-innerR), 0, round(-height/2*0.95));
    } else { //draw month line
      line(0, round(-innerR), 0, round(-outerR + textAscent()/2));
    }
  }
}

int getHighlightIndex() {
  int index = -1;
  index = round((13*7-2) - (rotationAngle+ theta*3)*(13*7-2)/TWO_PI);
  return index;
}