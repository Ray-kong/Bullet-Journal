package cs3500.pa05.controller;

import cs3500.pa05.model.data.DayOfWeek;
import cs3500.pa05.model.data.Event;
import cs3500.pa05.model.data.JournalDay;
import cs3500.pa05.model.data.JournalWeek;
import cs3500.pa05.model.data.Task;
import cs3500.pa05.model.json.BujoUtils;
import cs3500.pa05.view.ApplicationView;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Represents the controller for a Java Journal.
 */
public class ApplicationController implements JavaJournalController {
  private JournalWeek journalWeek;
  private String filePath;
  private final Stage stage;

  @FXML
  private Label journalName;
  @FXML
  private VBox sunBox;
  @FXML
  private VBox monBox;
  @FXML
  private VBox tueBox;
  @FXML
  private VBox wedBox;
  @FXML
  private VBox thuBox;
  @FXML
  private VBox friBox;
  @FXML
  private VBox satBox;
  @FXML
  private VBox taskQueueBox;
  @FXML
  private VBox weeklyOverviewBox;
  @FXML
  private TextArea notesTextArea;

  @FXML
  private ProgressBar sunProgressBar;
  @FXML
  private ProgressBar monProgressBar;
  @FXML
  private ProgressBar tueProgressBar;
  @FXML
  private ProgressBar wedProgressBar;
  @FXML
  private ProgressBar thuProgressBar;
  @FXML
  private ProgressBar friProgressBar;
  @FXML
  private ProgressBar satProgressBar;
  @FXML
  private Label sunProgressBarLabel;
  @FXML
  private Label monProgressBarLabel;
  @FXML
  private Label tueProgressBarLabel;
  @FXML
  private Label wedProgressBarLabel;
  @FXML
  private Label thuProgressBarLabel;
  @FXML
  private Label friProgressBarLabel;
  @FXML
  private Label satProgressBarLabel;

  @FXML
  private MenuBar menuBar;
  @FXML
  private MenuItem menuRename;
  @FXML
  private MenuItem menuSave;
  @FXML
  private MenuItem menuLoad;
  @FXML
  private MenuItem menuAddEvent;
  @FXML
  private MenuItem menuMaxEvent;
  @FXML
  private MenuItem menuAddTask;
  @FXML
  private MenuItem menuMaxTask;
  @FXML
  private MenuItem menuEditNote;
  @FXML
  private MenuItem menuAddCategory;
  @FXML
  private MenuItem menuTemplate;

  /**
   * Default Constructor
   *
   * @param journalWeek The week of the Journal
   * @param stage       Stage of the Journal
   */
  public ApplicationController(JournalWeek journalWeek, Stage stage) {
    this.journalWeek = Objects.requireNonNull(journalWeek);
    this.stage = stage;
  }

  /**
   * Gets the week journal of the application.
   *
   * @return the current week journal
   */
  public JournalWeek getJournalWeek() {
    return this.journalWeek;
  }

  /**
   * Gets the file path of the journal.
   *
   * @return the file path of the journal
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Gets the stage of the application.
   *
   * @return the stage of the application
   */
  public Stage getStage() {
    return stage;
  }

  /**
   * Sets the week journal of the application.
   *
   * @param journalWeek the new week journal to set
   */
  public void setJournalWeek(JournalWeek journalWeek) {
    this.journalWeek = new JournalWeek(journalWeek.getName(), journalWeek.getJournalDays(),
        journalWeek.getMaxEventWarnLimit(), journalWeek.getMaxTaskWarnLimit(),
        journalWeek.getCategories(), journalWeek.getNotes(), journalWeek.getPassword());
  }

  /**
   * Sets the file path of the journal.
   *
   * @param filePath the new file path to set
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Disable the menubar by the given state
   *
   * @param state True or False
   */
  public void disableMenuBar(boolean state) {
    menuBar.setDisable(state);
  }

  /**
   * Initializes a Java Journal
   *
   * @throws IllegalStateException If the journal is not defined
   */
  @Override
  public void run() {
    initJournal();
  }

