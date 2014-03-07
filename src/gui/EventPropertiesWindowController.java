package gui;
import java.net.URL;
import java.sql.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;

import model.Duration;
import model.TLEvent;
import model.TimelineMaker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Timeline;
import model.*;

public class EventPropertiesWindowController{

    private  TimelineMaker timelineMaker;
	
    private TLEvent oldEvent;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="buttonSeparator"
    private Separator buttonSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="categoryComboBox"
    private ComboBox<String> categoryComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="categoryLabel"
    private Label categoryLabel; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="dateLabel"
    private Label dateLabel; // Value injected by FXMLLoader

    @FXML // fx:id="dateToLabel"
    private Label dateToLabel; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionLabel"
    private Label descriptionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionTextArea"
    private TextArea descriptionTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="durationCheckBox"
    private CheckBox durationCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="endDateTextField"
    private TextField endDateTextField; // Value injected by FXMLLoader

    @FXML // fx:id="eventPropertiesWindowAnchor"
    private AnchorPane eventPropertiesWindowAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="iconComboBox"
    private ComboBox<?> iconComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="iconLabel"
    private Label iconLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="startDateTextField"
    private TextField startDateTextField; // Value injected by FXMLLoader

    @FXML // fx:id="titleLabel"
    private Label titleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="titleTextField"
    private TextField titleTextField; // Value injected by FXMLLoader

    @FXML // fx:id="typeLabel"
    private Label typeLabel; // Value injected by FXMLLoader


    // Handler for Button[fx:id="cancelButton"] onAction
    @FXML
    void cancelPressed(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    // Handler for Button[fx:id="createButton"] onAction
    @FXML
    void createPressed(ActionEvent event) {
        Category selectedCategory = timelineMaker.getSelectedTimeline().
                getCategory(categoryComboBox.getSelectionModel().getSelectedItem());
    	String title = titleTextField.getText();
        Date startDate = Date.valueOf(startDateTextField.getText());
        Date endDate = null;
    	String description = descriptionTextArea.getText();
    	if(durationCheckBox.isSelected()){
    		endDate = Date.valueOf(endDateTextField.getText());
    	}
    	if(oldEvent != null) timelineMaker.editEvent(oldEvent, title, startDate, endDate, selectedCategory, description);
    	else timelineMaker.addEvent(title, startDate, endDate, selectedCategory, description);
        
    	Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    // Handler for CheckBox[fx:id="durationCheckBox"] onAction
    @FXML
    void durationPressed(ActionEvent event) {
        endDateTextField.setVisible(!endDateTextField.isVisible());
        dateToLabel.setVisible(!dateToLabel.isVisible());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	endDateTextField.setVisible(false);
        dateToLabel.setVisible(false);
        oldEvent = null;
    }

    //Populates the combo box with categories.
    public void initComboBox() {
        categoryComboBox.setItems(FXCollections.observableList(timelineMaker
                .getSelectedTimeline().getCategoryTitles()));
        if(timelineMaker.getSelectedTimeline() != null){
            if(oldEvent != null && !oldEvent.getCategory().getName().equals("")) categoryComboBox.getSelectionModel().select(
                oldEvent.getCategory().getName());
            else categoryComboBox.getSelectionModel().select(
                timelineMaker.getSelectedTimeline().getDefaultCategory().getName());
        }
    }

	public void initData(TimelineMaker timelineMaker, TLEvent event) {
		this.timelineMaker = timelineMaker;
		this.oldEvent = event;
		if(event != null) loadEventInfo(event);
                initComboBox();
	}

	private void loadEventInfo(TLEvent event) {
		titleTextField.setText(event.getName());
		if(event instanceof Duration){
			durationCheckBox.setSelected(true);
	        endDateTextField.setVisible(!endDateTextField.isVisible());
	        dateToLabel.setVisible(!dateToLabel.isVisible());
			endDateTextField.setText(((Duration) event).getEndDate().toString());
		}
		startDateTextField.setText(event.getStartDate().toString());
		categoryComboBox.setValue(event.getCategory().getName());
		descriptionTextArea.setText(event.getDescription());
	}

}
