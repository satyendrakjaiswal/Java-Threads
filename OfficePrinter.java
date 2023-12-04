import java.util.concurrent.Semaphore;

public class OfficePrinter {
    public static void main(String[] args) {
        Semaphore printerSemaphore = new Semaphore(2);

        // Users trying to print documents
        new Thread(new User("Alice", printerSemaphore)).start();
        new Thread(new User("Bob", printerSemaphore)).start();
        new Thread(new User("Charlie", printerSemaphore)).start();
        new Thread(new User("David", printerSemaphore)).start();
        new Thread(new User("Five", printerSemaphore)).start();
        new Thread(new User("Six", printerSemaphore)).start();

    }
}

class User implements Runnable {
    private final String name;
    private final Semaphore printerSemaphore;

    public User(String name, Semaphore printerSemaphore) {
        this.name = name;
        this.printerSemaphore = printerSemaphore;
    }

    @Override
    public void run() {
        try {
            // Acquire a permit from the Semaphore
            System.out.println(name + " is waiting to use the printer.");
            printerSemaphore.acquire();

            // Simulate printing time
            System.out.println(name + " is printing a document.");
            Thread.sleep((long) (Math.random() * 1000));

            // Release the permit after printing is completed
            System.out.println(name + " has finished printing.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            printerSemaphore.release(); // Release the permit
        }
    }
}
