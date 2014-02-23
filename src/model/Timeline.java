/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Timeline.java
 * 
 * Timeline object to keep track of the different timelines in the project. Contains a name, ArrayList of TLEvents, AxisLabel (for rendering), 
 * and boolean, dirty, which is updated whenever the Timeline is changed. This can be used for deciding when to sync to the database, but is
 * currently not in use (we sync whenever certain buttons in the GUI are pressed). 
 * 
 * @author Josh Wright and Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 1
 * Feb 15, 2014
 */
public class Timeline implements TimelineAPI{
	
	/**
	 * ArrayList to keep track of the events in the timeline
	 */
	private ArrayList<TLEvent> events;
	
	/**
	 * Name of the timeline
	 */
	private String name;
	
	/**
	 * enum for keeping track of the potential units to render the timeline in
	 * currently only DAYS, MONTHS, and YEARS work, but implementing the others would be very simple
	 */
	public static enum AxisLabel {
		DAYS, WEEKS, MONTHS, YEARS, DECADES, CENTURIES, MILLENNIA;
	}
	
	/**
	 * Array of the AxisLabels, for getting the value based on an index
	 */
	private static final AxisLabel[] AXIS_LABELS = { AxisLabel.DAYS, AxisLabel.WEEKS, AxisLabel.MONTHS, AxisLabel.YEARS, AxisLabel.DECADES, AxisLabel.CENTURIES, AxisLabel.MILLENNIA};
	
	/**
	 * The units to render the timeline in
	 */
	private AxisLabel axisLabel;
	
	/**
	 * whether the timeline has been changed since its last database sync
	 */
	private boolean dirty;
	
	/**
	 * Constructor
	 * 
	 * @param name Timeline name
	 */
	public Timeline(String name){
		this.name = name;
		events = new ArrayList<TLEvent>();
		axisLabel = AxisLabel.YEARS;
		setDirty(true);
	}
	
	/**
	 * Constructor for name and events
	 * Sets axisLabel to YEARS by default
	 * 
	 * @param name Timeline name
	 * @param events Events in timeline
	 */
	public Timeline(String name, TLEvent[] events){
		this.name = name;
		this.events = new ArrayList<TLEvent>(Arrays.asList(events));
		axisLabel = AxisLabel.YEARS;
		setDirty(true);
	}
	
	/**
	 * Constructor for name and axisLabel
	 * 
	 * @param name Timeline name
	 * @param axisLabel Unit to render timeline in
	 */
	public Timeline(String name, int axisLabel) {
		this.name = name;
		events = new ArrayList<TLEvent>();
		this.axisLabel = AXIS_LABELS[axisLabel];
		this.events = new ArrayList<TLEvent>();
		dirty = true;
	}
	
	/**
	 * Constructor for name, events, and axisLabel
	 * 
	 * @param name Timeline name
	 * @param events Events in timeline
	 * @param axisLabel Unit to render timeline in
	 */
	public Timeline(String name, TLEvent[] events, int axisLabel) {
		this.name = name;
		if(events != null)
			this.events = new ArrayList<TLEvent>(Arrays.asList(events));
		else
			this.events = new ArrayList<TLEvent>();
		this.axisLabel = AXIS_LABELS[axisLabel];
		dirty = true;
	}
	
	@Override
	public boolean contains(TLEvent event) {
		for (TLEvent e : events)
			if (e.equals(event))
				return true;
		return false;
	}

	@Override
	public void addEvent(TLEvent event) {
		setDirty(true);
		events.add(event);
	}

	@Override
	public boolean removeEvent(TLEvent event) {
		if(events.contains(event)){
			events.remove(event);
			setDirty(true);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean changeEvent(TLEvent oldEvent, TLEvent newEvent) {
		if(events.contains(oldEvent)){
			events.remove(oldEvent);
			events.add(newEvent);
			setDirty(true);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public TLEvent[] getEvents() {
		if(events.isEmpty()) return null;
		return (TLEvent[])events.toArray(new TLEvent[events.size()]);
	}
	
	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getAxisLabelIndex() { 
		for (int i = 0; i < AXIS_LABELS.length; i++)
			if (AXIS_LABELS[i] == axisLabel)
				return i;
		return -1;
	}
	
	@Override
	public AxisLabel getAxisLabel() {
		return axisLabel;
	}
}
