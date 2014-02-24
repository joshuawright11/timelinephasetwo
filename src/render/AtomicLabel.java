package render;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.TimelineMaker;
import model.Atomic;

/**
 * Atomic version of TLEventLabel
 * 
 * @author Josh Wright
 * February 15, 2014
 */
public class AtomicLabel extends TLEventLabel {
	
	/**
	 * The event this label is associated with
	 */
	private Atomic event;
	
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
	private AtomicLabel label;
	
	/**
	 * ArrayList of all other eventLabels, used for clearing previous selection
	 */
	private ArrayList<TLEventLabel> eventLabels;
	
	/**
	 * Constructor calls the super constructor with the event name, assigns instance variables,
	 * and then calls init
	 * 
	 * @param event the event this label is associated with
	 * @param xPos the xPosition on the screen
	 * @param yPos the yPosition on the screen
	 * @param model the program model
	 * @param eventLabels the list of TLEventLabels
	 */
	AtomicLabel(Atomic event, int xPos, int yPos, TimelineMaker model, ArrayList<TLEventLabel> eventLabels){
		super(event.getName());
		this.event = event;
		this.xPos = xPos;
		this.yPos = yPos;
		this.eventLabels = eventLabels;
		this.label = this;
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
		label.setLayoutX(xPos);
		label.setLayoutY(yPos);
		label.setStyle("-fx-border-color: green");
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
			label.setStyle("-fx-border-color: green");
		}
	}

}
