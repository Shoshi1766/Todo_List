import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskService {

    private final TaskRepository repository;
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.getTasks();
    }
    public Task addTask(Task task) {
        repository.addTask(task);
        return task;
    }

    public Task updateTask(Task updatedTask) {
        return repository.updateTask(updatedTask);
    }

    public Task deleteTask(int id) {
        return repository.deleteTask(id);
    }

    public Task getTaskById(int id) {
       return repository.getTaskById(id);
    }

    public String listAllTasks() {
       return repository.listAllTasks();
    }


    public Task updateTaskToDone(int id){
            for (Task task : repository.getTasks())
                if (task.getId() == id) {
                    task.setStatus(Status.DONE);
                    return task;
                }
            return null;
        }
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

        public List<Task> sortTasksByStatus(){
            List<Task> sortedTasks = new ArrayList<>(repository.getTasks());
            sortedTasks.sort(Comparator.comparing(Task::getStatus));
            return sortedTasks;
        }
    }

