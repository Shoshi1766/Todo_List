/**
 * Represents a task object with ID, title, description, and status.
 */
public class Task {
    private static int counter = 0;
    private int id;
    private String title;
    private String description;
    private Status status;

    /**
     * Constructor for new tasks with default status NEW.
     */
    public Task(String title, String description) {
        this.id = ++counter;
        this.title = title;
        this.description = description;
        this.status=Status.NEW;
    }
    /**
     * Constructor with specified status.
     */
    public Task(String title, String description, String status) {
        this.id = ++counter;
        this.title = title;
        this.description = description;
        try {
            this.status = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.status = Status.NEW;
        }
    }
    /**
     * Constructor with specified ID (used when loading from JSON).
     */
    protected Task(int id, String title, String description, String status){
        this.id = id;
        this.title = title;
        this.description = description;
        try {
            this.status = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.status = Status.NEW;
        }
        if (id > counter) counter = id;
    }

    // Getters and setters...
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Task.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(Status status) {
            this.status = status;
        }
    public Status getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return "Id: "+this.id+", Title: "+this.title+", Description: "+this.description+", Status: "+this.status;
    }
}
