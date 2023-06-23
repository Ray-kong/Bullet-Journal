package cs3500.pa05.model.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represent a day of the Journal.
 */
public class JournalDay {
  private final List<Event> events;
  private final List<Task> tasks;
  private int completedTasks;

  /**
   * Default Constructor
   */
  public JournalDay() {
    this.events = new ArrayList<>();
    this.tasks = new ArrayList<>();
    this.completedTasks = 0;
  }

  /**
   * Convenient Constructor
   * For setting the Journal with `load` functions
   *
   * @param events List of Events
   * @param tasks  List of Tasks
   */
  public JournalDay(List<Event> events, List<Task> tasks) {
    this.events = new ArrayList<>(Objects.requireNonNullElseGet(events, ArrayList::new));
    this.tasks = new ArrayList<>(Objects.requireNonNullElseGet(tasks, ArrayList::new));
    updateCompletedTasks();
  }

  /**
   * Update the completed tasks count of this Day
   */
  public void updateCompletedTasks() {
    int completedTasks = 0;
    for (Task task : tasks) {
      if (task.getIsComplete()) {
        completedTasks++;
      }
    }
    this.completedTasks = completedTasks;
  }

  /**
   * Gets all the Events from this JournalDay
   *
   * @return List of Events
   */
  public List<Event> getEvents() {
    return events;
  }

  /**
   * Gets all the tasks from this JournalDay
   *
   * @return List of Tasks
   */
  public List<Task> getTasks() {
    return tasks;
  }

  /**
   * Gets the completed Tasks count of this Day
   *
   * @return Completed Tasks count
   */
  public int getCompletedTasks() {
    return completedTasks;
  }

  /**
   * Adds an event to the Day of the Journal
   *
   * @param event Event of the Journal
   */
  public void addEvent(Event event) {
    this.events.add(event);
  }

  /**
   * Adds a task to the Day of the Journal
   *
   * @param task Task of the Journal
   */
  public void addTask(Task task) {
    this.tasks.add(task);
  }

  /**
   * Determines if there are too many activities
   * (either events or tasks) than the maximum warning limit
   *
   * @param activities List of activities (either events or tasks)
   * @param maxWarnLimit Maximum warning limit
   * @return If the number of activities is larger than the max warning limit
   */
  public boolean overCommitted(List<? extends Activity> activities, int maxWarnLimit) {
    return activities.size() > maxWarnLimit;
  }
}
