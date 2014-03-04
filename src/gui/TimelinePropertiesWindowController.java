package gui;
import java.net.URL;
import java.util.ResourceBundle;

import model.Timeline;
import model.Timeline.AxisLabel;
import model.TimelineMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class TimelinePropertiesWindowController extends TimelineMakerController{

	private TimelineMaker timelineMaker;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="appearanceLabel"
    private Label appearanceLabel; // Value injected by FXMLLoader

    @FXML // fx:id="appearanceSeparator"
    private Separator appearanceSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="axisUnitComboBox"
    private ComboBox<AxisLabel> axisUnitComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="axisUnitLabel"
    private Label axisUnitLabel; // Value injected by FXMLLoader

    @FXML // fx:id="buttonSeparator"
    private Separator buttonSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="colorColorChooser"
    private ColorPicker colorColorChooser; // Value injected by FXMLLoader

    @FXML // fx:id="colorLabel"
    private Label colorLabel; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="fontComboBox"
    private ComboBox<?> fontComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="fontLabel"
    private Label fontLabel; // Value injected by FXMLLoader

    @FXML // fx:id="informationLabel"
    private Label informationLabel; // Value injected by FXMLLoader

    @FXML // fx:id="timelinePropertiesWindowAnchor"
    private AnchorPane timelinePropertiesWindowAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="titleLabel"
    private Label titleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="titleTextField"
    private TextField titleTextField; // Value injected by FXMLLoader


    // Handler for Button[fx:id="cancelButton"] onAction
    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Node source = (Node) event.getSource(); 
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    // Handler for Button[fx:id="createButton"] onAction
    @FXML
    void createButtonPressed(ActionEvent event) {
        String title = titleTextField.getText();
        if(title.equals("")){
        	//TODO title missing alert
        	return;
        }
        Color color = colorColorChooser.getValue();
        AxisLabel axisUnit = axisUnitComboBox.getValue();
        Font font = null; //TODO set font?
        timelineMaker.addTimeline(title, color, axisUnit, font);
        
        Node source = (Node) event.getSource(); 
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        initComboBox();
    }

	/**
	 * 
	 */
	private void initComboBox() {
		AxisLabel[] labels = Timeline.AxisLabel.values();
		for(AxisLabel label : labels)
			axisUnitComboBox.getItems().addAll(label);
		axisUnitComboBox.setValue(labels[0]);
	}

	/* (non-Javadoc)
	 * @see gui.TimelineMakerController#initData(model.TimelineMaker)
	 */
	@Override
	public void initData(TimelineMaker timelineMaker) {
		this.timelineMaker = timelineMaker;
	}

}