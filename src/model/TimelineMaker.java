package model;

import render.*;
import gui.*;
import storage.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Timeline.AxisLabel;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

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
	 * A list of all the categories in this application.
	 */
        private ArrayList<Category> categories;
	/**
	 * The timeline selected in this application.
	 */
	private Timeline selectedTimeline;
        private Category selectedCategory;
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
        int idCounter;
	/**
	 * Constructor.
	 * Create a new TimelineMaker application model with database, graphics, and GUI components.
	 */
	public TimelineMaker() {
		database = new DBHelper("timeline.db");
		graphics = new TimelineGraphics(this);
		timelines = new ArrayList<Timeline>();
                categories = new ArrayList<Category>();
                categories.add(new Category("Default"));

		try {
			for (Timeline t : database.getTimelines())
				timelines.add(t);
			selectedTimeline = timelines.get(0);
			selectedEvent = null;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Your database is empty.");
		} catch (Exception e){
			System.out.println("Error loading from Database.");
		}

		//initGUI();
	}

//	/**
//	 * Constructor.
//	 * Only for testing purposes.
//	 * @param db
//	 */
//	public TimelineMaker(DBHelper db) {
//		database = db;
//		timelines = new ArrayList<Timeline>();
//		try {
//			for (Timeline t : database.getTimelines())
//				timelines.add(t);
//			selectedTimeline = timelines.get(0);
//			selectedEvent = selectedTimeline.getEvents()[0];
//		} catch (IndexOutOfBoundsException e) {
//			System.out.println("Your database is empty.");
//		} catch (Exception e){
//			System.out.println("Error loading from Database.");
//		}		
//		graphics = new TimelineGraphics(this);
//		gui = new MainWindow(this, graphics);
//		while (!timelines.isEmpty())
//			deleteTimeline();
//	}

