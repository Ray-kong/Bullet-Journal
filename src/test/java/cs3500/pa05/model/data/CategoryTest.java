package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Category class.
 */
public class CategoryTest {
  Category category;
  String categoryName;
  Color categoryColor;

  /**
   * Sets up the testing environment before each test case.
   */
  @BeforeEach
  public void setup() {
    categoryName = "Category";
    categoryColor = Color.RED;
    category = new Category(categoryName, categoryColor);
  }

  /**
   *  Test the getName method of the Category class.
   */
  @Test
  public void testGetName() {
    assertEquals("Category", category.getName());
  }

  /**
   *  Test the getColor method of the Category class.
   */
  @Test
  public void testGetColor() {
    assertEquals(Color.RED, category.getColor());
  }

  /**
   *  Test the equals method of the Category class.
   */
  @Test
  public void testEquals() {
    Category category1 = new Category(categoryName, categoryColor);

    // Test equal objects
    assertEquals(category, category);
    assertEquals(category, category1);

    // Test unequal objects
    assertNotEquals(category, null);
    assertNotEquals(category, new JournalWeek());

    Category category2 = new Category("Category2", categoryColor);
    assertNotEquals(category1, category2);
  }

  /**
   *  Test the hashCode method of the Category class.
   */
  @Test
  public void testHashCode() {
    Category category1 = new Category(categoryName, categoryColor);

    // Test equal objects
    assertEquals(category.hashCode(), category1.hashCode());
  }
}
