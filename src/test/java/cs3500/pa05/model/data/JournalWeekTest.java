package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the JournalWeek class.
 */
class JournalWeekTest {
  JournalWeek journalWeek;
  Task task;
  Event event;
  Category category;
  DayOfWeek dayOfWeek;

  /**
   * Set up the testing environment before each test case.
   */
  @BeforeEach
  public void setUp() {
    journalWeek = new JournalWeek();
    category = new Category("Test Category", Color.RED);
    task = new Task("Task1", "Test Task", DayOfWeek.MON, category);
    event = new Event("Event1", "Test Event", DayOfWeek.MON,
        category, LocalTime.of(10, 0), LocalTime.of(1, 0));
    dayOfWeek = DayOfWeek.MON;
  }

  /**
   * Test the addTask method of the JournalWeek class.
   */
  @Test
  public void testAddTask() {
    assertEquals(0, journalWeek.getJournalDays().get(dayOfWeek).getTasks().size());
    assertEquals(0, journalWeek.getTaskQueue().getTasks().size());

    journalWeek.addTask(task, dayOfWeek);

    assertEquals(1, journalWeek.getJournalDays().get(dayOfWeek).getTasks().size());
    assertTrue(journalWeek.getJournalDays().get(dayOfWeek).getTasks().contains(task));
    assertEquals(1, journalWeek.getTaskQueue().getTasks().size());
    assertTrue(journalWeek.getTaskQueue().getTasks().contains(task));
  }

  /**
   * Test the addEvent method of the JournalWeek class.
   */
  @Test
  public void testAddEvent() {
    assertEquals(0, journalWeek.getJournalDays().get(dayOfWeek).getEvents().size());

    journalWeek.addEvent(event, dayOfWeek);

    assertEquals(1, journalWeek.getJournalDays().get(dayOfWeek).getEvents().size());
    assertTrue(journalWeek.getJournalDays().get(dayOfWeek).getEvents().contains(event));
  }

  /**
   * Test the setName method of the JournalWeek class.
   */
  @Test
  public void testSetName() {
    String newName = "New Name";
    journalWeek.setName(newName);
    assertEquals(newName, journalWeek.getName());
  }

  /**
   * Test the getNotes method of the JournalWeek class.
   */
  @Test
  public void testGetNotes() {
    Notes notes = new Notes();
    notes.setNote("This is a note.");
    Map<DayOfWeek, JournalDay> days = new EnumMap<>(DayOfWeek.class);
    for (DayOfWeek day : DayOfWeek.values()) {
      days.put(day, new JournalDay());
    }
    List<Category> categories = new ArrayList<>();
    categories.add(category);

    journalWeek = new JournalWeek("name", days, 8, 8,
        categories, notes, "password");
    assertEquals("This is a note.", journalWeek.getNotes().getNote());

    journalWeek = new JournalWeek("name", days, 8, 8,
        categories, null, "password");
    assertEquals("", journalWeek.getNotes().getNote());
  }

  /**
   * Test the setMaxEventWarnLimit method of the JournalWeek class with valid inputs.
   */
  @Test
  public void testValidSetMaxEventWarning() {
    journalWeek.setMaxEventWarnLimit(5);
    assertEquals(journalWeek.getMaxEventWarnLimit(), 5);
  }

  /**
   * Test the setMaxEventWarnLimit method of the JournalWeek class with invalid inputs.
   */
  @Test
  public void testInvalidSetMaxEventWarning() {
    assertThrows(IllegalArgumentException.class, () -> journalWeek.setMaxEventWarnLimit(-1));
  }

  /**
   * Tests the setMaxTaskWarnLimit method of the JournalWeek class with valid inputs.
   */
  @Test
  public void testValidSetMaxTaskWarning() {
    journalWeek.setMaxTaskWarnLimit(5);
    assertEquals(journalWeek.getMaxTaskWarnLimit(), 5);
  }

  /**
   * Tests the setMaxTaskWarnLimit method of the JournalWeek class with invalid inputs.
   */
  @Test
  public void testInvalidSetMaxTaskWarning() {
    assertThrows(IllegalArgumentException.class, () -> journalWeek.setMaxTaskWarnLimit(-1));
  }

  /**
   * Tests the addCategory method of the JournalWeek class.
   */
  @Test
  public void testAddCategory() {
    Category newCategory = new Category("New Category", Color.BLUE);
    journalWeek.addCategory(newCategory);
    assertTrue(journalWeek.getCategories().contains(newCategory));
  }

  /**
   * Tests the setCategories method of the JournalWeek class.
   */
  @Test
  public void testSetCategories() {
    List<Category> categories = new ArrayList<>();
    category = new Category("New Category", Color.BLUE);
    categories.add(category);
    journalWeek.setCategories(categories);
    assertTrue(journalWeek.getCategories().contains(category));
  }

  /**
   * Test the addCategory method of the JournalWeek class
   * when attempting to add an existing category.
   */
  @Test
  public void testAddExistingCategory() {
    journalWeek.addCategory(new Category("New Category", Color.BLUE));
    assertThrows(IllegalArgumentException.class,
        () -> journalWeek.addCategory(new Category("New Category", Color.BLUE)));

  }

  /**
   * Tests the updateTaskQueue method of the JournalWeek class.
   */
  @Test
  public void testUpdateTaskQueue() {
    Task task1 = new Task("Task1", "Test Task 1", DayOfWeek.MON, category);
    Task task2 = new Task("Task2", "Test Task 2", DayOfWeek.TUE, category);

    journalWeek.addTask(task1, DayOfWeek.MON);
    journalWeek.addTask(task2, DayOfWeek.TUE);
    journalWeek.updateTaskQueue();

    assertEquals(2, journalWeek.getTaskQueue().getTasks().size());
    assertTrue(journalWeek.getTaskQueue().getTasks().contains(task1));
    assertTrue(journalWeek.getTaskQueue().getTasks().contains(task2));
  }

  /**
   * Tests the getWeeklyOverview method of the JournalWeek class.
   */
  @Test
  public void testGetWeeklyOverview() {
    journalWeek.addTask(task, DayOfWeek.MON);
    task.setIsComplete(true);
    journalWeek.addEvent(event, dayOfWeek);

    assertEquals(1, journalWeek.getWeeklyOverview().getTotalTasks());
    assertEquals(1, journalWeek.getWeeklyOverview().getTotalEvents());
    assertEquals(1, journalWeek.getWeeklyOverview().getCompletedTasks());
  }
}
