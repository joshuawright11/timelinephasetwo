package model;
import java.sql.Date;
import javax.swing.ImageIcon;
/**
 * Extension of class TLEvent to represent atomic (single date) events
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Package: backend
 *
 */
public class Atomic extends TLEvent {
        
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
