import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {

    public static void main(String[] args) {
        // Create a ScheduledExecutorService with a single thread
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // Schedule a task to run after an initial delay of 0 seconds and then every 2 seconds
        executor.scheduleAtFixedRate(ScheduledExecutorServiceExample::printCurrentTime, 0, 2, TimeUnit.SECONDS);

        // Schedule another task with an initial delay of 5 seconds and then every 5 seconds
        executor.scheduleWithFixedDelay(ScheduledExecutorServiceExample::printMessage, 5, 5, TimeUnit.SECONDS);

        // Allow the executor to run for a while
        try {
            Thread.sleep(20_000); // Run for 20 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutdown the executor
        executor.shutdown();
    }

    private static void printCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        System.out.println("Current Time: " + currentTime);
    }

    private static void printMessage() {
        System.out.println("Scheduled Message: This task runs with a fixed delay");
    }
}
