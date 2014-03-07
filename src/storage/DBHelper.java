/**
 * 
 */
package storage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.*;
import model.Timeline.AxisLabel;

/**
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Updated: March 1, 2014
 * Package: storage
 *
 * Using SQL ideas and very minimal code from http://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm
 *
 */
public class DBHelper implements DBHelperAPI{
	/**
	 * Used for connection to database
	 * connection: the connection to the database
	 * resultSet: for getting the results of queries
	 * statement: for executing queries, although I try to use prepared statements if possible
	 */
	private Connection connection = null;
	private ResultSet resultSet = null;  
    private Statement statement = null; 
	
    /**
     * The path of the database (should be <name>.db)
     */
    private String dbName;
    
	/**
	 * Used for making database Android compatible, useful to save as a variable
	 */
	private static final String ID = "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE"; // unique id for android
	
	/**
	 * Constructor; sets the database name
	 * 
	 * @param dbName the path to the database
	 */
	public DBHelper(String dbName){
		this.dbName = dbName;
		init();
	}
	
	/**
	 * Initializes the timeline_info database. 
	 * This can store various timeline attributes though currently only has 1, axisLabel
	 * 
	 */
	private void init(){
		open();
		try {
			statement.executeUpdate("CREATE TABLE timeline_info ("+ID+", timelineName TEXT, axisLabel TEXT);");
		} catch (SQLException e) {
			if(e.getMessage().contains("already exists")) {
				//it has already been created, no issues
			}else
				e.printStackTrace();
		}
		close();
	}
	
