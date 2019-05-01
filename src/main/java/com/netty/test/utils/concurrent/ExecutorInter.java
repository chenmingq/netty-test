package com.netty.test.utils.concurrent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 *
 */
public interface ExecutorInter {

    /**
     * 提价执行一个线程任务
     *
     * @param runnable
     */
    void execute(Runnable runnable);


    /**
     * 提交一个有返回值的线程任务
     *
     * @param callable
     * @return
     */
    <V> Future<V> submit(Callable<V> callable);


    /**
     * 查看当前线程的状态
     *
     * @return
     */
    boolean state();

    /**
     * 所有一个线程
     *
     */
    void shutdown();

    /**
     * 关闭所有的任务
     *
     * @return
     */
    List<Runnable> shutdownNow();

    /**
     * 定时一个任务
     *
     * @param runnable 要执行的任务
     * @param delay    从现在开始延迟执行的时间
     * @param timeUnit 延时参数的时间单位
     */
    void schedule(Runnable runnable, long delay, TimeUnit timeUnit);


    /**
     * 定时执行一个有返回值的任务
     *
     * @param callable
     * @param delay
     * @param timeUnit
     * @param <V>
     * @return
     */
    <V> V schedule(Callable<V> callable, long delay, TimeUnit timeUnit);


}
