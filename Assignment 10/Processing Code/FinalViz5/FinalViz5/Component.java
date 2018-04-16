/* From Verlet World Demo 
   By: Ira Greenberg
   Made for MSDS6390: Visualization of Information
   at Southern Methodist University*/
import processing.core.*;
import processing.data.*;

abstract class Component{
  PVector position;
  PVector dimension;
  String label;
  int labelCol, labelTextCol;
  int offState, onState, overState, pressState;
  int[] states = {
    offState, onState, overState, pressState
  };
  boolean hasBorder = false;
  boolean isSelected = false;
  int mouseClickCount = 0;
  
  Component(){
  }
  
  Component(PApplet object, PVector position, PVector dimension, String label, int[] states){
    this.position = position;
    this.dimension = dimension;
    this.label = label;
    labelCol = states[0];
    labelTextCol = 0xffffffff;
    offState = states[0];
    onState = states[1];
    overState = states[2];
    pressState = states[3];
//    System.out.println("States:" + states[0] + ":" + states[1] + ":" + states[2] + ":" + states[3]);
    this.states = states;
  }
  
  //concrete method
  void setHasBorder(boolean hasBorder){
    this.hasBorder = hasBorder;
  }
  
  //implement subclasses
  abstract boolean isHit(PApplet object);
  abstract void display(PApplet object);
}