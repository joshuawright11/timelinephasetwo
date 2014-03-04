package gui;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import model.TimelineMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class EventPropertiesWindowController extends TimelineMakerController{

	private TimelineMaker timelineMaker;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="buttonSeparator"
    private Separator buttonSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="categoryComboBox"
    private ComboBox<?> categoryComboBox; // Value injected by FXMLLoader

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
    	String title = titleTextField.getText();
        Date startDate = new Date(0); //TODO get Date working
        Date endDate = null;
    	Object category = categoryComboBox.getValue();
    	String description = descriptionTextArea.getText();
    	if(durationCheckBox.isPressed()){
    		endDate = new Date(0); //TODO get Date working
    	}
    	timelineMaker.addEvent(title, startDate, endDate, category, description);
    }

    // Handler for CheckBox[fx:id="durationCheckBox"] onAction
    @FXML
    void durationPressed(ActionEvent event) {
        endDateTextField.setVisible(!endDateTextField.isVisible());
        dateToLabel.setVisible(!dateToLabel.isVisible());
        //TODO shift startDateTextField over
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	endDateTextField.setVisible(false);
        dateToLabel.setVisible(false);
      //TODO shift startDateTextField over
        initComboBox();
    }

	private void initComboBox() {
		//TODO initialize categories
	}

	/* (non-Javadoc)
	 * @see gui.TimelineMakerController#initData(model.TimelineMaker)
	 */
	@Override
	public void initData(TimelineMaker timelineMaker) {
		this.timelineMaker = timelineMaker;
	}

}
