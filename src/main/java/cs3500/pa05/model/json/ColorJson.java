package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds a Color of the Category
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *      "red" : 0.9411764740943909,
 *      "green" : 0.9725490212440491,
 *      "blue" : 1.0,
 *      "opacity" : 1.0
 * }
 * </code>
 * </p>
 *
 * @param red  Red of the Color
 * @param green Green of the Color
 * @param blue Blue of the Color
 * @param opacity Opacity of the Color
 */
public record ColorJson(
    @JsonProperty("red") double red,
    @JsonProperty("green") double green,
    @JsonProperty("blue") double blue,
    @JsonProperty("opacity") double opacity) {
}
