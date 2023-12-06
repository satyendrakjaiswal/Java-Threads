import java.util.concurrent.CompletableFuture;

public class AsyncContext {
    private static final ThreadLocal<String> requestIdThreadLocal = new ThreadLocal<>();

    public static CompletableFuture<Void> processAsyncRequest() {
        return CompletableFuture.supplyAsync(() -> {
            // Simulating asynchronous processing
            String requestId = generateRequestId();
            requestIdThreadLocal.set(requestId);
            System.out.println("Processing request asynchronously with ID: " + requestId);
            return processIntermediateResult();
        }).thenAccept(result -> {
            // Accessing the thread local variable in the next stage
            String requestId = requestIdThreadLocal.get();
            System.out.println("Finalizing request processing for ID: " + requestId);
            requestIdThreadLocal.remove();
        });
    }

    private static String generateRequestId() {
        return "REQ-" + Math.random();
    }

    private static Void processIntermediateResult() {
        // Simulating intermediate processing
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Starting asynchronous request processing...");

        CompletableFuture<Void> asyncProcess = AsyncContext.processAsyncRequest();

        // Simulating other synchronous work while waiting for the asynchronous processing to complete
        System.out.println("Doing some other work synchronously...");

        // Block and wait for the asynchronous processing to complete
        asyncProcess.join();

        System.out.println("Main thread completed.");
    }
}
