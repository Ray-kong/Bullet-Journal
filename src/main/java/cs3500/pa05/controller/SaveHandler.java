package cs3500.pa05.controller;

import cs3500.pa05.model.json.BujoUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handel saving Journal.
 */
public class SaveHandler implements EventHandler<ActionEvent> {
  private final ApplicationController controller;

  /**
   * Constructs a new SaveHandler.
   *
   * @param controller the ApplicationController to be used
   */
  public SaveHandler(ApplicationController controller) {
    this.controller = controller;
  }

  /**
   * Handles the action event for saving a journal.
   *
   * @param event the action event to handle
   */
  @Override
  public void handle(ActionEvent event) {
    BujoUtils.saveToFile(controller.getFilePath(), controller.getJournalWeek());
  }
}