  /**
   * Initializes the journal for the application.
   */
  private void initJournal() {
    boolean passwordSet = false;
    while (!passwordSet) {
      Map.Entry<Integer, String> runMode = FileSelector.selectFile();
      if (runMode == null) {
        Platform.exit();
        System.exit(0);
      }
      filePath = runMode.getValue();
      if (runMode.getKey() == 0 || runMode.getKey() == 1) {
        String password = null;
        if (runMode.getKey() == 0) { // Load an existing file
          JournalWeek journalWeek = BujoUtils.openFile(filePath);
          boolean passwordCorrect = false;
          while (!passwordCorrect) {
            password = JavaFxBuilder.getPassword(this);
            if (password == null) {
              break;
            }
            if (password.equals(journalWeek.getPassword())) {
              setJournalWeek(journalWeek);
              passwordCorrect = true;
              passwordSet = true;
            } else {
              Alert alert = new Alert(Alert.AlertType.ERROR,
                  "Password incorrect!",
                  ButtonType.OK);
              alert.showAndWait();
            }
          }
        } else { // Create a new file
          journalWeek = new JournalWeek();
          password = JavaFxBuilder.setPassword();
          if (password == null) {
            continue;
          }
          journalWeek.setPassword(password);
          BujoUtils.saveToFile(filePath, journalWeek);
          passwordSet = true;
        }
        if (password == null) {
          continue;
        }
        // Load splash screen
        try {
          ImageView root = FXMLLoader.load(
              Objects.requireNonNull(getClass().getResource("/splash.fxml")));
          Group group = new Group(root);
          Scene scene = new Scene(group);
          stage.setScene(scene);
          stage.show();
        } catch (IOException e) {
          e.printStackTrace();
        }

        PauseTransition delay = new PauseTransition(Duration.seconds(1.8f));
        delay.setOnFinished(event -> {
          // Switch scene to main application scene after splash screen
          ApplicationView view = new ApplicationView(this);
          try {
            Scene mainScene = view.load();
            stage.setScene(mainScene);
          } catch (IllegalStateException e) {
            e.printStackTrace();
          }
          initButtons();
          renderWeek();
        });
        delay.play();
      } else { // Create with a template
        LoadTemplateHandler loadTemplateHandler = new LoadTemplateHandler(this);
        ActionEvent event = new ActionEvent();
        loadTemplateHandler.handle(event);
        if (!loadTemplateHandler.getFlag()) {
          continue;
        }
        initButtons();
        renderWeek();
        passwordSet = true;
      }
    }
  }

  /**
   * Initializes the functionality of the menu buttons in the application.
   */
  private void initButtons() {
    menuRename.setOnAction(
        e -> new RenameJournalHandler(this).handle(e));
    menuAddEvent.setOnAction(
        e -> new AddEventHandler(this).handle(e));
    menuAddTask.setOnAction(
        e -> new AddTaskHandler(this).handle(e));
    menuMaxEvent.setOnAction(
        e -> new MaxActivityLimitHandler(this, 0).handle(e));
    menuMaxTask.setOnAction(
        e -> new MaxActivityLimitHandler(this, 1).handle(e));
    menuEditNote.setOnAction(
        e -> new EditNotesHandler(this).handle(e));
    menuAddCategory.setOnAction(
        e -> new AddCategoryHandler(this).handle(e));
    menuSave.setOnAction(
        e -> new SaveHandler(this).handle(e));
    menuLoad.setOnAction(
        e -> new LoadHandler(this).handle(e));
    menuTemplate.setOnAction(
        e -> new LoadTemplateHandler(this).handle(e));
  }

  /**
   * Renders the week journal on the view of the application.
   */
  public void renderWeek() {
    journalName.setText(journalWeek.getName());
    Map<DayOfWeek, VBox> dayToBox = getDayToBox();

    for (Map.Entry<DayOfWeek, VBox> entry : dayToBox.entrySet()) {
      renderDay(entry);
    }

    renderTaskQueue();
    renderWeeklyOverview();
    notesTextArea.setText(journalWeek.getNotes().getNote());
    notesTextArea.setStyle("-fx-control-inner-background: #B1AFFF; -fx-border-color: #2B2730;"
        + "-fx-border-width: 2px; -fx-border-radius: 5px");
  }

  /**
   * Renders the day of the week journal in the specified VBox.
   *
   * @param entry the day of the week and its VBox
   */
  private void renderDay(Map.Entry<DayOfWeek, VBox> entry) {
    JournalDay day = journalWeek.getJournalDays().get(entry.getKey());
    VBox vbox = entry.getValue();
    vbox.getChildren().clear();
    renderTasks(day, vbox);
    renderEvents(day, vbox);
    updateProgressBar(day, entry.getKey());
  }

  /**
   * Renders tasks of the day in the specified VBox.
   *
   * @param day  the day of the journal
   * @param vbox the VBox to render tasks in
   */
  private void renderTasks(JournalDay day, VBox vbox) {
    for (Task task : day.getTasks()) {
      vbox.getChildren().add(JavaFxBuilder.buildTask(task, this));
    }
  }

  /**
   * Renders events of the day in the specified VBox.
   *
   * @param day  the day of the journal
   * @param vbox the VBox to render events in
   */
  private void renderEvents(JournalDay day, VBox vbox) {
    for (Event event : day.getEvents()) {
      vbox.getChildren().add(JavaFxBuilder.buildEvent(event, this));
    }
  }

