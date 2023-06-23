package cs3500.pa05.model.data;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class that represent a whole week of the Journal.
 */
public class JournalWeek {
  private String name;
  private final Map<DayOfWeek, JournalDay> journalDays;
  private int maxEventWarnLimit;
  private int maxTaskWarnLimit;
  private final TaskQueue taskQueue;
  private List<Category> categories;
  private final Notes notes;
  private WeeklyOverview weeklyOverview;
  private String password;

  /**
   * Default Constructor
   */
  public JournalWeek() {
    journalDays = initializeJournalDays();
    this.name = "Journal";
    this.maxEventWarnLimit = 8;
    this.maxTaskWarnLimit = 8;
    this.taskQueue = new TaskQueue();
    this.categories = new ArrayList<>();
    this.notes = new Notes();
    this.weeklyOverview = new WeeklyOverview();
    this.password = "";
  }

  /**
   * Convenient Constructor
   * For setting the Journal with `load` functions
   *
   * @param name              Name of the Week
   * @param journalDays       Days of the Journal
   * @param maxEventWarnLimit Max number of event warnings
   * @param maxTaskWarnLimit  Max number of task warnings
   * @param categories        Existed Categories
   * @param notes             Notes of the Journal
   * @param password          Password of the Journal
   */
  public JournalWeek(String name, Map<DayOfWeek, JournalDay> journalDays, int maxEventWarnLimit,
                     int maxTaskWarnLimit, List<Category> categories, Notes notes,
                     String password) {
    this.name = name;
    this.journalDays = journalDays;
    this.maxEventWarnLimit = maxEventWarnLimit;
    this.maxTaskWarnLimit = maxTaskWarnLimit;
    this.taskQueue = new TaskQueue();
    updateTaskQueue();
    this.categories = Objects.requireNonNullElseGet(categories, ArrayList::new);
    this.notes = notes == null ? new Notes() : notes;
    updateWeeklyOverview();
    this.password = password;
  }

  /**
   * Initialize the journal for each day
   *
   * @return Map of the day of week and the Journal to that day
   */
  private Map<DayOfWeek, JournalDay> initializeJournalDays() {
    Map<DayOfWeek, JournalDay> days = new EnumMap<>(DayOfWeek.class);
    for (DayOfWeek day : DayOfWeek.values()) {
      days.put(day, new JournalDay());
    }
    return days;
  }

  /**
   * Gets the name of this JournalWeek
   *
   * @return Name of the JournalWeek
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the Journal of each day from this JournalWeek
   *
   * @return Journal of each day
   */
  public Map<DayOfWeek, JournalDay> getJournalDays() {
    return journalDays;
  }

  /**
   * Gets the max event number warn limit of this JournalWeek
   *
   * @return Max event number warn limit of the JournalWeek
   */
  public int getMaxEventWarnLimit() {
    return maxEventWarnLimit;
  }

  /**
   * Gets the max task number warn limit of this JournalWeek
   *
   * @return Max task number warn limit of the JournalWeek
   */
  public int getMaxTaskWarnLimit() {
    return maxTaskWarnLimit;
  }

  /**
   * Gets the task queue of this JournalWeek
   *
   * @return Task Queue of the JournalWeek
   */
  public TaskQueue getTaskQueue() {
    return taskQueue;
  }

  /**
   * Gets the categories of this JournalWeek
   *
   * @return Categories of the JournalWeek
   */
  public List<Category> getCategories() {
    return categories;
  }

  /**
   * Gets the Notes of this JournalWeek
   *
   * @return Notes of the JournalWeek
   */
  public Notes getNotes() {
    return notes;
  }

  /**
   * A getter for the WeeklyOverview
   *
   * @return the WeeklyOverview
   */
  public WeeklyOverview getWeeklyOverview() {
    updateWeeklyOverview();
    return this.weeklyOverview;
  }

  /**
   * Gets the password of the Journal
   *
   * @return Password of the Journal
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets a name to this JournalWeek
   *
   * @param name Name of the JournalWeek
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the max number of event warnings
   *
   * @param maxEventWarnLimit Max number of event warnings
   */
  public void setMaxEventWarnLimit(int maxEventWarnLimit) {
    if (maxEventWarnLimit <= 0) {
      throw new IllegalArgumentException("Max warning limits must be positive integers.");
    }
    this.maxEventWarnLimit = maxEventWarnLimit;
  }

  /**
   * Sets the max number of task warnings
   *
   * @param maxTaskWarnLimit Max number of task warnings
   */
  public void setMaxTaskWarnLimit(int maxTaskWarnLimit) {
    if (maxTaskWarnLimit <= 0) {
      throw new IllegalArgumentException("Max warning limits must be positive integers.");
    }
    this.maxTaskWarnLimit = maxTaskWarnLimit;
  }

  /**
   * Sets the categories for the Journal
   *
   * @param categories Existed Categories
   */
  public void setCategories(List<Category> categories) {
    this.categories = Objects.requireNonNullElseGet(categories, ArrayList::new);
  }

  /**
   * Sets the password of the Journal
   *
   * @param password Password of the Journal
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Adds the Event to the specific day of weed
   *
   * @param event     Event of the Journal
   * @param dayOfWeek Day of Week of the Event
   */
  public void addEvent(Event event, DayOfWeek dayOfWeek) {
    Objects.requireNonNull(event, "Event cannot be null");
    Objects.requireNonNull(dayOfWeek, "DayOfWeek cannot be null");
    this.journalDays.get(dayOfWeek).addEvent(event);
  }

  /**
   * Adds the Task to the specific day of weed
   *
   * @param task      Task of the Journal
   * @param dayOfWeek Day of Week of the Event
   */
  public void addTask(Task task, DayOfWeek dayOfWeek) {
    Objects.requireNonNull(task, "Task cannot be null");
    Objects.requireNonNull(dayOfWeek, "DayOfWeek cannot be null");
    this.journalDays.get(dayOfWeek).addTask(task);
    this.taskQueue.addTask(task); // Add the task to the Task Queue
  }

  /**
   * Update the Task Queue with the current JournalDays
   */
  public void updateTaskQueue() {
    taskQueue.clearQueue();
    for (JournalDay journalDay : journalDays.values()) {
      journalDay.updateCompletedTasks();
      taskQueue.addAllTask(journalDay.getTasks());
    }
  }

  /**
   * Adds the category to the List
   *
   * @param category Category of Activity
   */
  public void addCategory(Category category) {
    if (!categories.contains(category)) {
      categories.add(category);
    } else {
      throw new IllegalArgumentException("This category already exists");
    }
  }

  /**
   * Update the Weekly Overview with the current JournalDays
   */
  public void updateWeeklyOverview() {
    int totalEvents = 0;
    int totalTasks = 0;
    int completedTasks = 0;

    for (JournalDay journalDay : journalDays.values()) {
      journalDay.updateCompletedTasks();
      totalEvents += journalDay.getEvents().size();
      totalTasks += journalDay.getTasks().size();
      completedTasks += journalDay.getCompletedTasks();
    }

    weeklyOverview = new WeeklyOverview(totalEvents, totalTasks, completedTasks);
  }
}
