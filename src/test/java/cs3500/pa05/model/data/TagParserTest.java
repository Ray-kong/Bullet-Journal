package cs3500.pa05.model.data;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the TagParser class.
 */
public class TagParserTest {
  private String sampleText;
  private List<String> expectedTags;
  private String expectedModifiedText;

  /**
   * This method initializes the objects required for each test.
   */
  @BeforeEach
  public void setup() {
    sampleText = "Check out this #awesome #article about #programming!";
    expectedTags = List.of("awesome", "article", "programming");
    expectedModifiedText = "Check out this   about !";
  }

  /**
   * Test the parseTags method of the TagParser class.
   */
  @Test
  public void testParseTags() {
    List<String> tags = TagParser.parseTags(sampleText);

    Assertions.assertNotNull(tags);
    Assertions.assertEquals(expectedTags.size(), tags.size());
    Assertions.assertIterableEquals(expectedTags, tags);
  }

  /**
   * Test the removeTags method of the TagParser class.
   */
  @Test
  public void testRemoveTags() {
    String modifiedText = TagParser.removeTags(sampleText);

    Assertions.assertNotNull(modifiedText);
    Assertions.assertEquals(expectedModifiedText, modifiedText);
  }
}