  /**
   * Updates the progress bar for the day of the journal based on the completed tasks.
   *
   * @param day      the day of the journal
   * @param dayOfWeek the day of the week
   */
  private void updateProgressBar(JournalDay day, DayOfWeek dayOfWeek) {
    if (day.getTasks().size() > 0) {
      day.updateCompletedTasks();
      double completedPercent = (double) day.getCompletedTasks() / day.getTasks().size();
      Map.Entry<ProgressBar, Label> progressBarLabelEntry = getProgressBarByDay(dayOfWeek);
      ProgressBar progressBar = progressBarLabelEntry.getKey();
      Label label = progressBarLabelEntry.getValue();
      progressBar.setProgress(completedPercent);
      label.setText("Remaining Task: " + (day.getTasks().size() - day.getCompletedTasks()));
    } else {
      Map.Entry<ProgressBar, Label> progressBarLabelEntry = getProgressBarByDay(dayOfWeek);
      ProgressBar progressBar = progressBarLabelEntry.getKey();
      Label label = progressBarLabelEntry.getValue();
      progressBar.setProgress(0);
      label.setText("Remaining Task: 0");
    }
  }

  /**
   * Returns the progress bar and the label for the specified day of the week.
   *
   * @param dayOfWeek the day of the week
   * @return the progress bar and the label for the specified day of the week
   */
  private Map.Entry<ProgressBar, Label> getProgressBarByDay(DayOfWeek dayOfWeek) {
    Map<ProgressBar, Label> progressBarLabelMap = new LinkedHashMap<>();

    switch (dayOfWeek) {
      case SUN -> {
        progressBarLabelMap.put(sunProgressBar, sunProgressBarLabel);
        return progressBarLabelMap.entrySet().iterator().next();
      }
      case MON -> {
        progressBarLabelMap.put(monProgressBar, monProgressBarLabel);
        return progressBarLabelMap.entrySet().iterator().next();
      }
      case TUE -> {
        progressBarLabelMap.put(tueProgressBar, tueProgressBarLabel);
        return progressBarLabelMap.entrySet().iterator().next();
      }
      case WED -> {
        progressBarLabelMap.put(wedProgressBar, wedProgressBarLabel);
        return progressBarLabelMap.entrySet().iterator().next();
      }
      case THU -> {
        progressBarLabelMap.put(thuProgressBar, thuProgressBarLabel);
        return progressBarLabelMap.entrySet().iterator().next();
      }
      case FRI -> {
        progressBarLabelMap.put(friProgressBar, friProgressBarLabel);
        return progressBarLabelMap.entrySet().iterator().next();
      }
      case SAT -> {
        progressBarLabelMap.put(satProgressBar, satProgressBarLabel);
        return progressBarLabelMap.entrySet().iterator().next();
      }
      default -> throw new IllegalArgumentException("Invalid DayOfWeek");
    }
  }

  /**
   * Returns a map with days of the week as keys and corresponding VBoxes as values.
   *
   * @return a map from days of the week to VBoxes
   */
  private Map<DayOfWeek, VBox> getDayToBox() {
    Map<DayOfWeek, VBox> dayToBox = new LinkedHashMap<>();
    dayToBox.put(DayOfWeek.SUN, sunBox);
    dayToBox.put(DayOfWeek.MON, monBox);
    dayToBox.put(DayOfWeek.TUE, tueBox);
    dayToBox.put(DayOfWeek.WED, wedBox);
    dayToBox.put(DayOfWeek.THU, thuBox);
    dayToBox.put(DayOfWeek.FRI, friBox);
    dayToBox.put(DayOfWeek.SAT, satBox);
    return dayToBox;
  }

  /**
   * Renders the task queue on the view of the application.
   */
  private void renderTaskQueue() {
    journalWeek.updateTaskQueue();
    renderTasksInBox(journalWeek.getTaskQueue().getTasks(), taskQueueBox);
  }

  /**
   * Renders tasks in the specified VBox.
   *
   * @param tasks the list of tasks
   * @param box   the VBox to render tasks in
   */
  private void renderTasksInBox(List<Task> tasks, VBox box) {
    box.getChildren().clear();
    for (Task task : tasks) {
      box.getChildren().add(JavaFxBuilder.buildTask(task, this));
    }
  }

  /**
   * Renders the weekly overview on the view of the application.
   */
  private void renderWeeklyOverview() {
    journalWeek.updateWeeklyOverview();
    weeklyOverviewBox.getChildren().clear();
    weeklyOverviewBox.getChildren().add(JavaFxBuilder.buildWeeklyOverview(journalWeek));
  }
}
