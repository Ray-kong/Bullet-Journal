package cs3500.pa05.controller;

import cs3500.pa05.JavaJournalApplication;
import cs3500.pa05.view.Fonts;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Hyperlink;

/**
 * The LinkParser class is a utility class.
 */
public class LinkParser {

  /**
   * Parses a string for hyperlinks and returns a list of JavaFX Hyperlink objects.
   *
   * @param text a string that may contain hyperlinks.
   * @return a list of Hyperlink objects for each hyperlink found in the text.
   */
  public static List<Hyperlink> parseLinks(String text) {
    List<Hyperlink> links = new ArrayList<>();

    // Sets up URLs
    String regex = "\\b(?:https?|ftp)://\\S+\\b";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(text);

    // Adds links to list
    while (matcher.find()) {
      String link = matcher.group();
      Hyperlink hyperlink = new Hyperlink(link);
      hyperlink.setFont(Fonts.customFont);
      hyperlink.setOnAction(e -> JavaJournalApplication.getHostServicesMine().showDocument(link));
      links.add(hyperlink);
    }
    return links;
  }

  /**
   /**
   * Removes all hyperlinks from a given string.
   *
   * @param text a string that may contain hyperlinks.
   * @return a new string with all the hyperlinks removed.
   */
  public static String removeLinks(String text) {
    // Regular expression pattern for matching URLs
    String regex = "\\b(?:https?|ftp)://\\S+\\b";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(text);

    // Remove all matches (URLs) from the text
    return matcher.replaceAll("");
  }
}
