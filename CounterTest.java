import java.util.concurrent.atomic.AtomicInteger;
public class CounterTest {
    public static void main(String[] args) {
        // Test locked counter
        LockedCounter lockedCounter = new LockedCounter();
        testCounter(lockedCounter, "Locked Counter");

        // Test non-blocking counter using CAS
        NonBlockingCounter nonBlockingCounter = new NonBlockingCounter();
        testCounter(nonBlockingCounter, "Non-blocking Counter (CAS)");
    }

    private static void testCounter(Counter counter, String counterType) {
        // Increment counter multiple times using multiple threads
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000000; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Verify the counter value
        int expectedCount = 5 * 10000000; // 5 threads incrementing 10000 times each
        int actualCount = counter.getCount();

        // Display the result
        System.out.println(counterType + " - Expected: " + expectedCount + ", Actual: " + actualCount +
                           ", Verification: " + (expectedCount == actualCount ? "Passed" : "Failed"));
    }
}
class LockedCounter  implements Counter {
    private int count;
    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            count++;
        }
    }

    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
}


class NonBlockingCounter implements Counter  {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        int current, next;
        do {
            current = count.get();
            next = current + 1;
        } while (!count.compareAndSet(current, next));
    }

    public int getCount() {
        return count.get();
    }
}
interface Counter {
    void increment();
    int getCount();
}
