package gui;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class TimelinePropertiesWindowController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="appearanceLabel"
    private Label appearanceLabel; // Value injected by FXMLLoader

    @FXML // fx:id="appearanceSeparator"
    private Separator appearanceSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="axisUnitComboBox"
    private ComboBox<?> axisUnitComboBox; // Value injected by FXMLLoader

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
        // handle the event here
    }

    // Handler for Button[fx:id="createButton"] onAction
    @FXML
    void createButtonPressed(ActionEvent event) {
        // handle the event here
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert appearanceLabel != null : "fx:id=\"appearanceLabel\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert appearanceSeparator != null : "fx:id=\"appearanceSeparator\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert axisUnitComboBox != null : "fx:id=\"axisUnitComboBox\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert axisUnitLabel != null : "fx:id=\"axisUnitLabel\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert buttonSeparator != null : "fx:id=\"buttonSeparator\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert colorColorChooser != null : "fx:id=\"colorColorChooser\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert colorLabel != null : "fx:id=\"colorLabel\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert fontComboBox != null : "fx:id=\"fontComboBox\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert fontLabel != null : "fx:id=\"fontLabel\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert informationLabel != null : "fx:id=\"informationLabel\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert timelinePropertiesWindowAnchor != null : "fx:id=\"timelinePropertiesWindowAnchor\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'TimelinePropertiesWindow.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

    }

}
