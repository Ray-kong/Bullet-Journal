package cs3500.pa05;

import cs3500.pa05.controller.ApplicationController;
import cs3500.pa05.controller.JavaJournalController;
import cs3500.pa05.model.data.JournalWeek;
import cs3500.pa05.view.ApplicationView;
import cs3500.pa05.view.JavaJournalView;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

/**
 * Represents a Java Journal application.
 */
public class JavaJournalApplication extends Application {
  private static HostServices hostServices;

  @Override
  public void start(Stage primaryStage) {
    hostServices = getHostServices();

    // Instantiate a controller
    JavaJournalController controller = new ApplicationController(new JournalWeek(), primaryStage);
    // Instantiate a simple welcome GUI view
    JavaJournalView view = new ApplicationView(controller);

    try {
      // Set a title to the stage
      primaryStage.setTitle("Bujo Journal");

      // Load and place the view's scene onto the stage
      primaryStage.setScene(view.load());
      controller.run();

      // Render the stage
      primaryStage.show();
    } catch (IllegalStateException exc) {
      System.err.println("Unable to load GUI.");
    }
  }

  /**
   * Gets the Host Services of this Application
   *
   * @return Host Services of the Application
   */
  public static HostServices getHostServicesMine() {
    return hostServices;
  }
}
