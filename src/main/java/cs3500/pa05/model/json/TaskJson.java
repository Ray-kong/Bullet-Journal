package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.data.DayOfWeek;

/**
 * Holds a Task of the Journal
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "name" : "Task",
 *   "description" : "This is a Task.",
 *   "dayOfWeek" : "MON",
 *   "category" : [ ],
 *   "completed" : false
 * }
 * </code>
 * </p>
 *
 * @param name        Name of the Task
 * @param description Description of the Task
 * @param dayOfWeek   Day of week of the Event
 * @param category    Category of the Event
 * @param isComplete  Status of the Task
 */
public record TaskJson(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("dayOfWeek") DayOfWeek dayOfWeek,
    @JsonProperty("category") CategoryJson category,
    @JsonProperty("completed") boolean isComplete) {
}
