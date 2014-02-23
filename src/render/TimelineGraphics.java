package render;

import model.TimelineMaker;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import model.Timeline;

/**
 * This is the object to represent all the graphics in the Timeline. It creates
 * TimelineRenders whenever the renderTimeline method is called.
 * 
 * @author Josh Wright
 * Created: Feb 4, 2014
 * Package: graphics
 *
 */
public class TimelineGraphics{
	
	/**
	 * the JFXPanel to put the graphics in. JFXPanel provides a link between the 
	 * graphics (javafx) and the gui (swing).
	 */
	private JFXPanel fxPanel;
	
	/**
	 * The Group that the timeline items are put in. This is then put on a Scene
	 * which displays the graphics to the screen.
	 * 
	 * Only in this class so that the clearScreen method can work, otherwise 
	 * it could be encapsulated in the TimelineRender object.
	 * 
	 */
	private Group root;
	
	/**
	 * The state of the program, only in this class to pass it to the 
	 * TimelineRender objects.
	 */
	private TimelineMaker model;
	
	/**
	 * Constructor that instantiates group and sets the model
	 * 
	 * @param model the model of the program
	 */
	public TimelineGraphics(TimelineMaker model){
		this.model = model;
		root = new Group();
	}
	
	/**
	 * Runs a TimelineRender object to draw the timeline on the javafx thread. 
	 * reinstantiates group since each group can only be in one scene (if this did not
	 * happen there would be issues with the clearScreen method)
	 * 
	 * @param timeline the timeline to render
	 */
	public void renderTimeline(Timeline timeline) {
		root = new Group();
		Platform.runLater(new TimelineRender(fxPanel, model, timeline, root));
	}

	/**
	 * Clears the screen. This is primarily used when a timeline has no information in it
	 * which would cause TimelineRender to do nothing, so this clears the screen of any
	 * previously rendered timelines.
	 * 
	 * This could also be used elsewhere, and seems like a useful method to have.
	 * 
	 */
	public void clearScreen() {
		Platform.runLater(new Runnable() {
			public void run() {
				root.getChildren().clear();
			}
		});
	}

	/**
	 * Seter for fxPanel
	 * 
	 * @param fxPanel the panel to set
	 */
	public void setPanel(JFXPanel fxPanel) {
		this.fxPanel = fxPanel;
	}
}
