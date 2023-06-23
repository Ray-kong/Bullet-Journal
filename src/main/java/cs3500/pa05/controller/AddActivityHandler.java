package cs3500.pa05.controller;

import cs3500.pa05.model.data.Category;
import cs3500.pa05.model.data.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.scene.control.ComboBox;
import javafx.stage.Popup;

/**
 * Class to handle Adding activity function.
 */
public abstract class AddActivityHandler {
  /**
   * Controller of the Journal
   */
  protected final ApplicationController controller;
  /**
   * Popup of the Journal
   */
  protected Popup popup;

  /**
   * Default Constructor
   *
   * @param controller Controller of the Journal
   */
  public AddActivityHandler(ApplicationController controller) {
    this.controller = controller;
  }

  /**
   * Initialize the given combo boxes
   *
   * @param dayComboBox      Combo Box of Day of Week
   * @param categoryComboBox Combo Box of existed Categories
   * @param selectedDay      Selected Day of Week
   * @param selectedCategory Selected Category
   */
  public void initComboBox(ComboBox<String> dayComboBox, ComboBox<String> categoryComboBox,
                         DayOfWeek[] selectedDay, Category[] selectedCategory) {
    Map<String, DayOfWeek> days = new LinkedHashMap<>();
    days.put("Sunday", DayOfWeek.SUN);
    days.put("Monday", DayOfWeek.MON);
    days.put("Tuesday", DayOfWeek.TUE);
    days.put("Wednesday", DayOfWeek.WED);
    days.put("Thursday", DayOfWeek.THU);
    days.put("Friday", DayOfWeek.FRI);
    days.put("Saturday", DayOfWeek.SAT);
    dayComboBox.getItems().addAll(days.keySet());

    dayComboBox.setOnAction(e -> selectedDay[0] = days.get(dayComboBox.getValue()));

    categoryComboBox.getItems().addAll(
        controller.getJournalWeek().getCategories().stream().map(Category::getName).toList());
    categoryComboBox.getItems().add("N/A");

    categoryComboBox.setOnAction(e ->
        selectedCategory[0] = "N/A".equals(categoryComboBox.getValue()) ? null
            : controller.getJournalWeek().getCategories().stream().filter(c -> c.getName().equals(
            categoryComboBox.getValue())).findFirst().orElse(null));
  }
}
