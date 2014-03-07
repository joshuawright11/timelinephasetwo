/**
 * 
 */
package model;
import java.sql.Date;
/**
 * Extension of class TLEvent to represent duration (events that have a start and end date) events
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Package: backend
 *
 */
public class Duration extends TLEvent {
	
	/**
	 * the date the event ends
	 */
	private Date endDate;
	
	/**
	 * Constructor for the name, category, startDate, and endDate
	 * 
	 * @param name the event name
	 * @param category the event category
	 * @param startDate the event startDate
	 * @param endDate the event endDate
	 * @param description 
	 * @param iconIndex 
	 */
	public Duration(String name, Category category, Date startDate, Date endDate, int iconIndex, String description){
		super(name, startDate, category);
		this.endDate = endDate;
	}
	
        /**
         * Method to return the end date of the TLEvent.
         * 
         * @return The end date.
         */
	public Date getEndDate(){
            return endDate;
	}
        
        public void setEndDate(Date endDate){
            this.endDate = endDate;
        }
        
        /**
         * Saves the event to the database.
         * TODO: insert the functionality for saving to the database.
         */
        public void save(){
           //
        }
}
