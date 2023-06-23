package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Event class.
 */
public class EventTest {
  private Event event;

  /**
   * Sets up the testing environment before each test case.
   */
  @BeforeEach
  public void setup() {
    String name = "Event";
    String description = "Description";
    DayOfWeek dayOfWeek = DayOfWeek.MON;
    Category category = new Category("Category", Color.RED);
    LocalTime startTime = LocalTime.of(9, 0);
    LocalTime duration = LocalTime.of(1, 30);

    event = new Event(name, description, dayOfWeek, category, startTime, duration);
  }

  /**
   * Test the getStartTime method of the Event class.
   */
  @Test
  public void testGetStartTime() {
    assertEquals(LocalTime.of(9, 0), event.getStartTime());
  }

  /**
   * Test the getDuration method of the Event class.
   */
  @Test
  public void testGetDuration() {
    assertEquals(LocalTime.of(1, 30), event.getDuration());
  }
}
