package com.netty.test.utils.concurrent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExcutorUtil {
    private static ExecutorInter executorInter;

    static {
        executorInter = new ExecutorImpl();
    }


    public void execute(Runnable runnable) {
        executorInter.execute(runnable);
    }

    public <V> Future<V> submit(Callable<V> callable) {
        return executorInter.submit(callable);
    }

    public boolean state(ExecutorService executorService) {
        return executorInter.state();
    }

    public void shutdown(Runnable runnable) {
        executorInter.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return executorInter.shutdownNow();
    }

    public void schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        executorInter.schedule(runnable, delay, timeUnit);
    }

    public <V> V schedule(Callable<V> callable, long delay, TimeUnit timeUnit) {
        return executorInter.schedule(callable, delay, timeUnit);
    }


}
