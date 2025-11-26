public class Main {
    public static void main(String[] args) {
        TaskRepository tr1=new TaskRepository("data.json");
        System.out.println(tr1.listAll());
        System.out.println("Task added:\n"+tr1.add(new Task("Dance", "with Orit")));
        System.out.println("Task Updated:\n"+tr1.update(new Task(2,"Play","Gemboy","DONE")));
        System.out.println("Task By id:\n"+tr1.getById(5));
        System.out.println(tr1.listAll());
    }
}
