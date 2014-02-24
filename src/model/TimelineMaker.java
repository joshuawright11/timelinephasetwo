package model;

import render.*;
import gui.*;
import storage.*;
import javax.swing.*;
import java.util.*;
import java.util.logging.*;

/**
 * TimelineMaker.java
 * 
 * The model of the timeline editor and viewer. Contains all necessary objects to model the application.
 * 
 * @author Josh Wright and Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 1
 * Feb 15, 2014
 *
 */
public class TimelineMaker {
	/**
	 * A list of all the timelines in this application.
	 */
	private ArrayList<Timeline> timelines;
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
	private MainWindow gui;
	/**
	 * The graphics object for displaying timelines in this application.
	 */
	private TimelineGraphics graphics;

	/**
	 * Constructor.
	 * Create a new TimelineMaker application model with database, graphics, and GUI components.
	 */
	public TimelineMaker() {
		database = new DBHelper("timeline.db");
		graphics = new TimelineGraphics(this);
		timelines = new ArrayList<Timeline>();

		try {
			for (Timeline t : database.getTimelines())
				timelines.add(t);
			selectedTimeline = timelines.get(0);
			selectedEvent = selectedTimeline.getEvents()[0];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Your database is empty.");
		} catch (Exception e){
			System.out.println("Error loading from Database.");
		}

		initGUI();
	}

	/**
	 * Constructor.
	 * Only for testing purposes.
	 * @param db
	 */
	public TimelineMaker(DBHelper db) {
		database = db;
		timelines = new ArrayList<Timeline>();
		try {
			for (Timeline t : database.getTimelines())
				timelines.add(t);
			selectedTimeline = timelines.get(0);
			selectedEvent = selectedTimeline.getEvents()[0];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Your database is empty.");
		} catch (Exception e){
			System.out.println("Error loading from Database.");
		}		
		graphics = new TimelineGraphics(this);
		gui = new MainWindow(this, graphics);
		while (!timelines.isEmpty())
			deleteTimeline();
	}

	/**
	 * Initialize the GUI components of this application.
	 */
	private void initGUI() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui = new MainWindow(TimelineMaker.this, graphics);
				gui.setVisible(true);
				new Thread(new Runnable() {
					public void run() {
						gui.updateTimelines(getTimelineTitles(), null);
					}
				}).start();
			}
		});

	}

	/**
	 * Retrieve a list of the names of all the timelines.
	 * @return timelines
	 */
	public ArrayList<String> getTimelineTitles() {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (Timeline t: timelines)
			toReturn.add(t.getName());
		return toReturn;
	}

	/**
	 * Retrieve the timeline with the parameterized name.
	 * @param title The name of the timeline to be found
	 * @return The timeline with the correct name; null otherwise.
	 */
	private Timeline getTimeline(String title) { 
		for (Timeline t : timelines)
			if (t.getName().equals(title))
				return t;
		return null;
	}

	/**
	 * Retrieve the currently selected timeline.
	 * @return selectedTimeline
	 */
	public Timeline getSelectedTimeline() {
		return selectedTimeline;
	}

	/**
	 * Set the selected timeline.
	 * Find the timeline of the parameterized title and set selectedTimeline to it.
	 * Update selectedTimeline, selectedEvent, and graphics.
	 * @param title of the timeline
	 */
	public void selectTimeline(String title) {
		selectedTimeline = getTimeline(title);
		selectedEvent = null;
		if (selectedTimeline != null)
			updateGraphics();
	}

	/**
	 * Add a timeline to this model.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param t the timeline to be added
	 */
	public void addTimeline(Timeline t) {
		selectedTimeline = t;
		selectedEvent = null;
		timelines.add(selectedTimeline);

		database.writeTimeline(selectedTimeline);
		gui.updateTimelines(getTimelineTitles(), selectedTimeline.getName());
		updateGraphics();
	}

	/**
	 * Remove a timeline from this model.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param t the timeline to be removed
	 */
	public void deleteTimeline() {
		if (selectedTimeline != null) {
			timelines.remove(selectedTimeline);
			database.removeTimeline(selectedTimeline);
			selectedTimeline = null;
			selectedEvent = null;
			graphics.clearScreen();
			gui.updateTimelines(getTimelineTitles(), null);
		}
	}

	/**
	 * Edit the selected timeline.
	 * Remove the selected timeline and replace it with the parameterized one.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param t the new timeline
	 */
	public void editTimeline(Timeline t) {
		timelines.remove(selectedTimeline);
		database.removeTimeline(selectedTimeline);

		boolean newName;
		try {
			newName = !selectedTimeline.getName().equals(t.getName());
		} catch (NullPointerException e) {
			newName = true;
		}
		selectedTimeline = t;
		timelines.add(selectedTimeline);
		database.writeTimeline(selectedTimeline);
		if (newName)
			gui.updateTimelines(getTimelineTitles(), selectedTimeline.getName());
		updateGraphics();
	}

	/**
	 * Retrieve the currently selected event.
	 * @return selectedEvent
	 */
	public TLEvent getSelectedEvent() { 
		return selectedEvent; 
	}

	/**
	 * Set the selected event.
	 * @param e The event to be selected
	 */
	public void selectEvent(TLEvent e) {
		if (e != null)
			selectedEvent = e;
	}

	/**
	 * Add an event to the selected timeline.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param e the new event
	 */
	public void addEvent(TLEvent e) {
		if (selectedTimeline != null) {
			selectedTimeline.addEvent(e);
			selectedEvent = e;

			updateGraphics();

			database.removeTimeline(selectedTimeline);
			database.writeTimeline(selectedTimeline);
		}
	}

	/**
	 * Delete the selected event from the timeline.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 */
	public void deleteEvent() {
		if (selectedEvent != null && selectedTimeline != null && selectedTimeline.contains(selectedEvent)) {
			selectedTimeline.removeEvent(selectedEvent);
			selectedEvent = null;

			updateGraphics();

			database.removeTimeline(selectedTimeline);
			database.writeTimeline(selectedTimeline);
		}
	}

	/**
	 * Edit the selected event.
	 * Remove the currently selected event from the timeline and replace it with the parameter.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param e the new event
	 */
	public void editEvent(TLEvent e) {
		if (selectedEvent != null && selectedTimeline != null && selectedTimeline.contains(selectedEvent)) {
			selectedTimeline.removeEvent(selectedEvent);
			selectedEvent = e;
			selectedTimeline.addEvent(selectedEvent);

			updateGraphics();

			database.removeTimeline(selectedTimeline);
			database.writeTimeline(selectedTimeline);
		}
	}

	/**
	 * Update the graphics for the display screen.
	 */
	public void updateGraphics() { 
		graphics.clearScreen();
		graphics.renderTimeline(selectedTimeline);
	}

}
