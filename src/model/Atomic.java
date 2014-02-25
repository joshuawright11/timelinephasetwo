/**
 * 
 */
package model;
import java.sql.Date;
/**
 * Extension of class TLEvent to represent atomic (single date) events
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Package: backend
 *
 */
public class Atomic extends TLEvent {
	
        /**
        * There is nothing special about Atomic events right now.
        * To put anything else in this class apart from what's in TLEvent would be redundant.
 We may choose to put an icon here.
        */
	public Atomic(String name, Category category, Date startDate){
		super(name, startDate, category);
	}
        
                /**
         * Saves the event to the database.
         * TODO: insert the functionality for saving to the database.
         */
        public void save(){
           //
        }
}
