package com.netty.test.utils.concurrent;

import javax.annotation.processing.Processor;
import javax.tools.JavaCompiler;
import java.util.Locale;
import java.util.concurrent.*;

/**
 * 执行管理器
 *
 */
public class ExecutorUtil {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(1,1,1000,TimeUnit.MINUTES,new LinkedBlockingDeque<>());
        Ri ri = new Ri();

        try {
            Cc c = new Cc();
            Object call = c.call();

            Future<Cc> submit = executorService.submit(ri, c);
            System.out.println(submit.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class Ri implements Runnable{
        @Override
        public void run() {

        }
    }

    static class Cc implements  Callable{

        @Override
        public Object call() throws Exception {
            return null;
        }
    }


}
