package model;

import render.*;
import gui.*;
import storage.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Timeline.AxisLabel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javafx.scene.image.Image;

/**
 * TimelineMaker.java
 * 
 * The model of the timeline editor and viewer. Contains all necessary objects
 * to model the application.
 * 
 * @author Josh Wright and Andrew Thompson Wheaton College, CS 335, Spring 2014
 *         Project Phase 1 Feb 15, 2014
 * 
 */
public class TimelineMaker {
	/**
	 * A list of all the timelines in this application.
	 */
	private ArrayList<Timeline> timelines;
        private ArrayList<Icon> icons;
	/**
	 * The timeline selected in this application.
	 */
	private Timeline selectedTimeline;
	/**
	 * The event selected in this application.
	 */
	private TLEvent selectedEvent;
	/**
	 * The database for storing timelines of this application.
	 */
	private DBHelper database;
	/**
	 * The main GUI window for this application.
	 */
	private MainWindowController mainWindow;
	/**
	 * The graphics object for displaying timelines in this application.
	 */
	public TimelineGraphics graphics;

	/**
	 * Constructor. Create a new TimelineMaker application model with database,
	 * graphics, and GUI components.
	 */
	public TimelineMaker() {
		database = new DBHelper("timeline.db");
		graphics = new TimelineGraphics(this);
		timelines = new ArrayList<Timeline>();
                icons = new ArrayList<Icon>();
                icons.add(new Icon("None", null));
		try {
			for (Timeline t : database.getTimelines())
				timelines.add(t);
			HashMap<Category, String> categories = database.getCategories();
			for (Timeline t : timelines){ // Very lame. Should have better implementation but don't have time.
				for(Category c : categories.keySet() ){
					if(t.getName().equals(categories.get(c))){
						t.addCategory(c);
					}
				}
			}
			for(Timeline t : timelines){ // sets categories.
				for(TLEvent e : t.getEvents()){
					Category toSet = t.getCategory(e.getCategory().getName());
					if(toSet != null){
						e.setCategory(toSet);
					}
				}
			}
			selectedTimeline = timelines.get(0);
			selectedEvent = null;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Your database is empty.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading from Database.");
		}

	}

        public Icon getIcon(String t){
            for(Icon i: icons){
                if(i.getName().equals(t)) return i;
            }
            return icons.get(0);
        }
        
	public ArrayList<String> getImageTitles() {
            ArrayList<String> iconTitles = new ArrayList<String>();
            for(Icon i: icons){
                iconTitles.add(i.getName());
            }
            return iconTitles;
	}

        
        public void addIcon(Icon i){
            if(i != null) icons.add(i);
        }
        
	/**
	 * Retrieve a list of the names of all the timelines.
	 * 
	 * @return timelines
	 */
	public ArrayList<String> getTimelineTitles() {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (Timeline t : timelines) {
			toReturn.add(t.getName());
		}
		return toReturn;
	}

	/**
	 * Retrieve the timeline with the parameterized name.
	 * 
	 * @param title
	 *            The name of the timeline to be found
	 * @return The timeline with the correct name; null otherwise.
	 */
	private Timeline getTimeline(String title) {
		for (Timeline t : timelines)
			if (t.getName().equals(title))
				return t;
		return null;
	}

	public Timeline getSelectedTimeline() {
		return selectedTimeline;
	}

	/**
	 * Set the selected timeline. Find the timeline of the parameterized title
	 * and set selectedTimeline to it. Update selectedTimeline, selectedTLEvent,
	 * and graphics.
	 * 
	 * @param title
	 *            of the timeline
	 */
	public void selectTimeline(String title) {
		selectedTimeline = getTimeline(title);
		selectedEvent = null;
		if (selectedTimeline != null)
			updateGraphics();
	}

	/**
	 * Add a timeline to this model. Update selectedTimeline, selectedTLEvent,
	 * graphics, and database.
	 * 
	 * @param t
	 *            the timeline to be added
	 */
	public void addTimeline(String title, Color color, AxisLabel axisUnit,
			Font font) {
		Timeline t = new Timeline(title, axisUnit);
		selectedTimeline = t;
		selectedEvent = null;
		timelines.add(selectedTimeline);

		database.saveTimeline(selectedTimeline);
		mainWindow.populateListView();
		// gui.updateTimelines(getTimelineTitles(), selectedTimeline.getName());
		updateGraphics();
	}

