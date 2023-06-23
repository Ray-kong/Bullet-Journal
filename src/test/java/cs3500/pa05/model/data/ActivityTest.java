package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tester class for Activity.
 */
public class ActivityTest {
  private Event activity;

  /**
   * Sets up common objects used in all test methods.
   */
  @BeforeEach
  public void setup() {
    String name = "Activity";
    String description = "Description";
    DayOfWeek dayOfWeek = DayOfWeek.MON;
    Category category = new Category("Category", Color.RED);
    LocalTime time = LocalTime.of(1, 20);
    activity = new Event(name, description, dayOfWeek, category, time, time);
  }

  /**
   * Test the getName method of the Activity class.
   */
  @Test
  public void testGetName() {
    assertEquals("Activity", activity.getName());
  }

  /**
   * Test the getDescription method of the Activity class.
   */
  @Test
  public void testGetDescription() {
    assertEquals("Description", activity.getDescription());
  }

  /**
   * Test the getDayOfWeek method of the Activity class.
   */
  @Test
  public void testGetDayOfWeek() {
    assertEquals(DayOfWeek.MON, activity.getDayOfWeek());
  }

  /**
   * Test the getCategory method of the Activity class.
   */
  @Test
  public void testGetCategory() {
    assertEquals(new Category("Category", Color.RED), activity.getCategory());
  }

  /**
   * Test the editCategory method of the Activity class.
   */
  @Test
  public void testEditCategory() {
    Category newCategory = new Category("Category2", Color.BLUE);
    activity.setCategory(newCategory);
    assertEquals(newCategory, activity.getCategory());
  }
}
