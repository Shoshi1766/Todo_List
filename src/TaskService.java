import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Service layer for managing tasks.
 * Handles business logic and communicates with the repository.
 */
public class TaskService {

    private final TaskRepository repository;
    /**
     * Constructor.
     * @param repository The repository instance to use for data operations.
     */
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }
    /**
     * Get all tasks from the repository.
     * @return List of all tasks.
     */
    public List<Task> getAllTasks() {
        return repository.getTasks();
    }
    /**
     * Add a new task to the repository.
     * @param task Task object to add.
     * @return The added task.
     */
    public Task addTask(Task task) {
        repository.addTask(task);
        return task;
    }
    /**
     * Update an existing task by ID.
     * @param id Task ID.
     * @param title New title (nullable to skip update).
     * @param description New description (nullable to skip update).
     * @param status New status (nullable to skip update).
     * @return Updated task or null if not found.
     */
    public Task updateTask(int id,String title, String description, String status) {
        return repository.updateTask(id,title,description,status);
    }
    /**
     * Delete a task by ID.
     * @param id Task ID to delete.
     * @return Deleted task or null if not found.
     */
    public Task deleteTask(int id) {
        return repository.deleteTask(id);
    }
    /**
     * Get a task by its ID.
     * @param id Task ID.
     * @return Task or null if not found.
     */
    public Task getTaskById(int id) {
       return repository.getTaskById(id);
    }
    /**
     * List all tasks in a formatted string.
     * @return String representation of all tasks.
     */
    public String listAllTasks() {
       return repository.listAllTasks();
    }

    /**
     * Update the status of a task to DONE.
     * @param id Task ID.
     * @return Updated task or null if not found.
     */
    public Task updateTaskToDone(int id){
        for (Task task : repository.getTasks()) {
            if (task.getId() == id) {
                task.setStatus(Status.DONE);
                return task;
            }
        }
        return null;
    }
    /**
     * Find tasks by text contained in title or description.
     * @param text Search text.
     * @return List of matching tasks.
     */
        public List<Task> findTaskByTitleOrDescription(String text){
            List<Task> result = new ArrayList<>();
            if (text == null || text.isEmpty()) {
                return result;
            }
            for (Task task : repository.getTasks()) {
                if (task.getTitle().toLowerCase().contains(text.toLowerCase()) ||
                        task.getDescription().toLowerCase().contains(text.toLowerCase())) {
                    result.add(task);
                }
            }
            return result;
        }
    /**
     * Sort tasks by status (NEW -> IN_PROGRESS -> DONE).
     * @return Sorted list of tasks.
     */
        public List<Task> sortTasksByStatus(){
            List<Task> sortedTasks = new ArrayList<>(repository.getTasks());
            sortedTasks.sort(Comparator.comparing(Task::getStatus));
            return sortedTasks;
        }
    }

