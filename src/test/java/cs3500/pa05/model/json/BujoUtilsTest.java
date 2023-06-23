package cs3500.pa05.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.data.Category;
import cs3500.pa05.model.data.DayOfWeek;
import cs3500.pa05.model.data.Event;
import cs3500.pa05.model.data.JournalWeek;
import cs3500.pa05.model.data.Task;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for BujoUtils class.
 */
public class BujoUtilsTest {
  private static final String TEST_FILE_PATH = "test.bujo";
  private static final String TEST_FILE_CONTENT = "{" + System.lineSeparator()
      + "  \"name\" : \"Journal\"," + System.lineSeparator()
      + "  \"journal-day-jsons\" : [ {" + System.lineSeparator()
      + "    \"day-of-week\" : \"SUN\"," + System.lineSeparator()
      + "    \"events\" : [ ]," + System.lineSeparator()
      + "    \"tasks\" : [ ]" + System.lineSeparator()
      + "  }, {" + System.lineSeparator()
      + "    \"day-of-week\" : \"MON\"," + System.lineSeparator()
      + "    \"events\" : [ {" + System.lineSeparator()
      + "      \"name\" : \"Event 1\"," + System.lineSeparator()
      + "      \"description\" : \"Event 1 description\"," + System.lineSeparator()
      + "      \"day-of-week\" : \"MON\"," + System.lineSeparator()
      + "      \"category\" : {" + System.lineSeparator()
      + "        \"name\" : \"Category 1\"," + System.lineSeparator()
      + "        \"color\" : {" + System.lineSeparator()
      + "          \"red\" : 1.0," + System.lineSeparator()
      + "          \"green\" : 0.0," + System.lineSeparator()
      + "          \"blue\" : 0.0," + System.lineSeparator()
      + "          \"opacity\" : 1.0" + System.lineSeparator()
      + "        }" + System.lineSeparator()
      + "      }," + System.lineSeparator()
      + "      \"start-time\" : \"09:00\"," + System.lineSeparator()
      + "      \"duration\" : \"10:00\"" + System.lineSeparator()
      + "    } ]," + System.lineSeparator()
      + "    \"tasks\" : [ {" + System.lineSeparator()
      + "      \"name\" : \"Task 1\"," + System.lineSeparator()
      + "      \"description\" : \"Task 1 description\"," + System.lineSeparator()
      + "      \"dayOfWeek\" : \"WED\"," + System.lineSeparator()
      + "      \"category\" : {" + System.lineSeparator()
      + "        \"name\" : \"Category 1\"," + System.lineSeparator()
      + "        \"color\" : {" + System.lineSeparator()
      + "          \"red\" : 1.0," + System.lineSeparator()
      + "          \"green\" : 0.0," + System.lineSeparator()
      + "          \"blue\" : 0.0," + System.lineSeparator()
      + "          \"opacity\" : 1.0" + System.lineSeparator()
      + "        }" + System.lineSeparator()
      + "      }," + System.lineSeparator()
      + "      \"completed\" : false" + System.lineSeparator()
      + "    } ]"  + System.lineSeparator()
      + "  }, {" + System.lineSeparator()
      + "    \"day-of-week\" : \"TUE\"," + System.lineSeparator()
      + "    \"events\" : [ {" + System.lineSeparator()
      + "      \"name\" : \"Event 1\"," + System.lineSeparator()
      + "      \"description\" : null," + System.lineSeparator()
      + "      \"day-of-week\" : \"MON\"," + System.lineSeparator()
      + "      \"category\" : null," + System.lineSeparator()
      + "      \"start-time\" : \"09:00\"," + System.lineSeparator()
      + "      \"duration\" : \"10:00\"" + System.lineSeparator()
      + "    } ]," + System.lineSeparator()
      + "    \"tasks\" : [ ]" + System.lineSeparator()
      + "  }, {" + System.lineSeparator()
      + "    \"day-of-week\" : \"WED\"," + System.lineSeparator()
      + "    \"events\" : [ ]," + System.lineSeparator()
      + "    \"tasks\" : [ ]" + System.lineSeparator()
      + "  }, {" + System.lineSeparator()
      + "    \"day-of-week\" : \"THU\"," + System.lineSeparator()
      + "    \"events\" : [ ]," + System.lineSeparator()
      + "    \"tasks\" : [ ]" + System.lineSeparator()
      + "  }, {" + System.lineSeparator()
      + "    \"day-of-week\" : \"FRI\"," + System.lineSeparator()
      + "    \"events\" : [ ]," + System.lineSeparator()
      + "    \"tasks\" : [ {" + System.lineSeparator()
      + "      \"name\" : \"Task 2\"," + System.lineSeparator()
      + "      \"description\" : null," + System.lineSeparator()
      + "      \"dayOfWeek\" : \"THU\"," + System.lineSeparator()
      + "      \"category\" : null," + System.lineSeparator()
      + "      \"completed\" : true" + System.lineSeparator()
      + "    } ]" + System.lineSeparator()
      + "  }, {" + System.lineSeparator()
      + "    \"day-of-week\" : \"SAT\"," + System.lineSeparator()
      + "    \"events\" : [ ]," + System.lineSeparator()
      + "    \"tasks\" : [ ]" + System.lineSeparator()
      + "  } ]," + System.lineSeparator()
      + "  \"max-event-warn-limit\" : 5," + System.lineSeparator()
      + "  \"max-task-warn-limit\" : 10," + System.lineSeparator()
      + "  \"categories\" : [ {" + System.lineSeparator()
      + "    \"name\" : \"Category 1\"," + System.lineSeparator()
      + "    \"color\" : {" + System.lineSeparator()
      + "      \"red\" : 1.0," + System.lineSeparator()
      + "      \"green\" : 0.0," + System.lineSeparator()
      + "      \"blue\" : 0.0," + System.lineSeparator()
      + "      \"opacity\" : 1.0" + System.lineSeparator()
      + "    }" + System.lineSeparator()
      + "  }, {" + System.lineSeparator()
      + "    \"name\" : \"Category 2\"," + System.lineSeparator()
      + "    \"color\" : {" + System.lineSeparator()
      + "      \"red\" : 0.0," + System.lineSeparator()
      + "      \"green\" : 0.501960813999176," + System.lineSeparator()
      + "      \"blue\" : 0.0," + System.lineSeparator()
      + "      \"opacity\" : 1.0" + System.lineSeparator()
      + "    }" + System.lineSeparator()
      + "  } ]," + System.lineSeparator()
      + "  \"notes\" : {" + System.lineSeparator()
      + "    \"note\" : \"\"" + System.lineSeparator()
      + "  }," + System.lineSeparator()
      + "  \"password\" : \"password\"" + System.lineSeparator()
      + "}";

