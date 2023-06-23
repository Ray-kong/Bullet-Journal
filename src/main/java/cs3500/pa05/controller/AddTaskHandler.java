package cs3500.pa05.controller;

import cs3500.pa05.model.data.Category;
import cs3500.pa05.model.data.DayOfWeek;
import cs3500.pa05.model.data.JournalDay;
import cs3500.pa05.model.data.Task;
import cs3500.pa05.model.json.BujoUtils;
import java.io.IOException;
import java.net.URL;
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
 * JavaFX event handler that is responsible for managing the addition of tasks.
 */
public class AddTaskHandler extends AddActivityHandler
    implements EventHandler<ActionEvent>, Initializable {
  @FXML
  private ComboBox<String> taskDayComboBox;
  @FXML
  private ComboBox<String> taskCategoryComboBox;
  @FXML
  private TextField taskNameTextField;
  @FXML
  private TextArea taskDescriptionTextArea;
  @FXML
  private Button saveTask;
  @FXML
  private Button cancelTask;

  /**
   * Default Constructor
   *
   * @param controller Controller of the Journal
   */
  public AddTaskHandler(ApplicationController controller) {
    super(controller);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    controller.disableMenuBar(true);

    final DayOfWeek[] selectedDay = new DayOfWeek[1];
    final Category[] selectedCategory = new Category[1];
    super.initComboBox(taskDayComboBox, taskCategoryComboBox, selectedDay, selectedCategory);

    initSave(selectedDay, selectedCategory);
    cancelTask.setOnAction(e -> {
      controller.disableMenuBar(false);
      popup.hide();
    });
  }

  /**
   * Initialize the save Button
   *
   * @param selectedDay      Selected Day of Week
   * @param selectedCategory Selected Category
   */
  private void initSave(DayOfWeek[] selectedDay, Category[] selectedCategory) {
    saveTask.setOnAction(e -> {
      String name = taskNameTextField.getText().trim();
      String description = taskDescriptionTextArea.getText().isBlank() ? null
          : taskDescriptionTextArea.getText();

      if (name.isEmpty() || selectedDay[0] == null) {
        controller.getStage().setAlwaysOnTop(true);
        Window popupWindow = popup.getOwnerWindow();
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "Please provide NAME, DAY OF WEEK for the task!",
            ButtonType.OK);
        alert.initOwner(popupWindow);
        alert.showAndWait();
        return;
      }

      Task task = new Task(name, description, selectedDay[0], selectedCategory[0]);
      controller.getJournalWeek().addTask(task, selectedDay[0]);

      JournalDay journalDay = controller.getJournalWeek().getJournalDays().get(selectedDay[0]);
      if (journalDay.overCommitted(
          journalDay.getTasks(), controller.getJournalWeek().getMaxTaskWarnLimit())) {
        controller.getStage().setAlwaysOnTop(true);
        Window popupWindow = popup.getOwnerWindow();
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "Warning: You are adding more tasks than your set limit for each day!"
                + " Please be aware that an overloaded schedule might affect your productivity "
                + "and stress levels. Consider adjusting your events or increasing your daily "
                + "event limit to maintain a balanced schedule.", ButtonType.OK);
        alert.initOwner(popupWindow);
        alert.showAndWait();
      }
      BujoUtils.saveToFile(controller.getFilePath(), controller.getJournalWeek());
      controller.renderWeek();
      controller.disableMenuBar(false);
      popup.hide();
    });
  }

  @Override
  public void handle(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
          .getResource("addTask.fxml"));
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
