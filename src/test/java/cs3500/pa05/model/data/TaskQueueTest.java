package cs3500.pa05.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test class for TaskQueue.
 * It checks if TaskQueue's methods work as expected.
 */
class TaskQueueTest {
  private TaskQueue taskQueue;
  private Task task1;
  private Task task2;
  private Task task3;

  /**
   * Setup method to initialize common test data.
   */
  @BeforeEach
  public void setUp() {
    Category homeCategory = new Category("Home", Color.BLUE);
    Category workCategory = new Category("Work", Color.RED);
    Category otherCategory = new Category("Other", Color.GREEN);
    task1 = new Task(
        "Task 1", "Description 1", DayOfWeek.MON, homeCategory, false);
    task2 = new Task(
        "Task 2", "Description 2", DayOfWeek.TUE, workCategory, true);
    task3 = new Task(
        "Task 3", "Description 3", DayOfWeek.WED, otherCategory, false);
    taskQueue = new TaskQueue();
  }


  /**
   * Test for TaskQueue's constructor with task list.
   * It checks if the task list is set to the queue.
   */
  @Test
  public void testConstructorWithTaskList() {
    List<Task> tasks = Arrays.asList(task1, task2, task3);
    TaskQueue queueWithTasks = new TaskQueue(tasks);
    assertEquals(tasks, queueWithTasks.getTasks());
  }

  /**
   * Test for clearQueue method. It checks if the queue is cleared.
   */
  @Test
  public void testClearQueue() {
    List<Task> tasks = Arrays.asList(task1, task2, task3);
    taskQueue.addAllTask(tasks);
    taskQueue.clearQueue();
    assertTrue(taskQueue.getTasks().isEmpty());
  }

  /**
   * Test for addTask method. It checks if the task is added to the queue.
   */
  @Test
  public void testAddTask() {
    taskQueue.addTask(task1);
    assertEquals(List.of(task1), taskQueue.getTasks());
  }

  /**
   * Test for addAllTask method. It checks if all tasks from a list are added to the queue.
   */
  @Test
  public void testAddAllTask() {
    List<Task> tasks = Arrays.asList(task1, task2);
    taskQueue.addAllTask(tasks);
    assertEquals(tasks, taskQueue.getTasks());
  }
}
