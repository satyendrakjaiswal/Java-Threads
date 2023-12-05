import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTermination {
    public static void main(String[] args) {
        // Create an ExecutorService with a fixed thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Submit tasks to the pool
        for (int i = 0; i < 5; i++) {
            executorService.submit(new WorkerTask());
        }

        // Allow tasks to work for some time
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shut down the ExecutorService
        executorService.shutdown();

        // Attempt to interrupt any remaining tasks
        try {
            executorService.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class WorkerTask implements Runnable {
        public void run() {
            while (!Thread.interrupted()) {
                // Perform tasks
                System.out.println("Working...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Handle interruption if needed
                }
            }
            System.out.println("Task terminated gracefully.");
        }
    }
}