  private JournalWeek testJournal;
  private JournalWeek testJournal2;

  private Event event1;
  private Event event2;
  private Task task1;
  private Task task2;
  private Category category1;
  private Category category2;

  /**
   * Set up testing for BujoUtil test.
   */
  @BeforeEach
  public void setup() {
    testJournal = new JournalWeek();
    testJournal2 = new JournalWeek();

    testJournal.setPassword("password");

    category1 = new Category("Category 1", Color.RED);
    category2 = new Category("Category 2", Color.GREEN);

    event1 = new Event("Event 1", "Event 1 description", DayOfWeek.MON,
        category1, LocalTime.of(9, 0), LocalTime.of(10, 0));
    event2 = new Event("Event 1", null, DayOfWeek.MON,
        null, LocalTime.of(9, 0), LocalTime.of(10, 0));

    task1 = new Task("Task 1", "Task 1 description", DayOfWeek.WED,
        category1, false);
    task2 = new Task("Task 2", null, DayOfWeek.THU,
        null, true);
  }

  /**
   * Clean the files after test.
   */
  @AfterEach
  public void cleanup() {
    // Delete the test file if it exists
    File file = new File(TEST_FILE_PATH);
    if (file.exists()) {
      file.delete();
    }
  }

  /**
   * Test the saveToFile method of the BujoUtils class.
   *
   * @throws IOException Failed to save the file
   */
  @Test
  public void testSaveToFile() throws IOException {
    testJournal.addEvent(event1, DayOfWeek.MON);
    testJournal.addEvent(event2, DayOfWeek.TUE);
    testJournal.addTask(task1, DayOfWeek.MON);
    testJournal.addTask(task2, DayOfWeek.FRI);

    testJournal.setMaxEventWarnLimit(5);
    testJournal.setMaxTaskWarnLimit(10);
    testJournal.addCategory(category1);
    testJournal.addCategory(category2);

    BujoUtils.saveToFile(TEST_FILE_PATH, testJournal);

    Path path = Paths.get(TEST_FILE_PATH);
    assertTrue(Files.exists(path));

    String fileContent = Files.readString(path);
    assertEquals(TEST_FILE_CONTENT, fileContent);
  }

