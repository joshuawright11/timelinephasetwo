package model;

import javafx.scene.control.Label;

/**
 * An abstract class to create labels for Atomic and Duration events to render.
 * Currently the subclasses have a decent amount of repetition so some of that could be
 * moved up here.
 * 
 * @author Josh Wright
 * February 15, 2014
 */
public abstract class TLEventLabel extends Label {
	
	/**
	 * Whether this is the selected event or not
	 */
	private boolean selected;
	
	/**
	 * Set the text of the label to text
	 * 
	 * @param text the label text
	 */
	TLEventLabel(String text){
		super(text);
	}

	/**
	 * Getter for selected
	 * 
	 * @return selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Setter for selected, that updates the label in accordance with the selection value
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		updateDesign();
	}

	/**
	 * How the label will update itself
	 */
	public abstract void updateDesign();
}
