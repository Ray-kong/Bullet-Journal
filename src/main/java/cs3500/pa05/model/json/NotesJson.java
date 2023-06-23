package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds a Note of the Journal
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *     "note" : "this is a note"
 * }
 * </code>
 * </p>
 *
 * @param note Notes of the Journal
 */
public record NotesJson(
    @JsonProperty("note") String note) {
}
