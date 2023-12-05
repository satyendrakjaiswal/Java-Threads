public class SharedFlagTermination {
    private static volatile boolean shutdownRequested = false;

    public static void main(String[] args) {
        // Start multiple threads
        Thread thread1 = new WorkerThread();
        Thread thread2 = new WorkerThread();

        thread1.start();
        thread2.start();

        // Allow threads to work for some time
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Request shutdown
        shutdownRequested = true;

        // Interrupt threads
        thread1.interrupt();
        thread2.interrupt();
    }

    static class WorkerThread extends Thread {
        public void run() {
            while (!shutdownRequested) {
                // Perform tasks
                System.out.println(Thread.currentThread().getName() +" Working...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Handle interruption if needed
                }
            }
            System.out.println(Thread.currentThread().getName() +" Thread terminated gracefully.");
        }
    }
}
