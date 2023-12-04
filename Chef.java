import java.util.concurrent.CountDownLatch;

public class Chef {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch coursesReady = new CountDownLatch(5);

        // Kitchen tasks for each course
        new Thread(new CookingTask("Appetizer", coursesReady)).start();
        new Thread(new CookingTask("Soup", coursesReady)).start();
        new Thread(new CookingTask("Main Course", coursesReady)).start();
        new Thread(new CookingTask("Dessert", coursesReady)).start();
        new Thread(new CookingTask("Coffee", coursesReady)).start();

        // Wait for all courses to be ready
        coursesReady.await();

        System.out.println("All courses are ready! Let's serve the meal.");
    }
}

class CookingTask implements Runnable {
    private final String course;
    private final CountDownLatch latch;

    public CookingTask(String course, CountDownLatch latch) {
        this.course = course;
        this.latch = latch;
    }

    @Override
    public void run() {
        // Simulate cooking time
        try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println(course + " is ready!");
            latch.countDown(); // Signal that this course is ready
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
