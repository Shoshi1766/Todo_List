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
    public Task(String title, String description, String status) {
        this.id = ++counter;
        this.title = title;
        this.description = description;
        try {
            this.status = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status: " + status + ". Defaulting to NEW.");
            this.status = Status.NEW;
        }
    }
    protected Task(int id, String title, String description, String status){
        this.id = id;
        this.title = title;
        this.description = description;
        try {
            this.status = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status: " + status + ". Defaulting to NEW.");
            this.status = Status.NEW;
        }
        if (id > counter) counter = id;
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

    public void setStatus(Status status) {
        try {
            this.status = status;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status: " + status + ". Status not changed.");
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
        return "[Id: "+this.id+", Title: "+this.title+", Description: "+this.description+", Status: "+this.status+"]";
    }
}
