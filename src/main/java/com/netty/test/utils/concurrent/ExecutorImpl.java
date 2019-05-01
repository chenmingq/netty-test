package com.netty.test.utils.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.List;
import java.util.concurrent.*;

public class ExecutorImpl implements ExecutorInter {


    private static final int CORE_POOL_SIZE = 100;
    private static final int MAXIMUM_POOL_SIZE = 100000;
    private static final long KEEP_ALIVE_TIME = 1000;
    private static final int WORK_QUEUE_SIZE = 1024;

    private static final String THREAD_NAME = "demo-pool-%d";

    private static final ExecutorService EXECUTOR_SERVICE;
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE;

    static {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(THREAD_NAME).build();

        EXECUTOR_SERVICE = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(WORK_QUEUE_SIZE), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


        SCHEDULED_EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

    }


    @Override
    public void execute(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }

    @Override
    public <V> Future<V> submit(Callable<V> callable) {
        return EXECUTOR_SERVICE.submit(callable);
    }

    @Override
    public boolean state() {
        return EXECUTOR_SERVICE.isShutdown();
    }

    @Override
    public void shutdown() {
        EXECUTOR_SERVICE.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return EXECUTOR_SERVICE.shutdownNow();
    }

    @Override
    public void schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        SCHEDULED_EXECUTOR_SERVICE.schedule(runnable, delay, timeUnit);
    }

    @Override
    public <V> V schedule(Callable<V> callable, long delay, TimeUnit timeUnit) {
        ScheduledFuture<V> schedule = SCHEDULED_EXECUTOR_SERVICE.schedule(callable, delay, timeUnit);
        try {
            return schedule.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
