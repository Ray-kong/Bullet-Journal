package cs3500.pa05.controller;

import cs3500.pa05.model.data.Activity;
import cs3500.pa05.model.data.Category;
import cs3500.pa05.model.data.Event;
import cs3500.pa05.model.data.HashPassword;
import cs3500.pa05.model.data.JournalWeek;
import cs3500.pa05.model.data.TagParser;
import cs3500.pa05.model.data.Task;
import cs3500.pa05.model.data.WeeklyOverview;
import cs3500.pa05.view.Fonts;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
 * This class provides methods to create JavaFX user interface components.
 */
public class JavaFxBuilder {

  /**
   * Builds a JavaFX VBox component representing an Event.
   *
   * @param event      Event to be represented in the VBox.
   * @param controller Controller of the Application
   * @return a VBox component representing the Event.
   */
  public static VBox buildEvent(Event event, ApplicationController controller) {
    VBox verticalBox = initVbox(event);
    setName(controller, event, verticalBox);
    setDescription(event, verticalBox);
    setCategory(event, verticalBox);

    LocalTime startTime = event.getStartTime();
    LocalTime duration = event.getDuration();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    Label startTimeLabel = new Label("Start Time: " + startTime.format(formatter));
    startTimeLabel.setFont(Fonts.customFont);
    Label durationLabel = new Label("Duration: " + duration.format(formatter));
    durationLabel.setFont(Fonts.customFont);
    verticalBox.getChildren().add(startTimeLabel);
    verticalBox.getChildren().add(durationLabel);

    return verticalBox;
  }

  /**
   * Builds a JavaFX VBox component representing a Task.
   * The task box includes a CheckBox which reflects
   * and can change the completion status of the task.
   *
   * @param task       the Task to be represented in the VBox.
   * @param controller the ApplicationController
   *                   which will re-render the week when the Task's completion status changes.
   * @return a VBox component representing the Task.
   */
  public static VBox buildTask(Task task, ApplicationController controller) {
    VBox verticalBox = initVbox(task);
    setName(controller, task, verticalBox);
    setDescription(task, verticalBox);
    setCategory(task, verticalBox);

    CheckBox checkBox = new CheckBox();
    checkBox.setFont(Fonts.customFont);
    checkBox.setSelected(task.getIsComplete());
    verticalBox.getChildren().add(checkBox);
    checkBox.setOnAction(event -> {
      task.setIsComplete(checkBox.isSelected());
      controller.renderWeek();
    });

    return verticalBox;
  }

