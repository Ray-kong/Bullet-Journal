package cs3500.pa05.controller;

import cs3500.pa05.model.data.JournalWeek;
import cs3500.pa05.model.json.BujoUtils;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

/**
 * Class to handle the load action of application.
 */
public class LoadHandler implements EventHandler<ActionEvent> {
  private final ApplicationController controller;

  /**
   * Constructs a new LoadHandler.
   *
   * @param controller the ApplicationController to be used
   */
  public LoadHandler(ApplicationController controller) {
    this.controller = controller;
  }

  /**
   * Handles the action event for loading a journal.
   *
   * @param event the action event to handle
   */
  @Override
  public void handle(ActionEvent event) {
    controller.disableMenuBar(true);

    FileChooser fileChooser = new FileChooser();

    // Set extension filter for .bujo files
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("BUJO files (*.bujo)", "*.bujo");
    fileChooser.getExtensionFilters().add(extFilter);

    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      String filePath = selectedFile.getAbsolutePath();
      JournalWeek journal = BujoUtils.openFile(filePath);
      boolean passwordCorrect = false;
      while (!passwordCorrect) {
        String password = JavaFxBuilder.getPassword(controller);
        if (password.equals(journal.getPassword())) {
          controller.setJournalWeek(journal);
          controller.setFilePath(filePath);
          controller.renderWeek();
          controller.disableMenuBar(false);
          passwordCorrect = true;
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR,
              "Password incorrect!",
              ButtonType.OK);
          alert.showAndWait();
        }
      }
    } else {
      controller.disableMenuBar(false);
    }
  }
}
