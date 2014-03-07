
/**
 * 
 */
package model;

import java.sql.Date;

import javafx.scene.image.Image;
import storage.DBHelper;

public class TLEvent{
	protected String description, name;
        private Date startDate;
        private Category category;
        private int id;
        private Icon icon;
        private int iconIndex;
        
        /**
         * Constructor
         * 
         * @param name The name of the Event to be constructed.
         */
        public TLEvent(String name){
        	this.setIconIndex(-1);
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
	public TLEvent(String name, Date startDate, Category category, int iconIndex, String description){
		this.name = name;
		this.startDate = startDate;
		this.category = category;
		this.description = description;
		this.icon = new Icon("None", null, null); //TODO this is kludgy
		this.setIconIndex(-1);
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
        
        /**
         * Saves the event to the database.
         */
        public void save(DBHelper db, String timelineName){
        	db.saveEvent(this, timelineName);
        }
        
        
        /**
	 * @return the id
	*/
	public int getID() {
            return id;
	}
        /**
        * @param id the id to set
         */
	public void setID(int id) {
            this.id = id;
	}
        /**
         * 
         * @return the icon associated with this event
         */
        public Icon getIcon(){
            return icon;
        }
        
        /**
         * 
         * @param icon sets the icon of this event
         */
        public void setIcon(Icon icon){
            this.icon = icon;
        }
		/**
		 * @return the iconIndex
		 */
		public int getIconIndex() {
			return iconIndex;
		}
		/**
		 * @param iconIndex the iconIndex to set
		 */
		public void setIconIndex(int iconIndex) {
			this.iconIndex = iconIndex;
		}
}