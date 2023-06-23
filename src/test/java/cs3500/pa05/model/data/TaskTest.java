package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Task class.
 */
public class TaskTest {
  private String name;
  private String description;
  private DayOfWeek dayOfWeek;
  private Category category;
  private boolean isComplete;

  /**
   * Sets up the testing environment before each test case.
   */
  @BeforeEach
  public void setup() {
    name = "Task";
    description = "Description";
    dayOfWeek = DayOfWeek.MON;
    category = new Category("Category", Color.RED);
    isComplete = true;
  }

  /**
   * Test the default Constructor of the Task class.
   */
  @Test
  public void testDefaultConstructor() {
    Task task = new Task(name, description, dayOfWeek, category);
    assertFalse(task.getIsComplete());
  }

  /**
   * Test the convenient Constructor of the Task class.
   */
  @Test
  public void testConstructorWithIsComplete() {
    Task task = new Task(name, description, dayOfWeek, category, isComplete);
    assertTrue(task.getIsComplete());
  }

  /**
   * Test the setIsComplete method of the Task class.
   */
  @Test
  public void testSetIsComplete() {
    Task task = new Task(name, description, dayOfWeek, category, false);
    task.setIsComplete(true);
    assertTrue(task.getIsComplete());
  }
}
