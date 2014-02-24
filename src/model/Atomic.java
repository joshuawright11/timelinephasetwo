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
	 * The date of the event
	 */
	private Date date;
	
	/**
	 * Constructor for the name, category, and date
	 * 
	 * @param name the name of the event
	 * @param category the event's category
	 * @param date the date of the event
	 */
	public Atomic(String name, String category, Date date){
		super(name, category);
		this.setDate(date);
	}
	
	/**
	 * date getter
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * date setter
	 * 
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String typeName() {
		return "atomic";
	}
}
