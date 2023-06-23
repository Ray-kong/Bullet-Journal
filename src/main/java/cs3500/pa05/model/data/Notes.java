package cs3500.pa05.model.data;

/**
 * Class that represent the notes of a Journal.
 */
public class Notes {
  String note;

  /**
   * Default Constructor
   */
  public Notes() {
    this.note = "";
  }

  /**
   * Convenient Constructor
   * For setting the Journal with `load` functions
   *
   * @param note Notes of the Journal
   */
  public Notes(String note) {
    this.note = note == null ? "" : note;
  }

  /**
   * Gets the Notes of the Journal
   *
   * @return Notes of the Journal
   */
  public String getNote() {
    return note;
  }

  /**
   * Sets the Notes of the Journal
   *
   * @param note Notes of the Journal
   */
  public void setNote(String note) {
    this.note = note;
  }
}
