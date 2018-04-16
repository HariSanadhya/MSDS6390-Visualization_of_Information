/* From Verlet World Demo 
   By: Ira Greenberg
   Made for MSDS6390: Visualization of Information
   at Southern Methodist University
   Customized it to have menu start at a specific position and 
   buttons within the menu to be operated independently so that at a single time,
   more then one button can be active (pressed)
 */

import java.util.*;
import processing.core.*;
import processing.data.*;

class Menu {
	// Table dataToPlot;
	PVector dimension;
	PVector startPos;
	Set<String> labels = new LinkedHashSet<String>();
	int[] states;
	Component[] buttons;
	private Boolean[] lastRunSelectedStatus;

	Menu() {
	}

	Menu(PApplet object, String[] labels, PVector dimension, int[] states, PVector startPos) {
		// this.dataToPlot = dataToPlot;
		this.dimension = dimension;
		this.states = states;
		this.startPos = startPos;
		for (String label : labels) {
			this.labels.add(label);
		}

		_init(object);
	}

	Menu(PApplet object, Table dataToPlot, PVector dimension, int[] states, PVector startPos) {
		// this.dataToPlot = dataToPlot;
		this.dimension = dimension;
		this.states = states;
		this.startPos = startPos;

		_init(object, dataToPlot);
	}

	void _init(PApplet object, Table dataToPlot) {
		for (TableRow row : dataToPlot.rows()) {
			labels.add(row.getString(0));
		}
		_init(object);
	}

	void _init(PApplet object) {
		buttons = new Component[labels.size()];

		float btnW, btnH;
		btnW = dimension.x / 2;
		btnH = dimension.y / buttons.length;
		int i = 0;

		for (String label : labels) {
			PVector pos, dim;
			pos = new PVector(startPos.x, startPos.y + btnH * i);
			dim = new PVector(btnW, btnH);
			buttons[i] = new Button(object, pos, dim, label, states);
			i++;
		}
		lastRunSelectedStatus = new Boolean[labels.size()];
		for (int j = 0; j < labels.size(); j++)
			lastRunSelectedStatus[j] = false;
	}

	void display(PApplet object) {
		object.textSize(16);
		// object.println("Buttons: ", buttons.length);
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].display(object);
		}

		createMenuEvents(object);
	}
	
	/*
	 * Method that decides the state (color) of the button.
	 * If the button is not selected and mouse is not over the button - state[0]
	 * If the button is selected and mouse is no more over the button - state[1]
	 * If the button is selected with the mouse over the button - state[2]
	 * If the button is not selected and mouse is over the button - state[3]
	 */
	void createMenuEvents(PApplet object) {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].isHit(object)) {
					if (!buttons[i].isSelected) {
						buttons[i].labelCol = states[2];
						buttons[i].labelTextCol = 0xff766676;
					} else {
						buttons[i].labelCol = states[3];
						buttons[i].labelTextCol = 0xff766676;
					}
			} else {
				buttons[i].labelTextCol = 0xffffffff;
				if (!buttons[i].isSelected) {
					buttons[i].labelCol = states[0];
				} else
					buttons[i].labelCol = states[1];
			}
		}
	}
	
	/*
	 * Function that compares the previous state of all the buttons on the menu with the current state 
	 * and return true is there is a change in the state
	 */
	public boolean isMenuStatusChanged() {
		boolean statusChanged = false;
		for (int i = 0; i < lastRunSelectedStatus.length; i++) {
			if (!statusChanged)
				statusChanged = !(lastRunSelectedStatus[i] == buttons[i].isSelected);
			lastRunSelectedStatus[i] = buttons[i].isSelected;
		}
		return statusChanged;
	}
	
	/*
	 * function that returns whether the specific button is selected or not
	 */
	void select(int isSelectedID) {
		for (int i = 0; i < buttons.length; i++) {
			if (i == isSelectedID) {
				buttons[i].isSelected = true;
			} else {
				buttons[i].isSelected = false;
			}
		}
	}
	
	/*
	 * Function that returns an array of boolean values specifying the selection status of all the buttons in a menu
	 */
	Boolean[] getSelected() {
		Boolean[] isSelected = new Boolean[buttons.length];
		for (int i = 0; i < buttons.length; i++) {
			isSelected[i] = buttons[i].isSelected;
		}
		return isSelected;
	}
	
	/*
	 * Function to return if none of the buttons on the entire menu is selected
	 * returns true if none selected otherwise false
	 */
	public boolean isMenuNoSelected() {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].isSelected)
				return false;
		}
		return true;
	}

}