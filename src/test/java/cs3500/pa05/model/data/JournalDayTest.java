package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing JournalDay class.
 */
public class JournalDayTest {
  private JournalDay journalDay;
  private Event testEvent;
  private Task testTask;
  private Category testCategory;

  /**
   * This method initializes the objects required for each test.
   */
  @BeforeEach
  public void setup() {
    journalDay = new JournalDay();
    testCategory = new Category("Test Category", Color.BLUE);
    testEvent = new Event("Test Event", "Testing", DayOfWeek.MON,
        testCategory, LocalTime.of(10, 0), LocalTime.of(1, 0));
    testTask = new Task("Test Task", "Testing", DayOfWeek.MON, testCategory);
  }

  /**
   * This test checks the default constructor of JournalDay class.
   * Ensures the returned JournalDay object is not null, has empty events and tasks lists,
   * and the count of completed tasks is zero.
   */
  @Test
  public void testDefaultConstructor() {
    JournalDay journalDayDefault = new JournalDay();

    assertNotNull(journalDayDefault.getEvents());
    assertNotNull(journalDayDefault.getTasks());
    assertEquals(0, journalDayDefault.getCompletedTasks());
    assertTrue(journalDayDefault.getEvents().isEmpty());
    assertTrue(journalDayDefault.getTasks().isEmpty());
  }

  /**
   * This test verifies the convenient constructor of JournalDay class.
   * Ensures the returned JournalDay object correctly contains the provided events and tasks,
   * and the count of completed tasks is zero.
   */
  @Test
  public void testConvenientConstructor() {
    List<Event> events = new ArrayList<>();
    events.add(testEvent);
    List<Task> tasks = new ArrayList<>();
    tasks.add(testTask);

    JournalDay journalDayConvenient = new JournalDay(events, tasks);

    assertNotNull(journalDayConvenient.getEvents());
    assertNotNull(journalDayConvenient.getTasks());
    assertEquals(0, journalDayConvenient.getCompletedTasks());
    assertTrue(journalDayConvenient.getEvents().contains(testEvent));
    assertTrue(journalDayConvenient.getTasks().contains(testTask));
  }

  /**
   * This test checks the functionality of addEvent method.
   * Ensures the event is properly added to the events list.
   */
  @Test
  public void testAddEvent() {
    journalDay.addEvent(testEvent);
    List<Event> events = journalDay.getEvents();
    assertTrue(events.contains(testEvent));
  }

  /**
   * This test checks the functionality of addTask method.
   * Ensures the task is properly added to the tasks list.
   */
  @Test
  public void testAddTask() {
    journalDay.addTask(testTask);
    List<Task> tasks = journalDay.getTasks();
    assertTrue(tasks.contains(testTask));
  }

  /**
   * This test checks the functionality of updateCompletedTasks method.
   * Ensures the completed tasks count is updated correctly.
   */
  @Test
  public void testUpdateCompletedTasks() {
    Task task1 = new Task(
        "Task1", "Testing", DayOfWeek.MON, testCategory, true);
    Task task2 = new Task(
        "Task2", "Testing", DayOfWeek.MON, testCategory, false);
    Task task3 = new Task(
        "Task3", "Testing", DayOfWeek.MON, testCategory, true);

    journalDay.addTask(task1);
    journalDay.addTask(task2);
    journalDay.addTask(task3);

    journalDay.updateCompletedTasks();
    assertEquals(2, journalDay.getCompletedTasks());
  }

  /**
   * This test checks the functionality of overCommitted method for events.
   * It adds exactly the maximum warning limit number of events to the journalDay.
   * Then it ensures the method correctly determines
   * that the number of events is not larger than the max warning limit.
   */
  @Test
  public void testOverCommittedEqualLimitEvent() {
    for (int i = 0; i < 10; i++) {
      journalDay.addEvent(testEvent);
    }
    assertFalse(journalDay.overCommitted(journalDay.getEvents(), 10));
  }

  /**
   * This test checks the functionality of overCommitted method for events.
   * It adds fewer than the maximum warning limit number of events to the journalDay.
   * Then it ensures the method correctly determines
   * that the number of events is not larger than the max warning limit.
   */
  @Test
  public void testOverCommittedUnderLimitEvent() {
    for (int i = 0; i < 9; i++) {
      journalDay.addEvent(testEvent);
    }
    assertFalse(journalDay.overCommitted(journalDay.getEvents(), 10));
  }

  /**
   * This test checks the functionality of overCommitted method for tasks.
   * It adds exactly the maximum warning limit number of tasks to the journalDay.
   * Then it ensures the method correctly determines
   * that the number of tasks is not larger than the max warning limit.
   */
  @Test
  public void testOverCommittedEqualLimitTask() {
    for (int i = 0; i < 10; i++) {
      journalDay.addTask(testTask);
    }
    assertFalse(journalDay.overCommitted(journalDay.getTasks(), 10));
  }

  /**
   * This test checks the functionality of overCommitted method for tasks.
   * It adds fewer than the maximum warning limit number of tasks to the journalDay.
   * Then it ensures the method correctly determines
   * that the number of tasks is not larger than the max warning limit.
   */
  @Test
  public void testOverCommittedUnderLimitTask() {
    for (int i = 0; i < 9; i++) {
      journalDay.addTask(testTask);
    }
    assertFalse(journalDay.overCommitted(journalDay.getTasks(), 10));
  }

  /**
   * This test checks the functionality of overCommitted method for tasks.
   * Ensures the method correctly determines when there are more tasks than a given limit.
   */
  @Test
  public void testOverCommittedTask() {
    for (int i = 0; i < 11; i++) {
      journalDay.addTask(testTask);
    }
    assertTrue(journalDay.overCommitted(journalDay.getTasks(), 10));
  }
}
