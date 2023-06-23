package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for the HashPassword class.
 */
public class HashPasswordTest {

  /**
   * Test the hash method of the JournalWeek class.
   */
  @Test
  public void testHash() {
    assertEquals(
        "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8",
        HashPassword.hash("password"));

    assertEquals(
        "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
        HashPassword.hash("hello"));

    assertEquals(
        "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f",
        HashPassword.hash("12345678"));
  }

  /**
   * Test the hash method by hashing an empty String of the JournalWeek class.
   */
  @Test
  public void testHashEmpty() {
    assertEquals(
        "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
        HashPassword.hash(""));
  }
}
