package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for WeeklyOverview.
 * It checks if WeeklyOverview's methods work as expected.
 */
class WeeklyOverviewTest {

  private WeeklyOverview weeklyOverview;

  /**
   * Setup method to initialize common test data.
   */
  @BeforeEach
  public void setUp() {
    weeklyOverview = new WeeklyOverview(2, 5, 3);
  }

  /**
   * Test for getTotalEvents method.
   */
  @Test
  public void testGetTotalEvents() {
    assertEquals(2, weeklyOverview.getTotalEvents());
  }

  /**
   * Test for getTotalTasks method.
   */
  @Test
  public void testGetTotalTasks() {
    assertEquals(5, weeklyOverview.getTotalTasks());
  }

  /**
   * Test for getCompletedTasks method.
   */
  @Test
  public void testGetCompletedTasks() {
    assertEquals(3, weeklyOverview.getCompletedTasks());
  }
}
