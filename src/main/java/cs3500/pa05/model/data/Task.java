package cs3500.pa05.model.data;

/**
 * Class that represents a task.
 */
public class Task extends Activity {
  private boolean isComplete;

  /**
   * Default Constructor
   *
   * @param name        Name of the Task
   * @param description Description of the Task
   * @param dayOfWeek   Day of Week of the Task
   * @param category    Category of the Task
   */
  public Task(String name, String description, DayOfWeek dayOfWeek, Category category) {
    super(name, description, dayOfWeek, category);
    this.isComplete = false;
  }

  /**
   * Convenient Constructor
   *
   * @param name        Name of the Task
   * @param description Description of the Task
   * @param dayOfWeek   Day of Week of the Task
   * @param category    Category of the Task
   * @param isComplete  Status of the Task
   */
  public Task(String name, String description, DayOfWeek dayOfWeek, Category category,
              boolean isComplete) {
    super(name, description, dayOfWeek, category);
    this.isComplete = isComplete;
  }

  /**
   * Gets the status of this Task
   *
   * @return If the Task has been completed
   */
  public boolean getIsComplete() {
    return this.isComplete;
  }

  /**
   * Sets the status of this Task
   *
   * @param isComplete If the Task has been completed
   */
  public void setIsComplete(boolean isComplete) {
    this.isComplete = isComplete;
  }
}
