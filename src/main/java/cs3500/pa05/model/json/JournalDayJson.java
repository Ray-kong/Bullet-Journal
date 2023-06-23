package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.data.DayOfWeek;
import java.util.List;

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
 *      "color" : {
 *          "red" : 0.9411764740943909,
 *          "green" : 0.9725490212440491,
 *          "blue" : 1.0,
 *          "opacity" : 1.0
 *      }
 *   },
 *   "start-time" : "12:30",
 *   "duration" : "03:10"
 * }
 * </code>
 * </p>
 *
 * @param dayOfWeek  Day of week of the Journal
 * @param eventJsons List of Events on that Day
 * @param taskJsons  List of Tasks on that Day
 */
public record JournalDayJson(
    @JsonProperty("day-of-week") DayOfWeek dayOfWeek,
    @JsonProperty("events") List<EventJson> eventJsons,
    @JsonProperty("tasks") List<TaskJson> taskJsons) {
}
