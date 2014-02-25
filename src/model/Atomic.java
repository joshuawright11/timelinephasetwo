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
	
        private AtomicLabel label;
        
	public Atomic(String name, Category category, Date startDate){
            super(name, startDate, category);
	}
        
        public void setLabel(AtomicLabel label){
            this.label = label;
        }
        
       /**
         * Saves the event to the database.
         * TODO: insert the functionality for saving to the database.
         */
        public void save(){
           //
        }
        
        
}
