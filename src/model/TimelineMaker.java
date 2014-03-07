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
import javafx.scene.image.Image;

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
        int idCounter;
        
        private final String help_text = "\tHow to use this Timeline Maker:  \n"
                + "*Use the buttons on the left to create, edit, or delete timelines. Timelines may have titles and background colors, and they may be displayed in a number of different units.\n"
                + "*Each timeline has a set of events. Create events with the \"add\" button.\n"
                + "*To edit and delete events, select them on the rendered timeline and then proceed to delete them.\"\n"
                + "*Each timeline also has a set of categories. There must be at least one category, the default category, which may be edited. Each category has a name and a color associated with it.\"\n"
                + "*Image icons may be added to timeline events. Upload images using the right side-bar and set them in the event editing window.\n";
        
        private final String about_text = "\tCredits: \n\n"
                +"@Authors Andrew.Sutton, Josh Wright, Kayley Lane, Conner Vick, Brian Williamson\n\n"
                +"\tSoftware Dev 2014";
	/**
	 * Constructor.
	 * Create a new TimelineMaker application model with database, graphics, and GUI components.
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
        
        public boolean deleteIcon(String icon){
            //The user is not allowed to delete the only category!
            if(icons.size() <= 1) return false;
            Icon ico = new Icon(null, null);
            for(Icon i: icons)
                if(i.getName().equals(icon)){
                    ico = i;
                    break;
                }
            for(Timeline t: timelines){
                Iterator<TLEvent> eventIterator = t.getEventIterator();
                TLEvent e;
                while(eventIterator.hasNext()){
                    e = eventIterator.next();
                    if(e.getIcon() == ico)
                        e.setIcon(null);
                }
            }
            return icons.remove(ico);
        }

        
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
        
        
        
        public Timeline getSelectedTimeline(){
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
		Timeline newTimeline = new Timeline(title, t.getEvents(), color, axisUnit);
		newTimeline.setID(t.getID());
		timelines.add(newTimeline);
		//database.removeTimeline(selectedTimeline);
		//database.saveTimeline(newTimeline);
		database.editTimelineInfo(newTimeline); //TODO get this working
		selectedTimeline = newTimeline;
		mainWindow.populateListView();
		updateGraphics();
	}
        
        public void populateView(){
            mainWindow.populateListView();
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
	public void addEvent(String title, Date startDate, Date endDate, Object category, String description, Icon icon) {
		TLEvent event;
		if(endDate != null){
			event = new Duration(title, new Category(""), startDate, endDate);
		}
		else{
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
	public void editEvent(TLEvent oldEvent, String title, Date startDate, Date endDate, Category category, String description, Icon icon) {
		if (selectedEvent != null && selectedTimeline != null && selectedTimeline.contains(selectedEvent)) {
			selectedTimeline.removeEvent(selectedEvent);
			TLEvent toAdd;
			if(endDate != null) toAdd = new Duration(title, new Category(""), startDate, endDate);
			else toAdd = new Atomic(title, new Category(""), startDate);
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
        
        public int getUniqueID() {
            return idCounter++;
         }

	/**
     * @param mainWindow the mainWindow to set
	 */
	public void setMainWindow(MainWindowController mainWindow) {
		this.mainWindow = mainWindow;
	}
                
        public int timeSize(){
            return timelines.size();
        }
        
        public String getHelpText(){
            return help_text;
        }

        public String getAboutText(){
            return about_text;
        }
}
