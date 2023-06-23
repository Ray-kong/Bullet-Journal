package cs3500.pa05.view;

import javafx.scene.Scene;

/**
 * Represents a GUI view for the Java Journal.
 */
public interface JavaJournalView {
  /**
   * Loads a scene from a Java Journal GUI layout
   *
   * @return Layout of the Java Journal
   * @throws IllegalStateException load() invoked at an illegal or inappropriate time
   */
  Scene load() throws IllegalStateException;
}
