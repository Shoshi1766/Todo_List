public class Main {
    public static void main(String[] args) {
        Task t1 = new Task("Dance", "funny with Orit");
        System.out.println(t1);
        t1.setStatus(String.valueOf(Status.DONE));
        System.out.println(t1);
    }
}
