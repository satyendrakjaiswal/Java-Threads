public class InterruptExample extends Thread {
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Perform a time-consuming task
                System.out.println("Working...");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // Handle interruption gracefully
            System.out.println(Thread.currentThread().getName() + " Thread interrupted!");
        }
    }

    public static void main(String[] args) {
        InterruptExample thread = new InterruptExample();
        thread.start();

        // Allow the thread to work for some time
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrupt the thread
        thread.interrupt();
    }
}
