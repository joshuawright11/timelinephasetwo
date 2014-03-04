package gui;
import java.net.URL;
import java.util.ResourceBundle;

import model.TimelineMaker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainWindowController extends TimelineMakerController{

	private TimelineMaker timelineMaker;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="aboutMenuItem"
    private MenuItem aboutMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="addEventButton"
    private Button addEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="addTimelineButton"
    private Button addTimelineButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteEventButton"
    private Button deleteEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteMenuItem"
    private MenuItem deleteMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="deleteTimelineButton"
    private Button deleteTimelineButton; // Value injected by FXMLLoader

    @FXML // fx:id="editEventButton"
    private Button editEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="editMenu"
    private Menu editMenu; // Value injected by FXMLLoader

    @FXML // fx:id="editTimelineButton"
    private Button editTimelineButton; // Value injected by FXMLLoader

    @FXML // fx:id="eventsLabel"
    private Label eventsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="exitMenuItem"
    private MenuItem exitMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="fileMenu"
    private Menu fileMenu; // Value injected by FXMLLoader

    @FXML // fx:id="helpMenuItem"
    private MenuItem helpMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="infoMenu"
    private Menu infoMenu; // Value injected by FXMLLoader

    @FXML // fx:id="insertMenu"
    private Menu insertMenu; // Value injected by FXMLLoader

    @FXML // fx:id="mainWindowAnchor"
    private AnchorPane mainWindowAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="menuBar"
    private MenuBar menuBar; // Value injected by FXMLLoader

    @FXML // fx:id="newCategoryMenuItem"
    private MenuItem newCategoryMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="newEventMenuItem"
    private MenuItem newEventMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="newTimelineMenuItem"
    private MenuItem newTimelineMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="renderPaneContainer"
    private AnchorPane renderPaneContainer; // Value injected by FXMLLoader

    @FXML // fx:id="renderScrollPane"
    private ScrollPane renderScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="saveMenuItem"
    private MenuItem saveMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="splitPane"
    private SplitPane splitPane; // Value injected by FXMLLoader

    @FXML // fx:id="timelinesLabel"
    private Label timelinesLabel; // Value injected by FXMLLoader

    @FXML // fx:id="timelinesListView"
    private ListView<?> timelinesListView; // Value injected by FXMLLoader

    @FXML // fx:id="toolbarPane"
    private AnchorPane toolbarPane; // Value injected by FXMLLoader

    @FXML // fx:id="toolbarSeparator"
    private Separator toolbarSeparator; // Value injected by FXMLLoader


    // Handler for MenuItem[fx:id="aboutMenuItem"] onAction
    // Handler for MenuItem[fx:id="aboutMenuItem"] onMenuValidation
    @FXML
    void aboutPressed(Event event) {
        // handle the event here
    }

    // Handler for Button[fx:id="deleteEventButton"] onAction
    @FXML
    void deleteEventPressed(ActionEvent event) {
        // handle the event here
    }

    // Handler for MenuItem[fx:id="deleteMenuItem"] onAction
    // Handler for MenuItem[fx:id="deleteMenuItem"] onMenuValidation
    @FXML
    void deletePressed(Event event) {
        // handle the event here
    }

    // Handler for Button[fx:id="deleteTimelineButton"] onAction
    @FXML
    void deleteTimelinePressed(ActionEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="editEventButton"] onAction
    @FXML
    void editEventPressed(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPropertiesWindow.fxml"));
			Parent root = (Parent)loader.load();
			EventPropertiesWindowController controller = loader.<EventPropertiesWindowController>getController();
	        controller.initData(timelineMaker);
			Stage stage = new Stage();
			stage.setTitle("Edit Event");
	        stage.setScene(new Scene(root));
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    // Handler for Button[fx:id="editTimelineButton"] onAction
    @FXML
    void editTimelinePressed(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TimelinePropertiesWindow.fxml"));
			Parent root = (Parent)loader.load();
			TimelinePropertiesWindowController controller = loader.<TimelinePropertiesWindowController>getController();
	        controller.initData(timelineMaker);
			Stage stage = new Stage();
			stage.setTitle("Edit Timeline");
	        stage.setScene(new Scene(root));
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    // Handler for MenuItem[fx:id="exitMenuItem"] onAction
    // Handler for MenuItem[fx:id="exitMenuItem"] onMenuValidation
    @FXML
    void exitPressed(Event event) {
        // handle the event here
    }

    // Handler for MenuItem[fx:id="helpMenuItem"] onAction
    // Handler for MenuItem[fx:id="helpMenuItem"] onMenuValidation
    @FXML
    void helpPressed(Event event) {
        // handle the event here
    }

    // Handler for MenuItem[fx:id="newCategoryMenuItem"] onAction
    // Handler for MenuItem[fx:id="newCategoryMenuItem"] onMenuValidation
    @FXML
    void newCategoryPressed(Event event) {
        // handle the event here
    }

    // Handler for Button[fx:id="addEventButton"] onAction
    // Handler for MenuItem[fx:id="newEventMenuItem"] onAction
    // Handler for MenuItem[fx:id="newEventMenuItem"] onMenuValidation
    @FXML
    void newEventPressed(Event event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPropertiesWindow.fxml"));
			Parent root = (Parent)loader.load();
	        EventPropertiesWindowController controller = loader.<EventPropertiesWindowController>getController();
	        controller.initData(timelineMaker);
			Stage stage = new Stage();
			stage.setTitle("Add Event");
	        stage.setScene(new Scene(root));
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    // Handler for Button[fx:id="addTimelineButton"] onAction
    // Handler for MenuItem[fx:id="newTimelineMenuItem"] onAction
    // Handler for MenuItem[fx:id="newTimelineMenuItem"] onMenuValidation
    @FXML
    void newTimelinePressed(Event event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TimelinePropertiesWindow.fxml"));
			Parent root = (Parent)loader.load();
			TimelinePropertiesWindowController controller = loader.<TimelinePropertiesWindowController>getController();
	        controller.initData(timelineMaker);
			Stage stage = new Stage();
			stage.setTitle("Add Timeline");
	        stage.setScene(new Scene(root));
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    // Handler for MenuItem[fx:id="saveMenuItem"] onAction
    // Handler for MenuItem[fx:id="saveMenuItem"] onMenuValidation
    @FXML
    void savePressed(Event event) {
        // handle the event here
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert aboutMenuItem != null : "fx:id=\"aboutMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert addEventButton != null : "fx:id=\"addEventButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert addTimelineButton != null : "fx:id=\"addTimelineButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert deleteEventButton != null : "fx:id=\"deleteEventButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert deleteMenuItem != null : "fx:id=\"deleteMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert deleteTimelineButton != null : "fx:id=\"deleteTimelineButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert editEventButton != null : "fx:id=\"editEventButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert editMenu != null : "fx:id=\"editMenu\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert editTimelineButton != null : "fx:id=\"editTimelineButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert eventsLabel != null : "fx:id=\"eventsLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert exitMenuItem != null : "fx:id=\"exitMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert fileMenu != null : "fx:id=\"fileMenu\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert helpMenuItem != null : "fx:id=\"helpMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert infoMenu != null : "fx:id=\"infoMenu\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert insertMenu != null : "fx:id=\"insertMenu\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert mainWindowAnchor != null : "fx:id=\"mainWindowAnchor\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert newCategoryMenuItem != null : "fx:id=\"newCategoryMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert newEventMenuItem != null : "fx:id=\"newEventMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert newTimelineMenuItem != null : "fx:id=\"newTimelineMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert renderPaneContainer != null : "fx:id=\"renderPaneContainer\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert renderScrollPane != null : "fx:id=\"renderScrollPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert saveMenuItem != null : "fx:id=\"saveMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert splitPane != null : "fx:id=\"splitPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert timelinesLabel != null : "fx:id=\"timelinesLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert timelinesListView != null : "fx:id=\"timelinesListView\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert toolbarPane != null : "fx:id=\"toolbarPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert toolbarSeparator != null : "fx:id=\"toolbarSeparator\" was not injected: check your FXML file 'MainWindow.fxml'.";


    }

	/* (non-Javadoc)
	 * @see gui.TimelineMakerController#initData(model.TimelineMaker)
	 */
	@Override
	public void initData(TimelineMaker timelineMaker) {
		this.timelineMaker = timelineMaker;
	}

}
