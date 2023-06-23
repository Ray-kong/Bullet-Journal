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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Popup;

/**
 * Handles note editing operations.
 */
public class EditNotesHandler implements EventHandler<ActionEvent>, Initializable {
  private final ApplicationController controller;
  private Popup popup;

  @FXML
  private TextArea editNotesTextArea;
  @FXML
  private Button saveNotes;
  @FXML
  private Button cancelNotes;

  /**
   * Default Constructor
   *
   * @param controller Controller of the Journal
   */
  public EditNotesHandler(ApplicationController controller) {
    this.controller = controller;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    controller.disableMenuBar(true);

    editNotesTextArea.setText(controller.getJournalWeek().getNotes().getNote());

    saveNotes.setOnAction(e -> {
      String userInput = editNotesTextArea.getText();
      editNotesTextArea.clear();
      if (!userInput.isEmpty()) {
        controller.getJournalWeek().getNotes().setNote(userInput);
        controller.renderWeek();
        BujoUtils.saveToFile(controller.getFilePath(), controller.getJournalWeek());
      }
      controller.disableMenuBar(false);
      popup.hide();
    });

    cancelNotes.setOnAction(e -> {
      controller.disableMenuBar(false);
      popup.hide();
    });
  }

  @Override
  public void handle(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
          .getResource("editNotes.fxml"));
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
