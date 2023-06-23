package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Holds the entire Journal
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "name" : "Journal",
 *   "journal-day-jsons" : [ {
 *     "day-of-week" : "SUN",
 *     "events" : [ ],
 *     "tasks" : [ ]
 *   }, {
 *     "day-of-week" : "MON",
 *     "events" : [ ],
 *     "tasks" : [ ]
 *   }, {
 *     "day-of-week" : "TUE",
 *     "events" : [ ],
 *     "tasks" : [ ]
 *   }, {
 *     "day-of-week" : "WED",
 *     "events" : [ ],
 *     "tasks" : [ ]
 *   }, {
 *     "day-of-week" : "THU",
 *     "events" : [ ],
 *     "tasks" : [ ]
 *   }, {
 *     "day-of-week" : "FRI",
 *     "events" : [ ],
 *     "tasks" : [ ]
 *   }, {
 *     "day-of-week" : "SAT",
 *     "events" : [ ],
 *     "tasks" : [ ]
 *   } ],
 *   "categories" : [ ],
 *   "max-event-warn-limit" : 8,
 *   "max-task-warn-limit" : 8,
 *   "notes" : {
 *     "note" : "note"
 *   },
 *   "password" : c04bc270f965db4d7ab365b3b865b743e35e13e295c7b15fb795e2a01d24a639
 * }
 * </code>
 * </p>
 *
 * @param name              Name of the Journal
 * @param journalDayJsons   List of Days to the Journal
 * @param maxEventWarnLimit Max number of event warnings
 * @param maxTaskWarnLimit  Max number of task warnings
 * @param categoryJsons     List of Categories to the Journal
 * @param notesJson         Notes of the Journal
 * @param password          Password of the Journal
 */
public record JournalWeekJson(
    @JsonProperty("name") String name,
    @JsonProperty("journal-day-jsons") List<JournalDayJson> journalDayJsons,
    @JsonProperty("max-event-warn-limit") int maxEventWarnLimit,
    @JsonProperty("max-task-warn-limit") int maxTaskWarnLimit,
    @JsonProperty("categories") List<CategoryJson> categoryJsons,
    @JsonProperty("notes") NotesJson notesJson,
    @JsonProperty("password") String password) {
}
