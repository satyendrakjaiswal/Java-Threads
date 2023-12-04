import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SprintPlanning {
    public static void main(String[] args) {
        CyclicBarrier syncPoint = new CyclicBarrier(3, () -> System.out.println("Sprint planning meeting"));

        // Teams working on different components
        new Thread(new TeamMember("Frontend", syncPoint)).start();
        new Thread(new TeamMember("Backend", syncPoint)).start();
        new Thread(new TeamMember("QA", syncPoint)).start();
    }
}

class TeamMember implements Runnable {
    private final String role;
    private final CyclicBarrier barrier;

    public TeamMember(String role, CyclicBarrier barrier) {
        this.role = role;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        // Work on the component
        System.out.println(role + " is working on the component.");

        // Synchronize at the barrier
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }

        // Continue with the next phase after synchronization
        System.out.println(role + " continues to the next phase.");
    }
}
