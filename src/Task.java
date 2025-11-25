public class Task {
    private static int counter = 0;
    private int id;
    private String title;
    private String description;
    private Status status;

    public Task(String title, String description) {
        this.id = ++counter;
        this.title = title;
        this.description = description;
        this.status=Status.NEW;
    }
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

    public void setStatus(String status) {
//        this.status = Status.valueOf(status);
        try {
            this.status = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid status: " + status + ". Allowed values: NEW, IN_PROGRESS, DONE"
            );
        }
    }
    public Status getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return "Task Details:\nId: "+this.id+"\nTitle: "+this.title+"\nDescription: "+this.description+"\nStatus: "+this.status;
    }
}
