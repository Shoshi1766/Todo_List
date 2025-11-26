import java.util.Scanner;
import java.util.List;

public class TaskUI {
    private void addTask() {
        System.out.print("Title: ");
        String title = in.nextLine();
        System.out.print("Description: ");
        String desc = in.nextLine();
        System.out.println(service.addTask(new Task(title, desc)));
        System.out.println("Task added!");
    }

    private void updateTask() {
        System.out.print("ID: ");
        int id = in.nextInt();
        in.nextLine();
        System.out.print("Title (leave empty to skip): ");
        String title = in.nextLine();
        if (title.isEmpty()) title = null;
        System.out.print("Description (leave empty to skip): ");
        String desc = in.nextLine();
        if (desc.isEmpty()) desc = null;
        System.out.print("Status (leave empty to skip): ");
        String status = in.nextLine();
        if (status.isEmpty()) status = null;
        Task updated = service.updateTask(id, title, desc, status);
        if (updated != null)
            System.out.println("Task updated: " + updated);
        else
            System.out.println("Task not found!");
    }


    private void deleteTask() {
        System.out.print("ID: ");
        int id = in.nextInt();
        in.nextLine();
        Task taskDeleted = service.deleteTask(id);
        if (taskDeleted != null)
            System.out.println("Task deleted!\n" + taskDeleted);
        else
            System.out.println("Task not found!");
    }

    private void findTaskByID() {
        System.out.print("ID: ");
        int id = in.nextInt();
        in.nextLine();
        Task taskFounded = service.getTaskById(id);
        if (taskFounded != null)
            System.out.println("Task founded!\n" + taskFounded);
        else
            System.out.println("Task not found!");
    }

    private void markDone() {
        System.out.print("Task ID to mark as DONE: ");
        int id = (in.nextInt());
        Task taskMarked = service.updateTaskToDone(id);
        if (taskMarked != null)
            System.out.println("Task Updated!\n" + taskMarked);
        else
            System.out.println("Task not found!");
    }


    private void findTasks() {
        System.out.print("Search text: ");
        String text = in.next();
        in.nextLine();
        List<Task> found = service.findTaskByTitleOrDescription(text);
        if (found.isEmpty())
            System.out.println("Task not found!");
        else
            found.forEach(System.out::println);
    }

    private void sortTasks() {
        List<Task> sortTasks = service.sortTasksByStatus();
        if (sortTasks.isEmpty())
            System.out.println("Tasks not found!");
        else
            sortTasks.forEach(System.out::println);
    }

    Scanner in = new Scanner(System.in);
    private final TaskService service;

    public TaskUI(TaskService service) {
        this.service = service;
    }

    public void start() {
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

            int choice = (in.nextInt());
            in.nextLine();
            switch (choice) {
                case 1:
                    System.out.println(service.listAllTasks());
                    break;
                case 2:
                    addTask();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    findTaskByID();
                    break;
                case 6:
                    markDone();
                    break;
                case 7:
                    findTasks();
                    break;
                case 8:
                    sortTasks();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}


