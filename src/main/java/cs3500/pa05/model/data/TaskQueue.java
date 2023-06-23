package cs3500.pa05.model.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represent a Task Queue of the Journal.
 */
public class TaskQueue {
  private final List<Task> tasks;

  /**
   * Default Constructor
   */
  public TaskQueue() {
    this.tasks = new ArrayList<>();
  }

  /**
   * Convenient Constructor
   * For setting the Task Queue with `load` functions
   *
   * @param tasks List of tasks
   */
  public TaskQueue(List<Task> tasks) {
    this.tasks = Objects.requireNonNullElseGet(tasks, ArrayList::new);
  }

  /**
   * Gets the Tasks of the Journal
   *
   * @return Tasks of the Journal
   */
  public List<Task> getTasks() {
    return tasks;
  }

  /**
   * Clear the current Task Queue
   */
  public void clearQueue() {
    this.tasks.clear();
  }

  /**
   * Adds a task to the Task Queue
   *
   * @param task Task of the Journal
   */
  public void addTask(Task task) {
    this.tasks.add(task);
  }

  /**
   * Adds all the Tasks from a list of Tasks to the Task Queue
   *
   * @param tasks List of Tasks
   */
  public void addAllTask(List<Task> tasks) {
    this.tasks.addAll(tasks);
  }
}
