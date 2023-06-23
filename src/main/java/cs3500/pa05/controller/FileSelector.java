package cs3500.pa05.controller;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

/**
 * A class for handling file selection operations.
 */
public class FileSelector {

  /**
   * Prompts the user to load an existing file or create a new file.
   *
   * @return A map entry representing the user's choice
   *         and selected file path, or null if cancelled.
   */
  public static Map.Entry<Integer, String> selectFile() {
    Map<Integer, String> runMode = new LinkedHashMap<>();

    while (true) {
      // Initialize FileChooser
      FileChooser fileChooser = new FileChooser();

      // Set extension filter for .bujo files
      FileChooser.ExtensionFilter extFilter =
          new FileChooser.ExtensionFilter("BUJO files (*.bujo)", "*.bujo");
      fileChooser.getExtensionFilters().add(extFilter);

      // Prompt the user to make a choice
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Choose journal");
      alert.setHeaderText("Would you like to load an existing file or create a new one?");

      // Custom button types
      ButtonType buttonTypeOne = new ButtonType("Load existing journal");
      ButtonType buttonTypeTwo = new ButtonType("Create new journal");
      ButtonType buttonTypeThree = new ButtonType("Create with template");
      ButtonType buttonTypeCancel = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

      alert.getButtonTypes().setAll(
          buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent()) {
        if (result.get() == buttonTypeOne) {
          // User chose to load an existing file
          File selectedFile = fileChooser.showOpenDialog(null);
          if (selectedFile != null) {
            runMode.put(0, selectedFile.getAbsolutePath());
            return runMode.entrySet().iterator().next();
          }
        } else if (result.get() == buttonTypeTwo) {
          // User chose to create a new file
          fileChooser.setInitialFileName("journal.bujo");
          File selectedFile = fileChooser.showSaveDialog(null);
          if (selectedFile != null) {
            runMode.put(1, selectedFile.getAbsolutePath());
            return runMode.entrySet().iterator().next();
          }
        } else if (result.get() == buttonTypeThree) {
          runMode.put(2, null);
          return runMode.entrySet().iterator().next();
        } else if (result.get() == buttonTypeCancel) {
          // User chose to cancel
          return null;
        }
      }
    }
  }
}
