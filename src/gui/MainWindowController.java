package gui;
import java.net.URL;
import java.util.ResourceBundle;

import model.TLEvent;
import model.Timeline;
import model.TimelineMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainWindowController{

	private TimelineMaker timelineMaker;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="aboutMenuItem"
    private MenuItem aboutMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="addCategoryButton"
    private Button addCategoryButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="addEventButton"
    private Button addEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="addTimelineButton"
    private Button addTimelineButton; // Value injected by FXMLLoader

    @FXML // fx:id="categoriesLabel"
    private Label categoriesLabel; // Value injected by FXMLLoader

    @FXML // fx:id="categoriesListView"
    private ListView<?> categoriesListView; // Value injected by FXMLLoader

    @FXML // fx:id="deleteCategoryButton"
    private Button deleteCategoryButton; // Value injected by FXMLLoader

    
    @FXML // fx:id="deleteEventButton"
    private Button deleteEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteMenuItem"
    private MenuItem deleteMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="deleteTimelineButton"
    private Button deleteTimelineButton; // Value injected by FXMLLoader

    @FXML // fx:id="editCategoryButton"
    private Button editCategoryButton; // Value injected by FXMLLoader
    
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

    @FXML // fx:id="iconsLabel"
    private Label iconsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="iconsSeparator"
    private Separator iconsSeparator; // Value injected by FXMLLoader
      
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
    private ListView<String> timelinesListView; // Value injected by FXMLLoader

    @FXML // fx:id="toolbarPane"
    private AnchorPane toolbarPane; // Value injected by FXMLLoader

    @FXML // fx:id="toolbarSeparator"
    private Separator toolbarSeparator; // Value injected by FXMLLoader

    
    // Handler for MenuItem[fx:id="aboutMenuItem"] onAction
    // Handler for MenuItem[fx:id="aboutMenuItem"] onMenuValidation
    @FXML
    void aboutPressed(Event event) {
        // TODO open about window
    }

    // Handler for Button[fx:id="addCategoryButton"] onAction
    @FXML
    void addCategoryPressed(ActionEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="addIconImageButton"] onAction
    @FXML
    void addIconImagePressed(ActionEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="deleteCategoryButton"] onAction
    @FXML
    void deleteCategoryPressed(ActionEvent event) {
        // handle the event here
    }
    
    // Handler for Button[fx:id="deleteEventButton"] onAction
    @FXML
    void deleteEventPressed(ActionEvent event) {
        timelineMaker.deleteEvent();
    }

    // Handler for MenuItem[fx:id="deleteMenuItem"] onAction
    // Handler for MenuItem[fx:id="deleteMenuItem"] onMenuValidation
    @FXML
    void deletePressed(Event event) {
        // TODO Might delete
    }

    // Handler for Button[fx:id="deleteTimelineButton"] onAction
    @FXML
    void deleteTimelinePressed(ActionEvent event) {
    	timelineMaker.deleteTimeline();
    }
    
    // Handler for Button[fx:id="editCategoryButton"] onAction
    @FXML
    void editCategoryPressed(ActionEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="editEventButton"] onAction
    @FXML
    void editEventPressed(ActionEvent event) {
    	TLEvent selectedEvent = timelineMaker.getSelectedEvent();
    	if(selectedEvent == null) return;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPropertiesWindow.fxml"));
			Parent root = (Parent)loader.load();
			EventPropertiesWindowController controller = loader.<EventPropertiesWindowController>getController();
	        controller.initData(timelineMaker, selectedEvent);
			Stage stage = new Stage();
			stage.setTitle("Edit Event");
			Scene scene = new Scene(root);
	        scene.getStylesheets().add("gui/EventPropertiesWindow.css");
			stage.setScene(scene);
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    // Handler for Button[fx:id="editTimelineButton"] onAction
    @FXML
    void editTimelinePressed(ActionEvent event) {
    	Timeline selectedTimeline = timelineMaker.getSelectedTimeline();
    	if(selectedTimeline == null) return;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TimelinePropertiesWindow.fxml"));
			Parent root = (Parent)loader.load();
			TimelinePropertiesWindowController controller = loader.<TimelinePropertiesWindowController>getController();
	        controller.initData(timelineMaker, selectedTimeline);
			Stage stage = new Stage();
			stage.setTitle("Edit Timeline");
			Scene scene = new Scene(root);
	        scene.getStylesheets().add("gui/TimelinePropertiesWindow.css");
	        stage.setScene(scene);
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    // Handler for MenuItem[fx:id="exitMenuItem"] onAction
    // Handler for MenuItem[fx:id="exitMenuItem"] onMenuValidation
    @FXML
    void exitPressed(Event event) {
        System.exit(0);
    }

    // Handler for MenuItem[fx:id="helpMenuItem"] onAction
    // Handler for MenuItem[fx:id="helpMenuItem"] onMenuValidation
    @FXML
    void helpPressed(Event event) {
        // TODO show help window
    }

    // Handler for MenuItem[fx:id="newCategoryMenuItem"] onAction
    // Handler for MenuItem[fx:id="newCategoryMenuItem"] onMenuValidation
    @FXML
    void newCategoryPressed(Event event) {
        // TODO new Category window
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
	        controller.initData(timelineMaker, null);
			Stage stage = new Stage();
			stage.setTitle("Add Event");
			Scene scene = new Scene(root);
	        scene.getStylesheets().add("gui/EventPropertiesWindow.css");
	        stage.setScene(scene);
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
	        controller.initData(timelineMaker, null);
			Stage stage = new Stage();
			stage.setTitle("Add Timeline");
			Scene scene = new Scene(root);
	        scene.getStylesheets().add("gui/EventPropertiesWindow.css");
	        stage.setScene(scene);
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    // Handler for MenuItem[fx:id="saveMenuItem"] onAction
    // Handler for MenuItem[fx:id="saveMenuItem"] onMenuValidation
    @FXML
    void savePressed(Event event) {
        // TODO manually save all data to database
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	timelinesListView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				timelineListViewClicked();
			}
		});
    }

    private void timelineListViewClicked(){
    	timelineMaker.selectTimeline(timelinesListView.getSelectionModel().getSelectedItem());
    }
    
	public void populateListView() {
		timelinesListView.setItems(FXCollections.observableList(timelineMaker.getTimelineTitles()));
		if(timelineMaker.getSelectedTimeline() != null) timelinesListView.getSelectionModel().select(timelineMaker.getSelectedTimeline().getName());
	}

	public void initData(TimelineMaker timelineMaker) {
		this.timelineMaker = timelineMaker;
		timelineMaker.setMainWindow(this);
		populateListView();
		timelineMaker.graphics.setPanel(renderScrollPane);
	}

}
