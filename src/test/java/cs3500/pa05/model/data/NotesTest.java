package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tester class for Notes.
 */
public class NotesTest {
  private String note;
  private Notes notes;

  /**
   * Sets up common objects used in all test methods.
   */
  @BeforeEach
  public void setup() {
    note = "This is a note.";
    notes = new Notes(note);
  }

  /**
   * Test the default constructor of the Notes class.
   */
  @Test
  public void testDefaultConstructor() {
    Notes notes = new Notes();
    assertEquals("", notes.getNote());
  }

  /**
   * Test the convenient constructor of the Activity class.
   */
  @Test
  public void testConstructorWithNullNote() {
    Notes notes1 = new Notes(null);
    assertEquals("", notes1.getNote());
  }

  /**
   * Test the setNote method of the Activity class.
   */
  @Test
  public void testSetNote() {
    notes.setNote(note);
    assertEquals("This is a note.", notes.getNote());
  }
}
