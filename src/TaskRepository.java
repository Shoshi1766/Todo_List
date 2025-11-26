import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;

/**
 * Repository layer for storing and managing tasks.
 * Handles reading/writing tasks from/to JSON file.
 */
public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Constructor loads tasks from JSON file.
     *
     * @param jsonName JSON file path.
     */
    public TaskRepository(String jsonName) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonName)));
            loadTasks(json);
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    /**
     * Returns all tasks in the repository.
     *
     * @return List of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a new task and saves to JSON file.
     *
     * @param task Task to add.
     * @return The added task.
     */
    public Task addTask(Task task) {
        tasks.add(task);
        saveToFile("data.json");
        return task;
    }
    /**
     * Updates an existing task by ID.
     * @param id Task ID.
     * @param title New title (nullable to skip update).
     * @param description New description (nullable to skip update).
     * @param status New status (nullable to skip update).
     * @return Updated task or null if not found.
     */
    public Task updateTask(int id, String title, String description, String status) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                if (title != null && !title.trim().isEmpty()) {
                    task.setTitle(title);
                }
                if (description != null && !description.trim().isEmpty()) {
                    task.setDescription(description);
                }
                if (status != null && !status.trim().isEmpty()) {
                    try {
                        task.setStatus(Status.valueOf(status.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status. Keeping old status.");
                    }
                }

                saveToFile("data.json");
                return task;
            }
        }
        return null;
    }

    /**
     * Deletes a task by ID and updates the JSON file.
     * @param id Task ID to delete.
     * @return Deleted task or null if not found.
     */
    public Task deleteTask(int id) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId() == id) {
                iterator.remove();
                saveToFile("data.json");
                return task;
            }
        }
        return null;
    }
    /**
     * Finds a task by its ID.
     * @param id Task ID.
     * @return Task if found, else null.
     */
    public Task getTaskById(int id) {
        for (Task task : tasks)
            if (task.getId() == id)
                return task;
        return null;
    }

    /**
     * Returns a formatted string listing all tasks.
     * @return String representation of all tasks.
     */
    public String listAllTasks() {
        StringBuilder sb = new StringBuilder("List all tasks:\n");
        for (Task task : tasks) {
            sb.append(task.toString()).append("\n");
        }
        return sb.toString();
    }
    /**
     * Parses a JSON object into a Task instance.
     * @param json JSON string representing a task.
     * @return Task object or null if invalid.
     */
    private Task parseTask(String json) {
        int id = 0;
        String title = "", description = "", status = "";

        Pattern pattern = Pattern.compile("\"(\\w+)\"\\s*:\\s*(\"((?:[^\"\\\\]|\\\\.)*)\"|(\\d+))");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(3) != null ? matcher.group(3) : matcher.group(4);

            if (value == null) continue;

            if (matcher.group(3) != null) {
                value = value.replace("\\\"", "\"").replace("\\\\", "\\");
            }

            switch (key) {
                case "id":
                    try {
                        id = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid id: " + value + ". Using 0.");
                        id = 0;
                    }
                    break;
                case "title":
                    title = value;
                    break;
                case "description":
                    description = value;
                    break;
                case "status":
                    status = value;
                    break;
            }
        }

        if (title.isEmpty() && description.isEmpty()) {
            return null;
        }
        return new Task(id, title, description, status);
    }
    /**
     * Loads multiple tasks from JSON string into repository.
     * @param json JSON string representing tasks array.
     */
    private void loadTasks(String json) {
        json = json.trim();

        if (json.isEmpty() || json.equals("[]")) return;
        if (json.startsWith("[")) json = json.substring(1);
        if (json.endsWith("]")) json = json.substring(0, json.length() - 1);

        List<String> objects = new ArrayList<>();
        int braceCount = 0;
        boolean inString = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
            }

            if (!inString) {
                if (c == '{') braceCount++;
                if (c == '}') braceCount--;
            }

            current.append(c);

            if (braceCount == 0 && !inString && current.length() > 0) {
                objects.add(current.toString().trim());
                current.setLength(0);
            }
        }

        for (String obj : objects) {
            Task task = parseTask(obj);
            if (task != null) {
                tasks.add(task);
            }
        }
    }
    /**
     * Escapes special characters in a string for JSON.
     * @param text Input text.
     * @return Escaped string.
     */
    private String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
    /**
     * Saves current tasks to JSON file.
     * @param fileName File path to save tasks.
     */
    private void saveToFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);

            sb.append("  {\n");
            sb.append("    \"id\": ").append(t.getId()).append(",\n");
            sb.append("    \"title\": \"").append(escapeJson(t.getTitle())).append("\",\n");
            sb.append("    \"description\": \"").append(escapeJson(t.getDescription())).append("\",\n");
            sb.append("    \"status\": \"")
                    .append(escapeJson(t.getStatus().name()))
                    .append("\"\n");
            sb.append("  }");

            if (i < tasks.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("]");

        try {
            Files.write(Paths.get(fileName), sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Error writing JSON file: " + e.getMessage());
        }
    }


}