//	/**
//	 * Initialize the GUI components of this application.
//	 */
//	private void initGUI() {
//		try {
//			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//		} catch (InstantiationException ex) {
//			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//		} catch (IllegalAccessException ex) {
//			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//		} catch (UnsupportedLookAndFeelException ex) {
//			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//		}
//
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				gui = new MainWindow(TimelineMaker.this, graphics);
//				gui.setVisible(true);
//				new Thread(new Runnable() {
//					public void run() {
//						gui.updateTimelines(getTimelineTitles(), null);
//					}
//				}).start();
//			}
//		});
//
//	}

	/**
	 * Retrieve a list of the names of all the timelines.
	 * @return timelines
	 */
	public ArrayList<String> getTimelineTitles() {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (Timeline t: timelines){
			toReturn.add(t.getName());
		}
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
 Update selectedTimeline, selectedTLEvent, and graphics.
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
	 * Update selectedTimeline, selectedTLEvent, graphics, and database.
	 * @param t the timeline to be added
	 */
	public void addTimeline(String title, Color color, AxisLabel axisUnit, Font font) {
		Timeline t = new Timeline(title, axisUnit);
		selectedTimeline = t;
		selectedEvent = null;
		timelines.add(selectedTimeline);

		database.saveTimeline(selectedTimeline);
		mainWindow.populateListView();
		//gui.updateTimelines(getTimelineTitles(), selectedTimeline.getName());
		updateGraphics();
	}

	/**
	 * Remove a timeline from this model.
	 * Update selectedTimeline, selectedTLEvent, graphics, and database.
	 * @param t the timeline to be removed
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
	 * Edit the selected timeline.
	 * Remove the selected timeline and replace it with the parameterized one.
 Update selectedTimeline, selectedTLEvent, graphics, and database.
	 * @param t the new timeline
	 */
	public void editTimeline(Timeline t, String title, Color color, AxisLabel axisUnit, Font font) {
		timelines.remove(selectedTimeline);
		Timeline newTimeline = new Timeline(title, t.getEvents(), axisUnit);
		newTimeline.setID(t.getID());
		timelines.add(newTimeline);
		//database.removeTimeline(selectedTimeline);
		//database.saveTimeline(newTimeline);
		database.editTimelineInfo(newTimeline); //TODO get this working
		selectedTimeline = newTimeline;
		mainWindow.populateListView();
		updateGraphics();
	}

	/**
	 * Retrieve the currently selected event.
	 * @return selectedTLEvent
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
	 * Update selectedTimeline, selectedTLEvent, graphics, and database.
	 * @param e the new event
	 */
	public void addEvent(String title, Date startDate, Date endDate, Object category, String description) {
		TLEvent event;
		if(endDate != null){
			event = new Duration(title, new Category(""), startDate, endDate);
		}
		else{
			event = new Atomic(title, new Category(""), startDate);
		}
		if (selectedTimeline != null) {
			selectedTimeline.addEvent(event);
			selectedEvent = event;
			updateGraphics();
			database.saveEvent(event, selectedTimeline.getName());
		}
	}

	/**
	 * Delete the selected event from the timeline.
	 * Update selectedTimeline, selectedTLEvent, graphics, and database.
	 */
	public void deleteEvent() {
		if (selectedEvent != null && selectedTimeline != null && selectedTimeline.contains(selectedEvent)) {
			selectedTimeline.removeEvent(selectedEvent);
			database.removeEvent(selectedEvent, selectedTimeline.getName());
			selectedEvent = null;
			updateGraphics();
		}
	}

	/**
	 * Edit the selected event.
	 * Remove the currently selected event from the timeline and replace it with the parameter.
 Update selectedTimeline, selectedTLEvent, graphics, and database.
	 * @param e the new event
	 */
	public void editEvent(TLEvent oldEvent, String title, Date startDate, Date endDate, Object category, String description) {
		if (selectedEvent != null && selectedTimeline != null && selectedTimeline.contains(selectedEvent)) {
			selectedTimeline.removeEvent(selectedEvent);
			TLEvent toAdd;
			if(endDate != null) toAdd = new Duration(title, new Category(""), startDate, endDate);
			else toAdd = new Atomic(title, new Category(""), startDate);
			toAdd.setID(oldEvent.getID());
			selectedEvent = toAdd;
			selectedTimeline.addEvent(toAdd);

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
        
        public int getUniqueID() {
            return idCounter++;
         }

	/**
     * @param mainWindow the mainWindow to set
	 */
	public void setMainWindow(MainWindowController mainWindow) {
		this.mainWindow = mainWindow;
	}
                
                         /**
         * Adds a Category to the current collection of Categories.
         * 
         * @param c The Category to add.
         * @return True if successful, False otherwise.
         */
        public boolean addCategory(Category c){
            if(containsTitle(c)) return false;
            else categories.add(c);
            return true;        
        }
        /**
         * Searches known category titles to find a match.
         * 
         * @param cat The category for which to search.
         * @return True if found, False otherwise.
         */
        private boolean containsTitle(Category cat){
            for(Category c : categories){
                if(c.getName().equals(cat.getName())) return true;
            }return false;
        }
        /**
         * Deletes a Category from the current set of categories.
         * 
         * @param name The name of the category to delete.
         * @return True if found and removed, False otherwise.
         */
        public boolean deleteCategory(String name){
            for(Iterator it = categories.iterator();it.hasNext();){
                Category category = (Category)it.next();
                if(category.getName().equals(name)){
                    categories.remove(category);
                    return true;
                }
            }
            return false;
        }
        /**
         * Deletes a Category from the current set of categories.
         * 
         * @param cat The category to delete.
         * @return True if found and removed, False otherwise.
         */
        public boolean deleteCategory(Category cat){
            return categories.remove(cat);
        }
        /**
         * Method to get the default category.
         * 
         * @return The default Category.
         */
        public Category getDefaultCategory(){
            return categories.get(0);
        }
        
        public Iterator getCategoryIterator(){
            return categories.iterator();
        }
        
                /**
         * Method to get the number of categories known.
         * 
         * @return An int representing the number of categories known.
         */
        public int catSize(){
            return categories.size();
        }
        public int timeSize(){
            return timelines.size();
        }


}
