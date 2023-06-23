package cs3500.pa05.view;

import cs3500.pa05.controller.JavaJournalController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents a Java Journal GUI view.
 */
public class ApplicationView implements JavaJournalView {
  private final FXMLLoader loader;

  /**
   * Default Constructor
   *
   * @param controller The controller of the View
   */
  public ApplicationView(JavaJournalController controller) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("journal.fxml"));

    // initialization and location setting omitted for brevity
    this.loader.setController(controller);
  }

  @Override
  public Scene load() throws IllegalStateException {
    // load the layout
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
