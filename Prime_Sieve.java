
import java.util.concurrent.*;
import java.util.*;

public class Prime_Sieve {
    private static final int N = 999999999;

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final boolean[] isPrime = new boolean[N + 1];
    private static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        ExecutorService producerExecutor = Executors.newSingleThreadExecutor();
        ExecutorService consumerExecutor = Executors.newFixedThreadPool(NUM_THREADS);

        long startTime = System.nanoTime();

        producerExecutor.submit(() -> {
            try {
                for (int i = 2; i * i <= N; i++) {
                    if (isPrime[i]) {
                        queue.put(i);
                    }
                }
                for (int i = 0; i < NUM_THREADS; i++) {
                    queue.put(-1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        for (int i = 0; i < NUM_THREADS; i++) {
            consumerExecutor.submit(() -> {
                try {
                    while (true) {
                        int prime = queue.take();
                        if (prime == -1) break;

                        for (int j = prime * prime; j <= N; j += prime) {
                            isPrime[j] = false;
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        producerExecutor.shutdown();
        consumerExecutor.shutdown();
        producerExecutor.awaitTermination(1, TimeUnit.MINUTES);
        consumerExecutor.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e6 + " ms");

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= N; i++) {
            if (isPrime[i]) primes.add(i);
        }
        System.out.println("Total Primes Found: " + primes.size());
    }
}