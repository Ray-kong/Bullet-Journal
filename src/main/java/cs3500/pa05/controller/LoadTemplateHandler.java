package cs3500.pa05.controller;

import cs3500.pa05.model.data.JournalWeek;
import cs3500.pa05.model.json.BujoUtils;
import java.io.File;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 * An event handler that handles the loading of .bujo files when triggered.
 */
public class LoadTemplateHandler implements EventHandler<ActionEvent> {
  private final ApplicationController controller;
  private boolean flag;

  /**
   * Default Constructor
   *
   * @param controller Controller of the Journal
   */
  public LoadTemplateHandler(ApplicationController controller) {
    this.controller = controller;
    this.flag = true;
  }

  /**
   * Gets the flag of the Handler
   *
   * @return Flag of the Handler
   */
  public boolean getFlag() {
    return this.flag;
  }

  @Override
  public void handle(ActionEvent event) {
    controller.disableMenuBar(true);

    FileChooser fileChooser = new FileChooser();

    // Set extension filter for .bujo files
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter(
            "Select template BUJO files (*.bujo)", "*.bujo");
    fileChooser.getExtensionFilters().add(extFilter);

    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      String templateFilePath = selectedFile.getAbsolutePath();
      JournalWeek templateJournal = BujoUtils.openFile(templateFilePath);

      // User chose to create a new file
      fileChooser.setInitialFileName("newJournal.bujo");
      File newFile = fileChooser.showSaveDialog(null);

      if (newFile != null) {
        JournalWeek newJournal = new JournalWeek();
        newJournal.setMaxEventWarnLimit(templateJournal.getMaxEventWarnLimit());
        newJournal.setMaxTaskWarnLimit(templateJournal.getMaxTaskWarnLimit());
        newJournal.setCategories(templateJournal.getCategories());

        String password = JavaFxBuilder.setPassword();
        if (password == null) {
          controller.disableMenuBar(false);
          flag = false;
          return;
        }
        newJournal.setPassword(password);
        String newFilePath = newFile.getAbsolutePath();
        BujoUtils.saveToFile(newFilePath, newJournal);
        controller.setJournalWeek(newJournal);
        controller.setFilePath(newFilePath);
        controller.renderWeek();

        FXMLLoader loader = new FXMLLoader(
            getClass().getClassLoader().getResource("splash.fxml"));

        try {
          ImageView splashScreen = loader.load();

          // Save the current scene
          final Scene oldScene = controller.getStage().getScene();

          // Create a new StackPane and add the splashScreen to it
          StackPane root = new StackPane();
          root.getChildren().add(splashScreen);

          Scene scene = new Scene(root);
          controller.getStage().setScene(scene);
          controller.getStage().show();

          // Wait for the splash screen to show for 1.8 seconds
          PauseTransition pause = new PauseTransition(Duration.seconds(1.8f));
          pause.setOnFinished(e -> {
            // Return to the original scene
            controller.getStage().setScene(oldScene);

            // Let the user choose the name
            RenameJournalHandler renameJournalHandler = new RenameJournalHandler(controller);
            ActionEvent actionEvent = new ActionEvent();
            renameJournalHandler.handle(actionEvent);
          });
          pause.play();

        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      } else {
        controller.disableMenuBar(false);
        flag = false;
      }
    } else {
      controller.disableMenuBar(false);
      flag = false;
    }
  }
}
