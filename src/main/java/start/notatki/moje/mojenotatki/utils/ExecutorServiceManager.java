package start.notatki.moje.mojenotatki.utils;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.*;

public class ExecutorServiceManager {
    private static final Map<String, ExecutorService> executors = new HashMap<>();

    public static ExecutorService createCachedThreadPool(String name) {
        ExecutorService executor = Executors.newCachedThreadPool();
        executors.put(name, executor);
        return executor;
    }

    public static ExecutorService createFixedThreadPool(String name, int nThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        executors.put(name, executor);
        return executor;
    }

    public static ExecutorService getExecutorService(String name) {
        return executors.get(name);
    }

    public static void shutdownAll() {
        for (Map.Entry<String, ExecutorService> entry : executors.entrySet()) {
            ExecutorService executor = entry.getValue();
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
