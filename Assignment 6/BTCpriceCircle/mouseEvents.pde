void mousePressed() {
  if (a==null) {
    clickX=mouseX;
    clickY = mouseY;
    a= new PVector(mouseX- (width/2-100), mouseY-height/2.0);
  }
}

void mouseDragged() {
  pushMatrix();
  translate(width/2-100, height/2);
  b = new PVector(mouseX- (width/2-100), mouseY-height/2.0);
  rotationAngle =angle(a, b); 
  popMatrix();
  redraw();
  //println(degrees(rotationAngle), rotationAngle, round(13*7 - rotationAngle*13*7/TWO_PI- 2));
}

// returns the angle from v1 to v2 in clockwise direction
// range: [0..TWO_PI]
float angle(PVector v1, PVector v2) {
  float a = atan2(v2.y, v2.x) - atan2(v1.y, v1.x);
  if (a < 0) a += TWO_PI;
  return a;
}