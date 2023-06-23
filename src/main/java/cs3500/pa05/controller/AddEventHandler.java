package cs3500.pa05.controller;

import cs3500.pa05.model.data.Category;
import cs3500.pa05.model.data.DayOfWeek;
import cs3500.pa05.model.data.Event;
import cs3500.pa05.model.data.JournalDay;
import cs3500.pa05.model.json.BujoUtils;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * Class to handle Adding event function.
 */
public class AddEventHandler extends AddActivityHandler
    implements EventHandler<ActionEvent>, Initializable {
  @FXML
  private ComboBox<String> eventDayComboBox;
  @FXML
  private ComboBox<String> eventCategoryComboBox;
  @FXML
  private TextField eventNameTextField;
  @FXML
  private TextArea eventDescriptionTextArea;
  @FXML
  private ComboBox<Integer> eventStartHour;
  @FXML
  private ComboBox<Integer> eventStartMinute;
  @FXML
  private ComboBox<Integer> eventDurationHour;
  @FXML
  private ComboBox<Integer> eventDurationMinute;
  @FXML
  private Button saveEvent;
  @FXML
  private Button cancelEvent;

  /**
   * Default Constructor
   *
   * @param controller Controller of the Journal
   */
  public AddEventHandler(ApplicationController controller) {
    super(controller);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    controller.disableMenuBar(true);

    final DayOfWeek[] selectedDay = new DayOfWeek[1];
    final Category[] selectedCategory = new Category[1];
    super.initComboBox(eventDayComboBox, eventCategoryComboBox, selectedDay, selectedCategory);

    final int[] hourChoices = {-1, -1};
    final int[] minuteChoices = {-1, -1};
    populateTimeChoices(eventStartHour, eventDurationHour, hourChoices, 24);
    populateTimeChoices(eventStartMinute, eventDurationMinute, minuteChoices, 60);

    initSave(hourChoices, minuteChoices, selectedDay, selectedCategory);
    cancelEvent.setOnAction(e -> {
      controller.disableMenuBar(false);
      popup.hide();
    });
  }

  /**
   * Initialize the save Button
   *
   * @param hourChoices      Selected hours
   * @param minuteChoices    Selected minutes
   * @param selectedDay      Selected Day of Week
   * @param selectedCategory Selected Category
   */
  private void initSave(int[] hourChoices, int[] minuteChoices,
                        DayOfWeek[] selectedDay, Category[] selectedCategory) {
    saveEvent.setOnAction(e -> {
      String name = eventNameTextField.getText().trim();
      String description = eventDescriptionTextArea.getText().isBlank() ? null
          : eventDescriptionTextArea.getText();
      if (name.isEmpty() || hourChoices[0] < 0 || minuteChoices[0] < 0
          || hourChoices[1] < 0 || minuteChoices[1] < 0 || selectedDay[0] == null) {
        //NOTE: Do not optimize this method body. Highly likely to cause bug.
        controller.getStage().setAlwaysOnTop(true);
        Window popupWindow = popup.getOwnerWindow();
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "Please provide NAME, DAY OF WEEK, START TIME and DURATION for the event!",
            ButtonType.OK);
        alert.initOwner(popupWindow);
        alert.showAndWait();
        return;
      }
      Event eve = new Event(name, description, selectedDay[0], selectedCategory[0],
          LocalTime.of(hourChoices[0], minuteChoices[0]),
          LocalTime.of(hourChoices[1], minuteChoices[1]));
      controller.getJournalWeek().addEvent(eve, selectedDay[0]);
      JournalDay journalDay = controller.getJournalWeek().getJournalDays().get(selectedDay[0]);
      if (journalDay.overCommitted(
          journalDay.getEvents(), controller.getJournalWeek().getMaxEventWarnLimit())) {
        controller.getStage().setAlwaysOnTop(true);
        Window popupWindow = popup.getOwnerWindow();
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "Warning: You are adding more events than your set limit for each day!"
                + " Please be aware that an overloaded schedule might affect your productivity "
                + "and stress levels. Consider adjusting your events or increasing your daily"
                + " event limit to maintain a balanced schedule.", ButtonType.OK);
        alert.initOwner(popupWindow);
        alert.showAndWait();
      }
      BujoUtils.saveToFile(controller.getFilePath(), controller.getJournalWeek());
      controller.renderWeek();
      controller.disableMenuBar(false);
      popup.hide();
    });
  }

  /**
   * Initialize the combo boxes of time
   *
   * @param start    Start Combo Box
   * @param duration Duration Combo Box
   * @param choices  Selected choices
   * @param limit    Time limits
   */
  private void populateTimeChoices(ComboBox<Integer> start, ComboBox<Integer> duration,
                                   int[] choices, int limit) {
    for (int i = 0; i < limit; i++) {
      start.getItems().add(i);
      duration.getItems().add(i);
    }
    start.setOnAction(event -> choices[0] = start.getValue());
    duration.setOnAction(event -> choices[1] = duration.getValue());
  }

  @Override
  public void handle(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
          .getResource("addEvent.fxml"));
      loader.setController(this);
      Scene scene = loader.load();
      popup = new Popup();
      popup.getContent().add(scene.getRoot());
      popup.show(controller.getStage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