  /**
   * Initialize the VBox based on the activity
   *
   * @param activity Activity of the Journal
   * @return VBox with specific preferences
   */
  private static VBox initVbox(Activity activity) {
    VBox verticalBox = new VBox();
    if (activity instanceof Event) {
      verticalBox.setStyle("-fx-background-color: #F29727;"
          + "-fx-background-radius: 20px; "
          + "-fx-border-color: #000000; "
          + "-fx-border-radius: 12px; "
          + "-fx-border-width: 4px;");
    } else if (activity instanceof Task) {
      verticalBox.setStyle("-fx-background-color: #22A699;"
          + "-fx-background-radius: 20px; "
          + "-fx-border-color: #000000; "
          + "-fx-border-radius: 12px; "
          + "-fx-border-width: 4px;");
    }
    verticalBox.setAlignment(Pos.TOP_CENTER);
    BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.8f),
        CornerRadii.EMPTY, Insets.EMPTY);
    verticalBox.setBackground(new Background(backgroundFill));
    return verticalBox;
  }

  /**
   * Sets the name label of an activity to a VBox
   *
   * @param controller Controller of the Journal
   * @param activity Activity of the Journal
   * @param verticalBox VBox need to be set
   */
  private static void setName(ApplicationController controller,
                               Activity activity, VBox verticalBox) {
    Random random = new Random();
    Label nameLabel = new Label();
    nameLabel.setFont(Fonts.customFont);
    if (activity instanceof Event) {
      nameLabel.setText("Event: " + TagParser.removeTags(activity.getName()));
    } else if (activity instanceof Task) {
      nameLabel.setText("Task: " + TagParser.removeTags(activity.getName()));
    }
    nameLabel.setWrapText(true);
    verticalBox.getChildren().add(nameLabel);
    // should we be able to have more than one category?
    if (!TagParser.parseTags(activity.getName()).isEmpty()) {
      Category category = new Category(TagParser.parseTags(activity.getName()).get(0),
          Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
      if (!controller.getJournalWeek().getCategories().contains(category)) {
        controller.getJournalWeek().addCategory(category);
        activity.setCategory(category);
      } else {
        List<Category> categoryList = controller.getJournalWeek().getCategories();
        activity.setCategory(categoryList.get(categoryList.indexOf(category)));
      }
    }
  }

  /**
   * Sets the description label of an activity to a VBox
   *
   * @param activity Activity of the Journal
   * @param verticalBox VBox need to be set
   */
  private static void setDescription(Activity activity, VBox verticalBox) {
    if (activity.getDescription() != null) {
      Label descriptionLabel = new Label();
      descriptionLabel.setFont(Fonts.customFont);
      descriptionLabel.setText("Description: " + LinkParser.removeLinks(activity.getDescription()));
      descriptionLabel.setWrapText(true);
      verticalBox.getChildren().add(descriptionLabel);
      verticalBox.getChildren().addAll(LinkParser.parseLinks(activity.getDescription()));
    }
  }

  /**
   * Sets the category label of an activity to a VBox
   *
   * @param activity Activity of the Journal
   * @param verticalBox VBox need to be set
   */
  private static void setCategory(Activity activity, VBox verticalBox) {
    if (activity.getCategory() != null) {
      Label categoryLabel = new Label();
      categoryLabel.setText("Category: " + activity.getCategory().getName());
      categoryLabel.setWrapText(true);
      categoryLabel.setFont(Fonts.customFont);
      categoryLabel.setTextFill(activity.getCategory().getColor());
      verticalBox.getChildren().add(categoryLabel);
    }
  }

  /**
   * Builds a weekly overview of a journal, including the total events, total tasks
   * and completion percentage.
   * The overview is formatted and stylized within a VBox layout.
   *
   * @param journalWeek The JournalWeek object that contains the weekly overview.
   * @return A VBox layout that contains labels for total events, total tasks, and
   *         task completion percentage (only if the total tasks is not zero).
   */
  public static VBox buildWeeklyOverview(JournalWeek journalWeek) {
    VBox verticalBox = new VBox();

    verticalBox.setAlignment(Pos.TOP_CENTER);

    Label totalEventLabel =
        new Label("Total Events: " + journalWeek.getWeeklyOverview().getTotalEvents());
    totalEventLabel.setFont(Fonts.customFont);
    verticalBox.getChildren().add(totalEventLabel);

    Label totalTaskLabel =
        new Label("Total Tasks: " + journalWeek.getWeeklyOverview().getTotalTasks());
    totalTaskLabel.setFont(Fonts.customFont);
    verticalBox.getChildren().add(totalTaskLabel);

    WeeklyOverview weeklyOverview = journalWeek.getWeeklyOverview();
    if (weeklyOverview.getTotalTasks() != 0) {
      String completedPercent =
          Math.round((double) weeklyOverview.getCompletedTasks()
              / weeklyOverview.getTotalTasks() * 100) + "% tasks completed";
      Label percentLabel = new Label(completedPercent);
      percentLabel.setFont(Fonts.customFont);
      verticalBox.getChildren().add(percentLabel);
    }

    return verticalBox;
  }

  /**
   * This method retrieves a user password.
   *
   * @param controller Controller to the Journal
   * @return A hashed password as a string,
   *        or null if no password was entered or the dialog was cancelled.
   */
  public static String getPassword(ApplicationController controller) {
    // Create a new dialog
    Dialog<String> dialog = new Dialog<>();
    dialog.setTitle("Password");
    dialog.setHeaderText("Please enter your password:");

    // Set up the dialog pane
    DialogPane dialogPane = dialog.getDialogPane();
    dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // Create a password field
    PasswordField passwordField = new PasswordField();

    // Create layout and add to dialog pane
    VBox vbox = new VBox();
    vbox.getChildren().addAll(new Label("Password:"), passwordField);
    dialogPane.setContent(vbox);

    // Convert the result to a string when the OK button is clicked
    dialog.setResultConverter(button -> {
      if (button.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
        return HashPassword.hash(passwordField.getText());
      }
      controller.disableMenuBar(false);
      return null;
    });

    // Show the dialog and get the result
    Optional<String> result = dialog.showAndWait();
    return result.orElse(null);
  }

  /**
   * This method sets a user password.
   *
   * @return A hashed password as a string,
   *        or null if the passwords do not match or the dialog was cancelled.
   */
  public static String setPassword() {
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Set Password");
    dialog.setHeaderText("Please enter your password twice to confirm:");
    // Set the button types.
    ButtonType setPasswordButtonType =
        new ButtonType("Set Password", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(setPasswordButtonType, ButtonType.CANCEL);
    // Create the password fields and labels
    PasswordField password = new PasswordField();
    password.setPromptText("Password");
    PasswordField confirmPassword = new PasswordField();
    confirmPassword.setPromptText("Confirm Password");

    GridPane grid = new GridPane();
    grid.add(new Label("Password:"), 0, 0);
    grid.add(password, 1, 0);
    grid.add(new Label("Confirm Password:"), 0, 1);
    grid.add(confirmPassword, 1, 1);
    dialog.getDialogPane().setContent(grid);

    // Enable/Disable Set Password button depending on whether passwords entered match
    Node setPasswordButton = dialog.getDialogPane().lookupButton(setPasswordButtonType);
    setPasswordButton.setDisable(true);

    confirmPassword.textProperty().addListener((observable, oldValue, newValue) ->
        setPasswordButton.setDisable(!password.getText().equals(newValue)));

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == setPasswordButtonType) {
        return new Pair<>(password.getText(), confirmPassword.getText());
      }
      return null;
    });
    Optional<Pair<String, String>> result = dialog.showAndWait();
    return result.map(stringStringPair -> HashPassword.hash(stringStringPair.getKey()))
        .orElse(null);
  }
}
