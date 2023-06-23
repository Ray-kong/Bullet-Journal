package cs3500.pa05.model.data;

/**
 * Class that represent a Weekly Overview of the Journal.
 */
public class WeeklyOverview {
  private final int totalEvents;
  private final int totalTasks;
  private final int completedTasks;

  /**
   * Default Constructor
   */
  public WeeklyOverview() {
    this.totalEvents = 0;
    this.totalTasks = 0;
    this.completedTasks = 0;
  }

  /**
   * Convenient Constructor
   *
   * @param totalEvents    Total Events count
   * @param totalTasks     Total Tasks count
   * @param completedTasks Total completed Tasks count
   */
  public WeeklyOverview(int totalEvents, int totalTasks, int completedTasks) {
    this.totalEvents = totalEvents;
    this.totalTasks = totalTasks;
    this.completedTasks = completedTasks;
  }

  /**
   * Gets the total number of events.
   *
   * @return the total number of events.
   */
  public int getTotalEvents() {
    return this.totalEvents;
  }

  /**
   * Gets the total number of tasks.
   *
   * @return the total number of tasks.
   */
  public int getTotalTasks() {
    return this.totalTasks;
  }

  /**
   * Gets the total number of completed tasks.
   *
   * @return the total number of completed tasks.
   */
  public int getCompletedTasks() {
    return this.completedTasks;
  }
}
