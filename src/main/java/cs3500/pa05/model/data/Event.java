package cs3500.pa05.model.data;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Class that represents an Event.
 */
public class Event extends Activity {
  private final LocalTime startTime;
  private final LocalTime duration;

  /**
   * Default Constructor
   *
   * @param name        Name of the Event
   * @param description Description of the Event
   * @param dayOfWeek   Day of Week of the Event
   * @param category    Category of the Event
   * @param startTime   Start time of the Event
   * @param duration    Duration of the Event
   */
  public Event(String name, String description, DayOfWeek dayOfWeek, Category category,
               LocalTime startTime, LocalTime duration) {
    super(name, description, dayOfWeek, category);

    // Checks if the start time and duration is provided
    Objects.requireNonNull(startTime, "startTime of an Event must not be null");
    Objects.requireNonNull(duration, "duration of an Event must not be null");

    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * Gets the start time of this Event
   *
   * @return Start time of the Event
   */
  public LocalTime getStartTime() {
    return startTime;
  }

  /**
   * Gets the duration of this Event
   *
   * @return Duration of the Event
   */
  public LocalTime getDuration() {
    return duration;
  }
}
