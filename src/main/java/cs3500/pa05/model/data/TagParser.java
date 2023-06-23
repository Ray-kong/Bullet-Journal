package cs3500.pa05.model.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing and manipulating tags in a string.
 */
public class TagParser {

  /**
   * Parses the tags in a given string and returns a list of tags.
   *
   * @param str the string to parse tags from
   * @return a list of tags found in the string
   */
  public static List<String> parseTags(String str) {
    List<String> hashtags = new ArrayList<>();
    String regex = "#(\\w+)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(str);

    while (matcher.find()) {
      String hashtag = matcher.group(1);
      hashtags.add(hashtag);
    }

    return hashtags;
  }

  /**
   * Removes the tags from a given string and returns the modified string.
   *
   * @param str the string to remove tags from
   * @return the modified string with tags removed
   */
  public static String removeTags(String str) {
    String regex = "#(\\w+)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(str);

    return matcher.replaceAll("");
  }
}
