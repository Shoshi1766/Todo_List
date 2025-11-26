import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
            TaskRepository repository = new TaskRepository("data.json");
            TaskService service = new TaskService(repository);
        Scanner in = new Scanner(System.in);

        while (true) {
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
            System.out.print("Choose option: ");

            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    System.out.println(service.listAllTasks());
                    break;
                case 2:
                    System.out.print("Title: ");
                    String title = in.nextLine();
                    System.out.print("Description: ");
                    String desc = in.nextLine();
                    System.out.println(service.addTask(new Task(title, desc)));
                    break;
                case 3:
                    System.out.print("ID: ");
                    int id = in.nextInt();
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
                    System.out.print("ID: ");
                    int delId = in.nextInt();
                    in.nextLine();
                    Task deleted = service.deleteTask(delId);
                    if (deleted != null)
                        System.out.println("Task deleted: " + deleted);
                    else
                        System.out.println("Task not found!");
                    break;
                case 5:
                    System.out.print("ID: ");
                    int findId = in.nextInt();
                    in.nextLine();
                    Task found = service.getTaskById(findId);
                    if (found != null)
                        System.out.println("Task found: " + found);
                    else
                        System.out.println("Task not found!");
                    break;
                case 6:
                    System.out.print("Task ID to mark as DONE: ");
                    int doneId = in.nextInt();
                    in.nextLine();
                    Task doneTask = service.updateTaskToDone(doneId);
                    if (doneTask != null)
                        System.out.println("Task Updated: " + doneTask);
                    else
                        System.out.println("Task not found!");
                    break;
                case 7:
                    System.out.print("Search text: ");
                    String search = in.nextLine();
                    List<Task> foundTasks = service.findTaskByTitleOrDescription(search);
                    if (foundTasks.isEmpty())
                        System.out.println("Task not found!");
                    else
                        foundTasks.forEach(System.out::println);
                    break;
                case 8:
                    List<Task> sorted = service.sortTasksByStatus();
                    if (sorted.isEmpty())
                        System.out.println("Tasks not found!");
                    else
                        sorted.forEach(System.out::println);
                    break;
                case 0:
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