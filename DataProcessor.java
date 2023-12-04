import java.util.concurrent.ConcurrentLinkedQueue;

public class DataProcessor {
    private static final int NUM_PRODUCERS = 3;
    private static final int NUM_CONSUMERS = 2;
    private static final int NUM_ITEMS_PER_PRODUCER = 5;

    private ConcurrentLinkedQueue<String> dataQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor();
        dataProcessor.startProcessing();
    }

    public void startProcessing() {
        // Create producer threads
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            Thread producerThread = new Thread(() -> {
                for (int j = 0; j < NUM_ITEMS_PER_PRODUCER; j++) {
                    String data = "Data-" + Thread.currentThread().getId() + "-" + j;
                    produceData(data);
                    try {
                        Thread.sleep(100); // Simulate some processing time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            producerThread.start();
        }

        // Create consumer threads
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            Thread consumerThread = new Thread(() -> {
                while (true) {
                    String data = consumeData();
                    if (data != null) {
                        process(data);
                    }
                }
            });
            consumerThread.start();
        }
    }

    public void produceData(String data) {
        dataQueue.offer(data);
        System.out.println("Produced: " + data);
    }

    public String consumeData() {
        String data = dataQueue.poll();
        if (data != null) {
            System.out.println("Consumed: " + data);
        }
        return data;
    }

    public void process(String data) {
        // Simulate data processing
        System.out.println("Processing data: " + data);
    }
}
