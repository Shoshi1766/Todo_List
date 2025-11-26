public class Main {
    public static void main(String[] args) {
            TaskRepository repository = new TaskRepository("data.json");
            TaskService service = new TaskService(repository);
            TaskUI ui = new TaskUI(service);
            ui.start();

    }
}
