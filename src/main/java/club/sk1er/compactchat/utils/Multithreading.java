package club.sk1er.compactchat.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mitchell Katz on 5/8/2017.
 */
public class Multithreading {

    static AtomicInteger counter = new AtomicInteger(0);

    public static ExecutorService POOL = Executors.newFixedThreadPool(8, r -> new Thread(r, String.format("Thread %s", counter.incrementAndGet())));
    private static ScheduledExecutorService RUNNABLE_POOL = Executors.newScheduledThreadPool(2, r -> new Thread(r, "Thread " + counter.incrementAndGet()));

    public static void runAsync(Runnable runnable) {
        POOL.execute(runnable);
    }

    public static void schedule(Runnable r, long initialDelay, long delay, TimeUnit unit) {
        RUNNABLE_POOL.scheduleAtFixedRate(r, initialDelay, delay, unit);
    }

    public static int getTotal() {
        return ((ThreadPoolExecutor) Multithreading.POOL).getActiveCount();
    }
}