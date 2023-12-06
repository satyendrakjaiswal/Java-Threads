import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class FibonacciCalculator extends RecursiveTask<Long> {
    private final int n;

    public FibonacciCalculator(int n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 1) {
            return (long) n;
        } else {
            FibonacciCalculator fib1 = new FibonacciCalculator(n - 1);
            fib1.fork();

            FibonacciCalculator fib2 = new FibonacciCalculator(n - 2);
            return fib2.compute() + fib1.join();
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FibonacciCalculator fibonacciCalculator = new FibonacciCalculator(10);
        long result = forkJoinPool.invoke(fibonacciCalculator);
        System.out.println("Result: " + result);
    }
}
