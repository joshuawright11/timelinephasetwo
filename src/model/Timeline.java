/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javafx.scene.paint.Color;

/**
 * Timeline.java
 * 
 * Timeline object to keep track of the different timelines in the project. Contains a name, ArrayList of TLEvents, AxisLabel (for rendering), 
 and boolean, dirty, which is updated whenever the Timeline is changed. This can be used for deciding when to sync to the database, but is
 currently not in use (we sync whenever certain buttons in the GUI are pressed). 
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
	 * A list of all the categories in this application.
	 */
        private ArrayList<Category> categories;
        private Category selectedCategory;

        
	private int id; //Unique timeline id for ease of SQL saving.
	
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
	 * The Color of the timeline
	 */
	private Color colorTL;
	/**
	 * The Color of the Background
	 */
	private Color colorBG;
	/**
	 * whether the timeline has been changed since its last database sync
	 */
	private boolean dirty;
	
	private TimelineMaker timelineMaker;
	
        
    public Timeline(String name){
            this.name = name;
            events = new ArrayList<TLEvent>();
            axisLabel = AxisLabel.YEARS;
            setDirty(true);
            categories = new ArrayList<Category>();
            categories.add(new Category("DEFAULT"));
    }
	
	/**
	 * Constructor for name and axisLabel
	 * 
	 * @param name Timeline name
	 * @param axisLabel Unit to render timeline in
	 */
	public Timeline(String name, AxisLabel axisLabel, Color colorTL, Color colorBG) {
		this.name = name;
		this.colorBG = colorBG;
		this.colorTL = colorTL;
		events = new ArrayList<TLEvent>();
		this.axisLabel = axisLabel;
		this.events = new ArrayList<TLEvent>();
		dirty = true;
		categories = new ArrayList<Category>();
		categories.add(new Category("DEFAULT"));
	}

	/**
	 * Constructor for name, events, and axisLabel
	 * 
	 * @param name Timeline name
	 * @param events TLEvents in timeline
	 * @param axisLabel Unit to render timeline in
	 */
	public Timeline(String name, TLEvent[] events, Color colorTL, Color colorBG, AxisLabel axisLabel) {
            categories = new ArrayList<Category>();
            categories.add(new Category("DEFAULT"));
		this.name = name;
		if (events != null)
			this.events = new ArrayList<TLEvent>(Arrays.asList(events));
		else
			this.events = new ArrayList<TLEvent>();
		this.axisLabel = axisLabel;
		this.colorBG = colorBG;
		this.colorTL = colorTL;
		dirty = true;
		
	}


	public int getID(){
		return id;
	}
	public void setID(int id){
		this.id = id;
	}
	
	@Override
	public boolean contains(TLEvent event) {
		for (TLEvent e : events)
			if (e.equals(event))
				return true;
		return false;
	}
        
         /**
         * Finds and returns an event
         * 
         * @param title The name of the event to return.
         * @return The TLEvent found.
         * @throws Exception If not found, throws this exception.
         */
        public TLEvent findEvent(String title) throws Exception{
            for(int i = 0 ; i < events.size(); i++){
                if(events.get(i).getName().equals(title))
                    return events.get(i);
            }
            throw new Exception("Not found.");
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
	
	public Color getColorBG(){
		return colorBG;
	}
	public Color getColorTL(){
		return colorTL;
	}
	
	@Override
	public AxisLabel getAxisLabel() {
		return axisLabel;
	}
	
	public void setAxisLabel(AxisLabel axisLabel) {
		this.axisLabel = axisLabel;
	}
        
        public Iterator<TLEvent> getEventIterator(){
            return events.iterator();
        }
        
                                 /**
         * Adds a Category to the current collection of Categories.
         * 
         * @param c The Category to add.
         * @return True if successful, False otherwise.
         */
        public boolean addCategory(Category c){
            if(containsTitle(c)) return false;
            //Replace the default category if it wasn't edited.
            else if(categories.size() == 1 && getDefaultCategory().getName().equals("DEFAULT")){
                categories.add(c);
                deleteCategory(getDefaultCategory());
            }
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
         * @param cat The category to delete.
         * @return True if found and removed, False otherwise.
         */
        public boolean deleteCategory(Category cat){
            //The user is not allowed to delete the only category!
            if(categories.size() <= 1) return false;
            for(TLEvent e: events)
                if(e.getCategory().getName().equals(cat.getName()))
                    e.setCategory(getDefaultCategory());
            selectCategory(getDefaultCategory().getName());
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
        
        public ArrayList<String> getCategoryTitles() {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (Category c: categories){
			toReturn.add(c.getName());
		}
		return toReturn;
        }
        
        public Category getSelectedCategory(){
            if(selectedCategory == null) selectedCategory = getDefaultCategory();
            return selectedCategory;
        }
        
        public Category getCategory(String title){
            for (Category c : categories)
                if (c.getName().equals(title))
                    return c;
            return getDefaultCategory();
        }
        
        public void editCategory(String title, String name, Color color){
            Category c = getCategory(title);
            c.setColor(color);
            c.setName(name);
        }
        
	public void selectCategory(String title) {
            selectedCategory = getCategory(title);
	}


}
