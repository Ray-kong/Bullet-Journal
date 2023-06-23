package cs3500.pa05.model.data;

import java.util.Objects;
import javafx.scene.paint.Color;

/**
 * Class that represents a category.
 */
public class Category {
  private final String name;
  private final Color color;

  /**
   * Convenient Constructor
   * Passing `null` for generating the Category for the first time
   * Passing `Color` for setting the Category with `load` functions
   *
   * @param name  Name of the Category
   * @param color Color of the Category
   */
  public Category(String name, Color color) {
    // Checks if the name and is provided
    Objects.requireNonNull(name, "name of a Category must not be null");

    this.name = name;
    this.color = color;
  }

  /**
   * Gets the name of this category
   *
   * @return Name of the category
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the color of this Category
   *
   * @return Color of the Category
   */
  public Color getColor() {
    return color;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Category category = (Category) obj;
    return Objects.equals(name, category.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
