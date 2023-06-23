package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds a Category of the Journal
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "name" : "category",
 * "color" : {
 * "red" : 0.9411764740943909,
 * "green" : 0.9725490212440491,
 * "blue" : 1.0,
 * "opacity" : 1.0
 * }
 * }
 * </code>
 * </p>
 *
 * @param name  Name of the Category
 * @param color Color of the Category
 */
public record CategoryJson(
    @JsonProperty("name") String name,
    @JsonProperty("color") ColorJson color) {
}