  /**
   * Test the openFile method of the BujoUtils class.
   */
  @Test
  public void testOpenFile() {
    testJournal.addEvent(event1, DayOfWeek.MON);
    testJournal.addEvent(event2, DayOfWeek.TUE);
    testJournal.addTask(task1, DayOfWeek.MON);
    testJournal.addTask(task2, DayOfWeek.FRI);

    testJournal.setMaxEventWarnLimit(5);
    testJournal.setMaxTaskWarnLimit(10);
    testJournal.addCategory(category1);
    testJournal.addCategory(category2);

    BujoUtils.saveToFile(TEST_FILE_PATH, testJournal);
    JournalWeek loadedJournal = BujoUtils.openFile(TEST_FILE_PATH);

    assertEquals(testJournal.getName(), loadedJournal.getName());
    assertEquals(testJournal.getMaxEventWarnLimit(), loadedJournal.getMaxEventWarnLimit());
    assertEquals(testJournal.getMaxTaskWarnLimit(), loadedJournal.getMaxTaskWarnLimit());
    assertEquals(testJournal.getCategories().size(), loadedJournal.getCategories().size());
    assertEquals(testJournal.getNotes().getNote(), loadedJournal.getNotes().getNote());

    BujoUtils.saveToFile(TEST_FILE_PATH, testJournal2);

    JournalWeek loadedJournal2 = BujoUtils.openFile(TEST_FILE_PATH);

    assertEquals(testJournal2.getName(), loadedJournal2.getName());
    assertEquals(testJournal2.getMaxEventWarnLimit(), loadedJournal2.getMaxEventWarnLimit());
    assertEquals(testJournal2.getMaxTaskWarnLimit(), loadedJournal2.getMaxTaskWarnLimit());
    assertEquals(testJournal2.getCategories().size(), loadedJournal2.getCategories().size());
    assertEquals(testJournal2.getNotes().getNote(), loadedJournal2.getNotes().getNote());
  }

  /**
   * Test the openFile method with empty categories of the BujoUtils class.
   */
  @Test
  public void testEmptyCategory() {
    BujoUtils.saveToFile(TEST_FILE_PATH, testJournal);
    JournalWeek loadedJournal = BujoUtils.openFile(TEST_FILE_PATH);
    assertEquals(new ArrayList<>(), loadedJournal.getCategories());
  }

  /**
   * Test the invalid saveToFile method of the BujoUtils class.
   */
  @Test
  public void testSaveToFileWithInvalidFilePath() {
    assertThrows(IllegalStateException.class, () ->
        BujoUtils.saveToFile("src/invalid/journal.bujo", testJournal));
  }

  /**
   * Test the invalid openFile method of the BujoUtils class.
   */
  @Test
  public void testOpenFileWithInvalidFilePath() {
    assertThrows(IllegalStateException.class, () ->
        BujoUtils.openFile("src/invalid/journal.bujo"));
  }
}
