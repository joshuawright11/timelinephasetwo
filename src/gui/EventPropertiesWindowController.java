package gui;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class EventPropertiesWindowController {

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
        // handle the event here
    }

    // Handler for Button[fx:id="createButton"] onAction
    @FXML
    void createPressed(ActionEvent event) {
        // handle the event here
    }

    // Handler for CheckBox[fx:id="durationCheckBox"] onAction
    @FXML
    void durationPressed(ActionEvent event) {
        // handle the event here
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert buttonSeparator != null : "fx:id=\"buttonSeparator\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert categoryComboBox != null : "fx:id=\"categoryComboBox\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert categoryLabel != null : "fx:id=\"categoryLabel\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert dateLabel != null : "fx:id=\"dateLabel\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert dateToLabel != null : "fx:id=\"dateToLabel\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert descriptionTextArea != null : "fx:id=\"descriptionTextArea\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert durationCheckBox != null : "fx:id=\"durationCheckBox\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert endDateTextField != null : "fx:id=\"endDateTextField\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert eventPropertiesWindowAnchor != null : "fx:id=\"eventPropertiesWindowAnchor\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert startDateTextField != null : "fx:id=\"startDateTextField\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";
        assert typeLabel != null : "fx:id=\"typeLabel\" was not injected: check your FXML file 'EventPropertiesWindow.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

    }

}
