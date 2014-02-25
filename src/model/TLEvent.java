
/**
 * 
 */
package model;

import java.sql.Date;

public class TLEvent{
	private String description, name;
        private Date startDate;
	private Category category;
        private TLEventLabel label;
        /**
         * Constructor
         * 
         * @param name The name of the Event to be constructed.
         */
        public TLEvent(String name){
            this.name = name;
            category = new Category("Default");
        }
        /**
         * Constructor
         * 
         * @param name The name of the Event to be consructed.
         * @param startDate The int value of start of the event.
         * @param category The category of the event.
         */
	public TLEvent(String name, Date startDate, Category category){
		this.name = name;
		this.startDate = startDate;
		this.category = category;
	}
        /**
         * Method to return the description of the TLEvent.
         * 
         * @return The Description.
         */
	public String getDescription(){
		return description;
	}
        /**
         * Method to return the name of the TLEvent.
         * 
         * @return The name of the event.
         */
	public String getName(){
		return name;
	}
        /**
         * Method to return the start date of the TLEvent.
         * 
         * @return The start date.
         */
	public Date getStartDate(){
            return startDate;
	}
        /**
         * Method to return the category of the TLEvent.
         * 
         * @return The category.
         */
	public Category getCategory(){
            return category;
	}
        /**
         * Sets the description.
         * 
         * @param description The description to use.
         */
	public void setDescription(String description){
		this.description = description;
	}
        /**
         * Sets the name.
         * 
         * @param name The name to use.
         */
	public void setName(String name){
		this.name = name;
	}
        /**
         * Sets the start date.
         * 
         * @param startDate The start date to use.
         */
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
        
        /**
         * Sets the category.
         * 
         * @param category The category to use.
         */
	public void setCategory(Category category){
		this.category = category;
	}
        
        public void setLabel(TLEventLabel label){
            this.label = label;
        }
        
        public TLEventLabel getLabel(){
            return this.getLabel();
        }
        
        /**
         * Saves the event to the database.
         * TODO: insert the functionality for saving to the database.
         */
        public void save(){
            this.save();
        }
        
        

}