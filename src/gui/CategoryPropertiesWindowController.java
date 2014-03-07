package gui;

import javafx.scene.paint.*;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import model.Category;
import model.TimelineMaker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CategoryPropertiesWindowController {

    private TimelineMaker timelineMaker;
	
    private Category category;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="buttonSeparator"
    private Separator buttonSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="categoryNameLabel"
    private Label categoryNameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="categoryNameTextField"
    private TextField categoryNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="renderColorColorPicker"
    private ColorPicker renderColorColorPicker; // Value injected by FXMLLoader

    @FXML // fx:id="renderColorLabel"
    private Label renderColorLabel; // Value injected by FXMLLoader


    // Handler for Button[fx:id="cancelButton"] onAction
    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    // Handler for Button[fx:id="createButton"] onAction
	@FXML
	void createButtonPressed(ActionEvent event) {
		String title = categoryNameTextField.getText();
		Color color = renderColorColorPicker.getValue();
		if (!title.equals("")) {
			if (category == null) {
				category = new Category(title, color);
				if (timelineMaker.getSelectedTimeline().addCategory(category))
					timelineMaker.addCategory(category);
			} else {
				timelineMaker.getSelectedTimeline().editCategory(category.getName(), title, color);
				timelineMaker.editCategory(category);
			}
			timelineMaker.getSelectedTimeline().selectCategory(category.getName());
			timelineMaker.populateView();
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
		}

	}
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	// nothing?
    }
    
    public void initData(TimelineMaker timelineMaker, Category category) {
	this.timelineMaker = timelineMaker;
	if(category != null){
            this.category = timelineMaker.getSelectedTimeline().getDefaultCategory();
            loadCategoryInfo();
        }
    }

	/**
	 * @param category2
	 */
	private void loadCategoryInfo() {
            categoryNameTextField.setText(category.getName());
            renderColorColorPicker.setValue(category.getColor());
	}

}
