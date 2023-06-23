package cs3500.pa05.view;

import java.io.File;
import javafx.scene.text.Font;

/**
 * Represents a font loader class.
 */
public class Fonts {
  private static final String fontPath = "src/main/resources/BubblegumSans-Regular.ttf";

  /**
   * Load the BubblegumSans Font as a custom Font.
   */
  public static final Font customFont = Font.loadFont(new File(fontPath).toURI().toString(), 16);
}
