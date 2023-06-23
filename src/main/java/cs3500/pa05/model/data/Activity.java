package cs3500.pa05.model.data;

import java.util.Objects;

/**
 * Class that represents a common Activity of the Journal.
 */
public abstract class Activity {
  /**
   * Name of the Activity
   */
  protected final String name;
  /**
   * Description of the Activity
   */
  protected final String description;
  /**
   * Day of Week of the Activity
   */
  protected final DayOfWeek dayOfWeek;
  /**
   * Category of the Activity
   */
  protected Category category;

  /**
   * Default Constructor
   *
   * @param name        Name of the Activity
   * @param description Description of the Activity
   * @param dayOfWeek   Day of Week of the Activity
   * @param category    Category of the Activity
   */
  public Activity(String name, String description, DayOfWeek dayOfWeek, Category category) {
    // Checks if the name and dayOfWeek is provided
    Objects.requireNonNull(name, "name of an Activity must not be null");
    Objects.requireNonNull(dayOfWeek, "dayOfWeek of an Activity must not be null");

    this.name = name;
    this.dayOfWeek = dayOfWeek;
    this.description = description;
    this.category = category;
  }

  /**
   * Gets the name of this Activity
   *
   * @return Name of the Activity
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the description of this Activity
   *
   * @return Description of the Activity
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the day of week of this Activity
   *
   * @return Day of week of the Activity
   */
  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  /**
   * Gets the category of this Activity
   *
   * @return Category of this Activity
   */
  public Category getCategory() {
    return category;
  }

  /**
   * Sets the category of this Activity
   *
   * @param category Category of the Activity
   */
  public void setCategory(Category category) {
    Objects.requireNonNull(name, "category of Activity cannot be null!");
    this.category = category;
  }
}
