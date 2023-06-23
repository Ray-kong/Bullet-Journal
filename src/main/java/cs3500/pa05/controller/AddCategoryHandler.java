package cs3500.pa05.controller;

import cs3500.pa05.model.data.Category;
import cs3500.pa05.model.json.BujoUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * JavaFX event handler that is responsible for handling the creation of a new category.
 */
public class AddCategoryHandler implements EventHandler<ActionEvent>, Initializable {
  private final ApplicationController controller;
  private Popup popup;

  @FXML
  private TextField categoryNameTextField;
  @FXML
  private ColorPicker categoryColorPicker;
  @FXML
  private Button saveCategoryButton;
  @FXML
  private Button cancelCategoryButton;

  /**
   * Default Constructor
   *
   * @param controller Controller of the Journal
   */
  public AddCategoryHandler(ApplicationController controller) {
    this.controller = controller;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    controller.disableMenuBar(true);

    saveCategoryButton.setOnAction(e -> {
      String userInput = categoryNameTextField.getText();
      if (userInput.isEmpty()) {
        controller.getStage().setAlwaysOnTop(true);
        Window popupWindow = popup.getOwnerWindow();
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "Please provide NAME for the category!",
            ButtonType.OK);
        alert.initOwner(popupWindow);
        alert.showAndWait();
      } else {
        try {
          controller.getJournalWeek().addCategory(
              new Category(userInput, categoryColorPicker.getValue()));
          BujoUtils.saveToFile(controller.getFilePath(), controller.getJournalWeek());
        } catch (IllegalArgumentException ex) {
          new Alert(Alert.AlertType.ERROR,
              "Category already exists!",
              ButtonType.OK).showAndWait();
        }
        controller.disableMenuBar(false);
        popup.hide();
      }
    });

    cancelCategoryButton.setOnAction(e -> {
      controller.disableMenuBar(false);
      popup.hide();
    });
  }

  @Override
  public void handle(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
          .getResource("addCategory.fxml"));
      loader.setController(this);
      Scene scene = loader.load();
      popup = new Popup();
      popup.getContent().add(scene.getRoot());
      popup.show(controller.getStage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
