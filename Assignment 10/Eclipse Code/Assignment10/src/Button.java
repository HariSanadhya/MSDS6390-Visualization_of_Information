/* From Verlet World Demo 
   By: Ira Greenberg
   Made for MSDS6390: Visualization of Information
   at Southern Methodist University
 */

import processing.core.*;
import processing.data.*;

/* From Verlet World Demo 
   By: Ira Greenberg
   Made for MSDS6390: Visualization of Information
   at Southern Methodist University*/

public class Button extends Component{
  PFont font;
  
  Button(){
  }
  
  Button(PApplet object, PVector position, PVector dimension, String label, int[] states){
    super(object, position, dimension, label, states);
    font = object.loadFont("ArialMT-22.vlw");
    object.textFont(font, 15);
  }
  
  boolean isHit(PApplet object){
    if (object.mouseX >= position.x && object.mouseX <= position.x + dimension.x &&
    		object.mouseY >= position.y && object.mouseY <= position.y + dimension.y){
        return true;
    }
    return false;
  }
  
  void display(PApplet object){
    if (hasBorder){
    	object.stroke(100);
    } else{
    	object.noStroke();
    }
    object.fill(labelCol);
    object.rect(position.x, position.y, dimension.x, dimension.y);
    
    object.fill(labelTextCol);
    float tw = object.textWidth(label);
    object.textAlign(object.LEFT, object.CENTER);
    object.text(label, position.x + (dimension.x-tw)/2.0f, position.y + dimension.y/2);
  }
}