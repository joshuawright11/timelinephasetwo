/**
 * 
 */
package model;

import java.util.ArrayList;

import model.TimelineMaker;
import model.Duration;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;

/**
 * @author josh
 *
 */
public class DurationLabel extends TLEventLabel {
	/**
	 * The event this label is associated with
	 */
	private Duration event;
	
	/**
	 * The x and y position of this event
	 */
	private int xPos;
	private int yPos;

	/**
	 * The model of the program to update selected event
	 */
	private TimelineMaker model;
	
	/**
	 * This object. Used for passing to anonymous inner classes.
	 */
	private DurationLabel label;
	
	/**
	 * ArrayList of all other eventLabels, used for clearing previous selection
	 */
	private ArrayList<TLEventLabel> eventLabels;
	
	
	/**
	 * The width in pixels of the label
	 */
	private int width;
	
	/**
	 * Constructor calls the super constructor with the event name, assigns instance variables,
	 * and then calls init
	 * 
	 * @param event the event this label is associated with
	 * @param xPos the xPosition on the screen
	 * @param yPos the yPosition on the screen
	 * @param width the width of the label
	 * @param model the program model
	 * @param eventLabels the list of TLEventLabels
	 */
	public DurationLabel(Duration event, int xPos, int yPos, int width, TimelineMaker model, ArrayList<TLEventLabel> eventLabels){
		super(event.getName());
		this.event = event;
		this.xPos = xPos;
		this.yPos = yPos;
		this.eventLabels = eventLabels;
		this.label = this;
		this.width = width;
		this.model = model;
		init();
	}
	
	/**
	 * Calls two other init helper methods for cleanliness
	 */
	private void init() {
		initDesign();
		initHandlers();
	}

	/**
	 * Sets up the "design" of the label. Border, position, etc.
	 */
	private void initDesign(){
		label.setPrefWidth(width);
		label.setAlignment(Pos.CENTER);
		label.setLayoutX(xPos);
		label.setLayoutY(yPos);
		label.setStyle("-fx-border-color: blue");
	}
	
	/**
	 * Initializes the various handlers of the label
	 */
	private void initHandlers(){
		label.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				for(TLEventLabel label : eventLabels){
					label.setSelected(false);
				}
				setSelected(true);
				new Thread(new Runnable() {
					public void run() {
						model.selectEvent(event);
					}
				}).start();
			}
		});
	}

	@Override
	public void updateDesign() {
		if (isSelected()) {
			label.setStyle("-fx-border-color: black");
		}else{	
			label.setStyle("-fx-border-color: blue");
		}
	}
}
