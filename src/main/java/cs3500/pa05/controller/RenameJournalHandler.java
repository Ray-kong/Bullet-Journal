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
import javafx.scene.control.TextField;
import javafx.stage.Popup;

/**
 * Handles renaming a journal.
 */
public class RenameJournalHandler implements EventHandler<ActionEvent>, Initializable {
  private final ApplicationController controller;
  private Popup popup;

  @FXML
  private TextField renameTextField;
  @FXML
  private Button saveRenameButton;
  @FXML
  private Button cancelRenameButton;

  /**
   * Constructs a new RenameJournalHandler.
   *
   * @param controller the ApplicationController to be used
   */
  public RenameJournalHandler(ApplicationController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the dialog for renaming the journal.
   *
   * @param location  The location used to resolve relative paths for the root object, or null.
   * @param resources The resources used to localize the root object, or null.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    controller.disableMenuBar(true);

    saveRenameButton.setOnAction(e -> {
      String userInput = renameTextField.getText();
      if (userInput.isEmpty()) {
        new Alert(Alert.AlertType.WARNING,
            "The Journal name must not empty!",
            ButtonType.OK).showAndWait();
      } else {
        renameTextField.clear();
        controller.getJournalWeek().setName(userInput);
        BujoUtils.saveToFile(controller.getFilePath(), controller.getJournalWeek());
        controller.renderWeek();
        controller.disableMenuBar(false);
        popup.hide();
      }
    });

    cancelRenameButton.setOnAction(e -> {
      controller.disableMenuBar(false);
      popup.hide();
    });
  }

  /**
   * Handles the action event for renaming the journal.
   *
   * @param event the action event to handle
   */
  @Override
  public void handle(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getClassLoader().getResource("renameJournal.fxml"));
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
