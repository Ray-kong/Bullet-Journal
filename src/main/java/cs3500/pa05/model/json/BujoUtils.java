package cs3500.pa05.model.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.data.Category;
import cs3500.pa05.model.data.DayOfWeek;
import cs3500.pa05.model.data.Event;
import cs3500.pa05.model.data.JournalDay;
import cs3500.pa05.model.data.JournalWeek;
import cs3500.pa05.model.data.Notes;
import cs3500.pa05.model.data.Task;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;

/**
 * Utility Class that used to save and load a Journal.
 */
public class BujoUtils {

  /**
   * Saves the journal to the given path
   *
   * @param filePath Path of the file
   * @param journal  Journal of the week
   */
  public static void saveToFile(String filePath, JournalWeek journal) {
    JournalWeekJson journalWeekJson = convertToJournalWeekJson(journal);

    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), journalWeekJson);
    } catch (IOException e) {
      throw new IllegalStateException("fail to save the journal");
    }
  }

  /**
   * Loads the file to the given path and decodes to a JournalWeek
   *
   * @param filePath Path of the file
   * @return Journal of the week
   */
  public static JournalWeek openFile(String filePath) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      JournalWeekJson journalWeekJson = mapper.readValue(new File(filePath), JournalWeekJson.class);
      return convertJsonToJournalWeek(journalWeekJson);
    } catch (IOException e) {
      throw new IllegalStateException("fail to load the journal");
    }
  }

  /**
   * Converts the data of Journal to JSON format
   *
   * @param journal Journal of the week
   * @return Journal JSON
   */
  private static JournalWeekJson convertToJournalWeekJson(JournalWeek journal) {
    String journalName = journal.getName();
    List<CategoryJson> categoryJsonList = convertCategoriesToJson(journal.getCategories());
    List<JournalDayJson> journalDayJsonList = convertJournalDaysToJson(journal.getJournalDays());
    NotesJson notesJson = new NotesJson(journal.getNotes().getNote());
    String password = journal.getPassword();

    return new JournalWeekJson(journalName, journalDayJsonList, journal.getMaxEventWarnLimit(),
        journal.getMaxTaskWarnLimit(), categoryJsonList, notesJson, password);
  }

  /**
   * Converts the data of list of Categories to JSON format
   *
   * @param categories List of Categories
   * @return Categories JSON
   */
  private static List<CategoryJson> convertCategoriesToJson(List<Category> categories) {
    return categories.stream()
        .map(category -> {
          ColorJson colorJson = new ColorJson(category.getColor().getRed(),
              category.getColor().getGreen(), category.getColor().getBlue(),
              category.getColor().getOpacity());
          return new CategoryJson(category.getName(), colorJson);
        })
        .collect(Collectors.toList());
  }

  /**
   * Converts the data of list of Days to JSON format
   *
   * @param journalDays List of Days to the Journal
   * @return List of JSON Days in to the Journal
   */
  private static List<JournalDayJson> convertJournalDaysToJson(
      Map<DayOfWeek, JournalDay> journalDays) {
    return journalDays.entrySet().stream()
        .map(entry -> convertJournalDayToJson(entry.getKey(), entry.getValue()))
        .collect(Collectors.toList());
  }

  /**
   * Converts the Day of a Journal to JSON format
   *
   * @param dayOfWeek  Day of week of the Journal
   * @param journalDay Day of Journal
   * @return A JSON Day to the Journal
   */
  private static JournalDayJson convertJournalDayToJson(
      DayOfWeek dayOfWeek, JournalDay journalDay) {
    List<EventJson> eventJsons = convertEventsToJson(journalDay.getEvents());
    List<TaskJson> taskJsons = convertTasksToJson(journalDay.getTasks());

    return new JournalDayJson(dayOfWeek, eventJsons, taskJsons);
  }

  /**
   * Converts a List of Events to JSON format
   *
   * @param events List of Events
   * @return List of JSON Events
   */
  private static List<EventJson> convertEventsToJson(List<Event> events) {
    return events.stream()
        .map(event -> new EventJson(event.getName(), event.getDescription(), event.getDayOfWeek(),
            createCategoryJson(event.getCategory()), event.getStartTime().toString(),
            event.getDuration().toString()))
        .collect(Collectors.toList());
  }

  /**
   * Converts a List of Tasks to JSON format
   *
   * @param tasks List of Tasks
   * @return List of JSON Tasks
   */
  private static List<TaskJson> convertTasksToJson(List<Task> tasks) {
    return tasks.stream()
        .map(task -> new TaskJson(task.getName(), task.getDescription(), task.getDayOfWeek(),
            createCategoryJson(task.getCategory()), task.getIsComplete()))
        .collect(Collectors.toList());
  }

  /**
   * Converts a Category to JSON format
   *
   * @param category Category of an Activity
   * @return JSON Category of an Activity
   */
  private static CategoryJson createCategoryJson(Category category) {
    if (category == null) {
      return null;
    } else {
      ColorJson colorJson = new ColorJson(category.getColor().getRed(),
          category.getColor().getGreen(), category.getColor().getBlue(),
          category.getColor().getOpacity());
      return new CategoryJson(category.getName(), colorJson);
    }
  }

  /**
   * Converts a JSON representation of a journal week to a JournalWeek object
   *
   * @param journalWeekJson The JSON representation of a journal week
   * @return A JournalWeek object
   */
  private static JournalWeek convertJsonToJournalWeek(JournalWeekJson journalWeekJson) {
    String name = journalWeekJson.name();

    Map<DayOfWeek, JournalDay> journalDays = journalWeekJson.journalDayJsons().stream()
        .collect(Collectors.toMap(JournalDayJson::dayOfWeek, BujoUtils::convertJsonToJournalDay));

    int maxEventWarnLimit = journalWeekJson.maxEventWarnLimit();
    int maxTaskWarnLimit = journalWeekJson.maxTaskWarnLimit();

    List<Category> categories;
    if (journalWeekJson.categoryJsons().isEmpty()) {
      categories = null;
    } else {
      categories = journalWeekJson.categoryJsons().stream()
          .map(categoryJson -> {
            Color color = Color.color(
                categoryJson.color().red(),
                categoryJson.color().green(),
                categoryJson.color().blue(),
                categoryJson.color().opacity()
            );
            return new Category(categoryJson.name(), color);
          })
          .collect(Collectors.toList());
    }

    Notes notes = new Notes(journalWeekJson.notesJson().note());
    String password = journalWeekJson.password();

    return new JournalWeek(name, journalDays, maxEventWarnLimit, maxTaskWarnLimit,
        categories, notes, password);
  }

  /**
   * Converts a JSON Journal Day to a normal Journal Day
   *
   * @param journalDayJson A JSON Day to the Journal
   * @return A Day to the Journal
   */
  private static JournalDay convertJsonToJournalDay(JournalDayJson journalDayJson) {
    List<Event> events;
    if (!journalDayJson.eventJsons().isEmpty()) {
      events = journalDayJson.eventJsons().stream()
          .map(eventJson -> {
            String description = eventJson.description() == null ? null : eventJson.description();
            Category category = eventJson.category()
                == null ? null : convertJsonToCategory(eventJson.category());

            return new Event(eventJson.name(), description, eventJson.dayOfWeek(), category,
                LocalTime.parse(eventJson.startTime()), LocalTime.parse(eventJson.duration()));
          })
          .collect(Collectors.toList());
    } else {
      events = null;
    }

    List<Task> tasks;
    if (!journalDayJson.taskJsons().isEmpty()) {
      tasks = journalDayJson.taskJsons().stream()
          .map(taskJson -> {
            String description = taskJson.description() == null ? null : taskJson.description();
            Category category = taskJson.category()
                == null ? null : convertJsonToCategory(taskJson.category());

            return new Task(taskJson.name(), description, taskJson.dayOfWeek(), category,
                taskJson.isComplete());
          })
          .collect(Collectors.toList());
    } else {
      tasks = null;
    }

    return new JournalDay(events, tasks);
  }

  /**
   * Converts a JSON Category to a normal Category
   *
   * @param categoryJson A JSON Category to the Journal
   * @return A Category to the Journal
   */
  private static Category convertJsonToCategory(CategoryJson categoryJson) {
    Color color = Color.color(
        categoryJson.color().red(),
        categoryJson.color().green(),
        categoryJson.color().blue(),
        categoryJson.color().opacity()
    );
    return new Category(categoryJson.name(), color);
  }
}
