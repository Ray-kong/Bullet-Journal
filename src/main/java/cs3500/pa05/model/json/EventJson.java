package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.data.DayOfWeek;

/**
 * Holds an Event of the Journal
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "name" : "Event Name",
 *   "description" : "This is an Event.",
 *   "day-of-week" : "FRI",
 *   "category" : {
 *      "name": "category",
 *      "color": [ ]
 *   },
 *   "start-time" : "12:30",
 *   "duration" : "03:10"
 * }
 * </code>
 * </p>
 *
 * @param name        Name of the Event
 * @param description Description of the Event
 * @param dayOfWeek   Day of week of the Event
 * @param category    Category of the Event
 * @param startTime   Start time of the Event
 * @param duration    Duration of the Event
 */
public record EventJson(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("day-of-week") DayOfWeek dayOfWeek,
    @JsonProperty("category") CategoryJson category,
    @JsonProperty("start-time") String startTime,
    @JsonProperty("duration") String duration) {
}
