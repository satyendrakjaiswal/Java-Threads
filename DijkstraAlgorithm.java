import java.util.concurrent.Semaphore;

public class DijkstraAlgorithm {
    private static final Semaphore semaphoreA = new Semaphore(1);
    private static final Semaphore semaphoreB = new Semaphore(1);

    public static void main(String[] args) {
        new Thread(new Process1()).start();
        new Thread(new Process2()).start();
    }

    static class Process1 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    if (semaphoreA.tryAcquire()) {
                        System.out.println("Process 1 acquired semaphoreA.");
                        // Simulate some work
                        Thread.sleep(2000);

                        if (semaphoreB.tryAcquire()) {
                            System.out.println("Process 1 acquired semaphoreB.");
                            // Simulate some work
                            Thread.sleep(2000);
                            System.out.println("Process 1 completed task.");
                            // Release resources
                            semaphoreB.release();
                            System.out.println("Process 1 released semaphoreB.");

                            semaphoreA.release();
                            System.out.println("Process 1 released semaphoreA.");
                        } else {
                            System.out.println("Process 1 couldn't acquire semaphoreB. Releasing semaphoreA.");
                            semaphoreA.release();
                        }
                    }
                    // Simulate some work
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Process2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    if (semaphoreB.tryAcquire()) {
                        System.out.println("Process 2 acquired semaphoreB.");
                        // Simulate some work
                        Thread.sleep(3000);

                        if (semaphoreA.tryAcquire()) {
                            System.out.println("Process 2 acquired semaphoreA.");
                            // Simulate some work
                            Thread.sleep(2000);
                            System.out.println("Process 2 completed task.");
                            // Release resources
                            semaphoreA.release();
                            System.out.println("Process 2 released semaphoreA.");

                            semaphoreB.release();
                            System.out.println("Process 2 released semaphoreB.");
                        } else {
                            System.out.println("Process 2 couldn't acquire semaphoreA. Releasing semaphoreB.");
                            semaphoreB.release();
                        }
                    }
                    // Simulate some work
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
