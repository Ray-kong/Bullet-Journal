package cs3500.pa05.controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * Handle setting max activity limit.
 */
public class MaxActivityLimitHandler implements EventHandler<ActionEvent>, Initializable {
  private final ApplicationController controller;
  private Popup popup;
  private final int activityType;

  @FXML
  private Label maxActivityLabel;
  @FXML
  private TextField maxActivityTextField;
  @FXML
  private Button saveMaxActivityButton;
  @FXML
  private Button cancelMaxActivityButton;

  /**
   * Constructs a new MaxActivityLimitHandler.
   *
   * @param controller the ApplicationController to be used
   * @param activityType the type of activity to set a limit for (0 for events, 1 for tasks)
   */
  public MaxActivityLimitHandler(ApplicationController controller, int activityType) {
    this.controller = controller;
    this.activityType = activityType;
  }

  /**
   * Initializes the dialog for setting maximum activity limit.
   * This method is called after all @FXML annotated members have been injected.
   *
   * @param location  The location used to resolve relative paths for the root object, or null.
   * @param resources The resources used to localize the root object, or null.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    controller.disableMenuBar(true);

    if (activityType == 0) {
      maxActivityLabel.setText("Enter the max event limit:");
      maxActivityTextField.setText(
          Integer.toString(controller.getJournalWeek().getMaxEventWarnLimit()));
    } else {
      maxActivityLabel.setText("Enter the max task limit:");
      maxActivityTextField.setText(
          Integer.toString(controller.getJournalWeek().getMaxTaskWarnLimit()));
    }

    saveMaxActivityButton.setOnAction(e -> {
      String userInput = maxActivityTextField.getText();
      maxActivityTextField.clear();
      if (!userInput.isEmpty()) {
        try {
          int newMaxActivityLimit = Integer.parseInt(userInput);

          if (activityType == 0) {
            controller.getJournalWeek().setMaxEventWarnLimit(newMaxActivityLimit);
          } else {
            controller.getJournalWeek().setMaxTaskWarnLimit(newMaxActivityLimit);
          }
          BujoUtils.saveToFile(controller.getFilePath(), controller.getJournalWeek());
          controller.disableMenuBar(false);
          controller.getStage().setAlwaysOnTop(false);
          popup.hide();
        } catch (NumberFormatException ex) {
          controller.getStage().setAlwaysOnTop(true);
          Window popupWindow = popup.getOwnerWindow();
          Alert alert = new Alert(Alert.AlertType.ERROR,
              "Please enter a valid integer!",
              ButtonType.OK);
          alert.initOwner(popupWindow);
          alert.showAndWait();
        }
      }
    });

    cancelMaxActivityButton.setOnAction(e -> {
      controller.disableMenuBar(false);
      popup.hide();
    });
  }

  /**
   * Handles the action event for setting maximum activity limit.
   * This method opens a dialog to let the user input the maximum activity limit,
   * then saves this limit to the journal and refreshes the display.
   *
   * @param event the action event to handle
   */
  @Override
  public void handle(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
          .getResource("setMaxActivityLimit.fxml"));
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
