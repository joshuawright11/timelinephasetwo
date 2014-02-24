/**
 * 
 */
package model;

/**
 * This abstract class is used to define some methods and variables needed by all events.
 * It is currently extended by the Duration and Atomic classes. Both use java.sql.Date for their dates
 * to make database writing easier.
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Package: backend
 *
 */

public abstract class TLEvent {
	
	/**
	 * The name of the event
	 */
	private String name;
	
	/**
	 * The category of the event
	 */
	private String category;
	
	/**
	 * A super constructor for all TLEvents, setting the name and category 
	 * 
	 * @param name the name of the event
	 * @param category the category of the event
	 */
	TLEvent(String name, String category){
		this.name = name;
		this.category = category;
	}
	/**
	 * Get the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the category
	 * 
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * Set the name
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Return the name of the Type (to be used when storing on the database)
	 * 
	 * @return the name of the Type
	 */
	public abstract String typeName();
}
