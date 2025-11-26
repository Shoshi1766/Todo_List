import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();

    public TaskRepository(String jsonName) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonName)));
            loadTasks(json);
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    private Task parseTask(String json) {
        int id = 0;
        String title = "", description = "", status = "";

        Pattern pattern = Pattern.compile("\"(\\w+)\"\\s*:\\s*(\"((?:[^\"\\\\]|\\\\.)*)\"|(\\d+))");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(3) != null ? matcher.group(3) : matcher.group(4);

            if (value == null) continue;

            // המרת escape sequences ל־characters אמיתיים
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
                case "title": title = value; break;
                case "description": description = value; break;
                case "status": status = value; break;
            }
        }

        if (title.isEmpty() && description.isEmpty()) {
            return null;
        }

        return new Task(id, title, description, status);
    }

    private void loadTasks(String json) {
        json = json.trim();

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

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tasks:\n");
        for (Task task : tasks) {
            sb.append(task.toString()).append("\n\n");
        }
        return sb.toString();
    }
}
