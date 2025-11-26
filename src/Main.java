import java.util.Scanner;
import java.util.List;

/**
 * Main class for running the Todo List application.
 * Provides a console menu to interact with tasks.
 */
public class Main {
    /**
     * Main entry point of the application.
     * Initializes repository and service, then starts a console menu loop.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        TaskRepository repository = new TaskRepository("data.json"); // Load tasks from JSON
        TaskService service = new TaskService(repository); // Initialize service layer
        Scanner in = new Scanner(System.in);
        int choice = -1;
        while (true) {
            // Display menu header with colored text and heart symbol
            System.out.print("\u001B[31mMy-\u001B[0m");
            System.out.print("\u001B[32mTodo-\u001B[0m");
            System.out.print("\u001B[34mList \u001B[0m");
            System.out.print("\u2764");
            System.out.println("\n1. List all tasks");
            System.out.println("2. Add new task");
            System.out.println("3. Update task");
            System.out.println("4. Delete one task");
            System.out.println("5. Find task by ID");
            System.out.println("6. Mark task as DONE");
            System.out.println("7. Find tasks by title/description");
            System.out.println("8. Sort tasks by status");
            System.out.println("0. Exit");

            // Read valid menu choice
            String input = "null";
            while (!input.matches("-?\\d+")) {
                System.out.print("Choose option: ");
                input = in.next();
            }
            choice = Integer.parseInt(input);
            in.nextLine();
            switch (choice) {
                case 1:
                    // List all tasks
                    System.out.println(service.listAllTasks());
                    break;
                // Add a new task
                case 2:
                    System.out.print("Title: ");
                    String title = in.nextLine();
                    System.out.print("Description: ");
                    String desc = in.nextLine();
                    System.out.println(service.addTask(new Task(title, desc)));
                    break;
                case 3:
                    // Update existing task by ID
                    input = "null";
                    while (!input.matches("-?\\d+")) {
                        System.out.print("ID: ");
                        input = in.next();
                    }
                    int id = Integer.parseInt(input);
                    in.nextLine();
                    System.out.print("Title (leave empty to skip): ");
                    String newTitle = in.nextLine();
                    if (newTitle.isEmpty()) newTitle = null;
                    System.out.print("Description (leave empty to skip): ");
                    String newDesc = in.nextLine();
                    if (newDesc.isEmpty()) newDesc = null;
                    System.out.print("Status (leave empty to skip): ");
                    String status = in.nextLine();
                    if (status.isEmpty()) status = null;
                    Task updated = service.updateTask(id, newTitle, newDesc, status);
                    if (updated != null)
                        System.out.println("Task updated: " + updated);
                    else
                        System.out.println("Task not found!");
                    break;
                case 4:
                    // Delete task by ID
                    input = "null";
                    while (!input.matches("-?\\d+")) {
                        System.out.print("ID: ");
                        input = in.next();
                    }
                    int delId = Integer.parseInt(input);
                    in.nextLine();
                    Task deleted = service.deleteTask(delId);
                    if (deleted != null)
                        System.out.println("Task deleted: " + deleted);
                    else
                        System.out.println("Task not found!");
                    break;
                case 5:
                    // Find task by ID
                    input = "null";
                    while (!input.matches("-?\\d+")) {
                        System.out.print("ID: ");
                        input = in.next();
                    }
                    int findId = Integer.parseInt(input);
                    in.nextLine();
                    Task found = service.getTaskById(findId);
                    if (found != null)
                        System.out.println("Task found: " + found);
                    else
                        System.out.println("Task not found!");
                    break;
                case 6:
                    // Mark task as DONE
                    input = "null";
                    while (!input.matches("-?\\d+")) {
                        System.out.print("ID: ");
                        input = in.next();
                    }
                    int doneId = Integer.parseInt(input);
                    in.nextLine();
                    Task doneTask = service.updateTaskToDone(doneId);
                    if (doneTask != null)
                        System.out.println("Task Updated: " + doneTask);
                    else
                        System.out.println("Task not found!");
                    break;
                case 7:
                    // Search tasks by title or description
                    System.out.print("Search text: ");
                    String search = in.nextLine();
                    List<Task> foundTasks = service.findTaskByTitleOrDescription(search);
                    if (foundTasks.isEmpty())
                        System.out.println("Task not found!");
                    else
                        foundTasks.forEach(System.out::println);
                    break;
                case 8:
                    // Sort tasks by status and display
                    List<Task> sorted = service.sortTasksByStatus();
                    if (sorted.isEmpty())
                        System.out.println("Tasks not found!");
                    else
                        sorted.forEach(System.out::println);
                    break;
                case 0:
                    // Exit application
                    System.out.println("Exiting...");
                    in.close();
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}