	/**
	 * Opens the connection to the database. This gets called before and database queries are made
	 * It sets up the connection and statement variables.
	 * 
	 */
	private void open(){
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:database/"+this.dbName+"");
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Couldn't connect to database.");
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			System.out.println("Class not found. Check your classpath for the JDBC library.");
			e.printStackTrace();
		}
	}

	/**
	 * Closes the database. This must be called whenever the user is done using the database or else
	 * the database will be locked. Ensure that this is called when finished accessing database.
	 * 
	 */
	private void close(){
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean editTimelineInfo(Timeline timeline) {
		open();
		try {
			changeTimelineName(timeline);
			updateTimelineInfo(timeline);
		} catch (SQLException e) {
			e.printStackTrace();
			close();
			return false;
		}
		close();
		return true;
	}

	/**
	 * @throws SQLException 
	 * 
	 */
	private void changeTimelineName(Timeline timeline) throws SQLException {
		String oldName = getName(timeline);
		String newName = timeline.getName();
		if(oldName.equals(newName)) return;
		String SELECT_LABEL = "ALTER TABLE \""+oldName+"\" RENAME TO \""+newName+"\";";
		PreparedStatement pstmt = connection.prepareStatement(SELECT_LABEL);
		pstmt.execute();
	}

	/**
	 * @param timeline
	 */
	private String getName(Timeline timeline) throws SQLException{
		String SELECT_LABEL = "SELECT timelineName FROM timeline_info WHERE _id = ?;";
		PreparedStatement pstmt = connection.prepareStatement(SELECT_LABEL);
		pstmt.setInt(1, timeline.getID());
		resultSet = pstmt.executeQuery();
		String oldName = resultSet.getString(1);
		return oldName;
	}

	/**
	 * Uses prepared statements to insert the timelineName and axisLabel into the timeline_info table
	 * 
	 * @param timelineName the timeline of the axisLabel to write
	 * @param axisLabel the axisLabel enum value
	 * @throws SQLException because there are databases
	 */
	private void writeTimelineInfo(Timeline timeline) throws SQLException{
		String INSERT_LABEL = "INSERT INTO timeline_info (timelineName, axisLabel) VALUES "
				+"(?,?);";
		PreparedStatement pstmt = connection.prepareStatement(INSERT_LABEL);
		pstmt.setString(1, timeline.getName());
		pstmt.setString(2, timeline.getAxisLabel().name());
		pstmt.executeUpdate();
	}
	
	/**
	 * Syncs the database to this timeline's info. Uses id to access the timeline in the database
	 * 
	 * 
	 * @param timeline the timeline to update
	 * @throws SQLException because there are databases
	 */
	private void updateTimelineInfo(Timeline timeline) throws SQLException{
		
		String UPDATE_NAME_LABEL = " UPDATE timeline_info SET timelineName=? WHERE _id=?;";
		PreparedStatement pstmt = connection.prepareStatement(UPDATE_NAME_LABEL);
		pstmt.setString(1, timeline.getName());
		pstmt.setInt(2, timeline.getID());
		pstmt.executeUpdate();
		
		String UPDATE_AXIS_LABEL = " UPDATE timeline_info SET axisLabel=? WHERE _id=?;";
		pstmt = connection.prepareStatement(UPDATE_AXIS_LABEL);
		pstmt.setString(1, timeline.getAxisLabel().name());
		pstmt.setInt(2, timeline.getID());
		pstmt.executeUpdate();
	}

	@Override
	public boolean saveTimeline(Timeline timeline) {
		String tlName = timeline.getName(); 
		open();
		try {
			statement.executeUpdate("CREATE TABLE "+tlName
					+" ("+ID+",eventName TEXT, type TEXT, startDate DATETIME, endDate DATETIME, category TEXT);");
			writeTimelineInfo(timeline);
			setTimelineID(timeline);
		} catch (SQLException e) {
			if(e.getMessage().contains("already exists")) {
				System.out.println("A timeline with that name already exists!");
				close();
				return false;
			}
			e.printStackTrace();
		}
		close();
		
		if(timeline.getEvents() == null){
			return true; // did not save any events, timeline still created
		}
		open();
		for(TLEvent event : timeline.getEvents()){
			try {
				if(event instanceof Atomic){
					writeEvent((Atomic)event, tlName);
				}else if(event instanceof Duration){
					writeEvent((Duration)event, tlName);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}catch (NullPointerException e){
				System.out.println("Nothing!");
			}
		}
		close();
		return true;
	}
	
	/**
	 * Sets a timeline's id field to what its unique id in the timeline_info table is.
	 * Must call this immediately after the timeline is first stored in the database. 
	 * 
	 * @param timeline the timeline whose id field is set
	 */
	private void setTimelineID(Timeline timeline) throws SQLException{
			String SELECT_LABEL = "SELECT _id FROM timeline_info WHERE timelineName = ?;";
			PreparedStatement pstmt = connection.prepareStatement(SELECT_LABEL);
			pstmt.setString(1, timeline.getName());
			resultSet = pstmt.executeQuery();
			int id = resultSet.getInt(1);
			timeline.setID(id);
	}

	/**	
	 * Gets the unique id for a timeline from the timeline_info table, based on the timeline's name. 
	 * Must set this immediately after the timeline is first stored in the database. 
	 * 
	 * @param name the name of the timeline whose id will be returned
	 * @return the id of the timeline
	 * @throws SQLException because there are databases.
	 */
	private void setEventID(TLEvent event, String timelineName) throws SQLException{
			String SELECT_LABEL = "SELECT _id FROM "+timelineName+" WHERE eventName = ?;";
			PreparedStatement pstmt = connection.prepareStatement(SELECT_LABEL);
			pstmt.setString(1, event.getName());
			resultSet = pstmt.executeQuery();
			int id = resultSet.getInt(1);
			event.setID(id);
	}

	/**
	 * Uses prepared statements to remove the timeline's info from the timeline_info table
	 * 
	 * @param id the id of the timeline to remove the info of
	 * @throws SQLException because there are databases
	 */
	private void removeTimelineInfo(int id) throws SQLException{
		String REMOVE_LABEL = "DELETE FROM timeline_info WHERE _id = ?;";
		PreparedStatement pstmt = connection.prepareStatement(REMOVE_LABEL);
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
	}
	
	/**
	 * @param timelineName the name of the timeline to get the axisLabel of
	 * @return the index of the AxisLabel (there is room for more)
	 * @throws SQLException because there are databases
	 */
	private int getAxisLabel(Timeline timeline) throws SQLException{
		String SELECT_LABEL = "SELECT axisLabel FROM timeline_info WHERE _id = ?;";
		PreparedStatement pstmt = connection.prepareStatement(SELECT_LABEL);
		pstmt.setInt(1, timeline.getID());
		resultSet = pstmt.executeQuery();
		String labelName = resultSet.getString(1);
		switch(labelName){
		case "DAYS":
			return 0;
		case "MONTHS":
			return 2;
		case "YEARS":
			return 3;
		default:
			return 3;
		}
	}
	
	/**
	 * Helper method for writeTimeline. Puts the atomic event in the correct 
	 * timeline's database using prepared statements; overloaded see below
	 * 
	 * @param event the atomic event to insert
	 * @param tlName the name of the timeline whose table this event belongs in
	 * @throws SQLException because there are databases
	 */
	private void writeEvent(Atomic event, String tlName) throws SQLException{
		String INSERT_ATOMIC = "INSERT INTO "+tlName
				+" (eventName,type,startDate,endDate,category) VALUES "
				+"(?,?,?,NULL,?);";
		PreparedStatement pstmt = connection.prepareStatement(INSERT_ATOMIC);
		pstmt.setString(1, event.getName());
                pstmt.setString(2, "atomic");
		pstmt.setDate(3, event.getStartDate());
		pstmt.setString(4, event.getCategory().getName());
		pstmt.executeUpdate();
	}
	
	/**
	 * Helper method for writeTimeline. Puts the duration event in the correct 
	 * timeline's database using prepared statements; overloaded see above
	 * 
	 * @param event the duration event to insert
	 * @param tlName the name of the timeline whose table this event belongs in
	 * @throws SQLException because there are databases
	 */
	private void writeEvent(Duration event, String tlName) throws SQLException{
		String INSERT_DURATION = "INSERT INTO "+tlName
				+" (eventName,type,startDate,endDate,category) VALUES "
				+"(?,?,?,?,?);";
		PreparedStatement pstmt = connection.prepareStatement(INSERT_DURATION);
		pstmt.setString(1, event.getName());
		pstmt.setString(2, "duration");
		pstmt.setDate(3, event.getStartDate());
		pstmt.setDate(4, event.getEndDate());
		pstmt.setString(5, event.getCategory().getName());
		pstmt.executeUpdate();
	}

	@Override
	public boolean removeTimeline(Timeline timeline) {
		open();
		try {
			statement.executeUpdate("DROP TABLE IF EXISTS'"+timeline.getName()+"';"); // if exists is probably bad for this
			removeTimelineInfo(timeline.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return true;
	}

	@Override
	public Timeline[] getTimelines() { //this could probably be split up into methods
		open();
		try {
			resultSet = statement.executeQuery("select name from sqlite_master where type = \"table\" "
					+ "and name != \"sqlite_sequence\" and name != \"timeline_info\";");
			ArrayList<String> timelineNames = new ArrayList<String>();
			int numTimelines = 0;
			while(resultSet.next()){ // Get all timeline names
				numTimelines ++;
				timelineNames.add(resultSet.getString(1));
			}
			Timeline[] timelines = new Timeline[numTimelines];
			for(int j = 0; j < numTimelines; j++){ // Get all timelines event arrays
				resultSet = statement.executeQuery("select * from "+timelineNames.get(j)+";");
				ArrayList<TLEvent> events = new ArrayList<TLEvent>();
				while(resultSet.next()){ // Get all events for the event
					String name = resultSet.getString("eventName");
					String type = resultSet.getString("type");
					TLEvent event = null;
					if(type.equals("atomic")){
						String cat = resultSet.getString("category");
                        Category category = new Category(cat);
						Date startDate = resultSet.getDate("startDate");
						event = new Atomic(name, category, startDate); // TODO Get category from database. Pretty sure this works...
					}else if(type.equals("duration")){
                        String cat = resultSet.getString("category");
                        Category category = new Category(cat);
						Date startDate = resultSet.getDate("startDate");
						Date endDate = resultSet.getDate("endDate");
						event = new Duration(name, category, startDate, endDate); // TODO Get category from database. Pretty sure this works...
					}else{
						System.out.println("YOU DONE MESSED UP.");
					}
					events.add(event);
				}
				Timeline timeline = new Timeline(timelineNames.get(j), events.toArray(new TLEvent[events.size()]), AxisLabel.YEARS);
				setTimelineID(timeline);
				AxisLabel label = AxisLabel.values()[getAxisLabel(timeline)];
				timeline.setAxisLabel(label);
				timelines[j] = timeline;
			}
			close();
			return timelines;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return null;
	}

	@Override
	public void saveEvent(TLEvent event, String timelineName) {
		open();
		try{
			if(event instanceof Atomic)
				writeEvent((Atomic)event, timelineName);
			else if(event instanceof Duration)
				writeEvent((Duration)event, timelineName);
			setEventID(event, timelineName);
		}
		catch(SQLException sql){
			sql.printStackTrace();
		}
		close();
	}

	@Override
	public boolean removeEvent(TLEvent event, String timelineName) {
		open();
		try{
			String REMOVE_EVENT_LABEL = "DELETE FROM "+timelineName+" WHERE _id = ?;";
			PreparedStatement pstmt = connection.prepareStatement(REMOVE_EVENT_LABEL);
			pstmt.setInt(1, event.getID());
			pstmt.executeUpdate();
			System.out.println("Deleted... hopefully");
			close();
			return true;
		}catch(SQLException sql){
			close();
			return false;
		}
	}

	@Override
	public boolean editEvent(TLEvent event, String timelineName) {
		open();
		try{
			String UPDATE_NAME_LABEL = " UPDATE "+timelineName+" SET eventName=? WHERE _id=?;";
			PreparedStatement pstmt = connection.prepareStatement(UPDATE_NAME_LABEL);
			pstmt.setString(1, event.getName());
			pstmt.setInt(2, event.getID());
			pstmt.executeUpdate();
			
			String UPDATE_STARTDATE_LABEL = " UPDATE "+timelineName+" SET startDate=? WHERE _id=?;";
			pstmt = connection.prepareStatement(UPDATE_STARTDATE_LABEL);
			pstmt.setDate(1, event.getStartDate());
			pstmt.setInt(2, event.getID());
			pstmt.executeUpdate();
			
			if(event instanceof Duration){
				String UPDATE_ENDDATE_LABEL = " UPDATE "+timelineName+" SET endDate=? WHERE _id=?;";
				pstmt = connection.prepareStatement(UPDATE_ENDDATE_LABEL);
				pstmt.setDate(1, ((Duration)event).getEndDate());
				pstmt.setInt(2, event.getID());
				pstmt.executeUpdate();
			}
			
			String UPDATE_CATEGORY_LABEL = " UPDATE "+timelineName+" SET category=? WHERE _id=?;";
			pstmt = connection.prepareStatement(UPDATE_CATEGORY_LABEL);
			pstmt.setString(1, event.getCategory().getName());
			pstmt.setInt(2, event.getID());
			pstmt.executeUpdate();
			
			close();
			return true;
		}catch(SQLException sql){
			sql.printStackTrace();
			close();
			return false;
		}
	}

}
