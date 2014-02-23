/**
 * 
 */
package storage;

import model.TLEvent;
import model.Timeline;

/**
 * This is the interface for DBHelper. This class is used to access a database with the intent of storing and retrieving timelines.
 * Currently, this only adds and deletes the entire timeline, meaning that to change anything about the timeline means reading the 
 * entire thing to the database. Future development and refactoring of this could involve using each timeline's unique auto incremented
 * _id (used for using the database on android) to identify it so that methods could do specific changes to a timeline without having to
 * remove and read the entire thing. (This would speed up database operations)
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Package: backend
 *
 * Note: could be updated to have _id as parameters, it will make editing and adding stuff a lot easier (add _id to Timeline and TLEvent).
 *
 */
public interface DBHelperAPI {
	
	/**
	 * Saves a timeline and all its information to the database
	 * 
	 * @param timeline the timeline to write
	 * @return
	 */
	public boolean writeTimeline(Timeline timeline);
	
	/**
	 * Removes a timeline and all its information from the database
	 * 
	 * @param timeline the timeline to remove
	 * @return
	 */
	public boolean removeTimeline(Timeline timeline);

	/**
	 * Swaps a timeline for another timeline. Used for updating a timeline's information.
	 * 
	 * @param oldTimeline the old timeline to remove
	 * @param newTimeline the new timeline to switch it with
	 * @return the newly saved timeline
	 */
	public Timeline changeTimeline(Timeline oldTimeline, Timeline newTimeline);
	
	/**
	 * Returns an array of all timelines currently in the database. The timelines have their events and any additional info built into them
	 * 
	 * @return array of all timelines in the database
	 */
	public Timeline[] getTimelines();
	public void writeEvent(TLEvent event);
	public void removeEvent(TLEvent event);
}