	/**
	 * Remove a timeline from this model. Update selectedTimeline,
	 * selectedTLEvent, graphics, and database.
	 * 
	 * @param t
	 *            the timeline to be removed
	 */
	public void deleteTimeline() {
		if (selectedTimeline != null) {
			timelines.remove(selectedTimeline);
			database.removeTimeline(selectedTimeline);
			selectedTimeline = null;
			selectedEvent = null;
			graphics.clearScreen();
			mainWindow.populateListView();
		}
	}

	/**
	 * Edit the selected timeline. Remove the selected timeline and replace it
	 * with the parameterized one. Update selectedTimeline, selectedTLEvent,
	 * graphics, and database.
	 * 
	 * @param t
	 *            the new timeline
	 */
	public void editTimeline(Timeline t, String title, Color color,
			AxisLabel axisUnit, Font font) {
		timelines.remove(selectedTimeline);
		Timeline newTimeline = new Timeline(title, t.getEvents(), axisUnit);
		newTimeline.setID(t.getID());
		timelines.add(newTimeline);
		database.editTimelineInfo(newTimeline);
		selectedTimeline = newTimeline;
		mainWindow.populateListView();
		updateGraphics();
	}

	public void populateView() {
		mainWindow.populateListView();
	}

	/**
	 * Retrieve the currently selected event.
	 * 
	 * @return selectedTLEvent
	 */
	public TLEvent getSelectedEvent() {
		return selectedEvent;
	}

	/**
	 * Set the selected event.
	 * 
	 * @param e
	 *            The event to be selected
	 */
	public void selectEvent(TLEvent e) {
		if (e != null)
			selectedEvent = e;
	}

	/**
	 * Add an event to the selected timeline. Update selectedTimeline,
	 * selectedTLEvent, graphics, and database.
	 * 
	 * @param e
	 *            the new event
	 */
	
	public void addEvent(String title, Date startDate, Date endDate, Object category, String description, Icon icon) {
		TLEvent event;
		if (endDate != null) {
			event = new Duration(title, new Category(""), startDate, endDate);
		} else {
			event = new Atomic(title, new Category(""), startDate);
		}
                if(!icon.getName().equals("None")) event.setIcon(icon);
		if (selectedTimeline != null) {
			selectedTimeline.addEvent(event);
			selectedEvent = event;
			updateGraphics();
			database.saveEvent(event, selectedTimeline.getName());
		}
	}

	/**
	 * Delete the selected event from the timeline. Update selectedTimeline,
	 * selectedTLEvent, graphics, and database.
	 */
	public void deleteEvent() {
		if (selectedEvent != null && selectedTimeline != null
				&& selectedTimeline.contains(selectedEvent)) {
			selectedTimeline.removeEvent(selectedEvent);
			database.removeEvent(selectedEvent, selectedTimeline.getName());
			selectedEvent = null;
			updateGraphics();
		}
	}

	/**
	 * Edit the selected event. Remove the currently selected event from the
	 * timeline and replace it with the parameter. Update selectedTimeline,
	 * selectedTLEvent, graphics, and database.
	 * 
	 * @param e
	 *            the new event
	 */
	public void editEvent(TLEvent oldEvent, String title, Date startDate, Date endDate, Category category, String description, Icon icon) {
		if (selectedEvent != null && selectedTimeline != null && selectedTimeline.contains(selectedEvent)) {
			selectedTimeline.removeEvent(selectedEvent);
			TLEvent toAdd;
			if(endDate != null) toAdd = new Duration(title, category, startDate, endDate);
			else toAdd = new Atomic(title, category, startDate);
                        if(!icon.getName().equals("None")) toAdd.setIcon(icon);
			toAdd.setID(oldEvent.getID());
			selectedEvent = toAdd;
			selectedTimeline.addEvent(toAdd);
			toAdd.setCategory(category);
			updateGraphics();
			database.editEvent(toAdd, selectedTimeline.getName());
		}
	}

	/**
	 * Update the graphics for the display screen.
	 */
	public void updateGraphics() {
		graphics.clearScreen();
		graphics.renderTimeline(selectedTimeline);

	}

	/**
	 * @param mainWindow
	 *            the mainWindow to set
	 */
	public void setMainWindow(MainWindowController mainWindow) {
		this.mainWindow = mainWindow;
	}

	public int timeSize() {
		return timelines.size();
	}

	public void addCategory(Category category){
		database.saveCategory(category, selectedTimeline.getName());
	}
	public void deleteCategory(Category category){
		database.removeCategory(category, selectedTimeline.getName());
	}
	public void editCategory(Category category){
		database.editCategory(category, selectedTimeline.getName());
	}
}
