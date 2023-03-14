package com.funny.study.java.thread.async;

import com.sun.xml.internal.ws.util.CompletedFuture;

import java.util.concurrent.*;

public class FutureTest {

    public void testFutureTask() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("callable running");
                if (Math.random() < 0.5) {
                    throw new RuntimeException("callable throw exception");
                }
                return "callable";
            }
        };

        FutureTask<String> futureTask = new FutureTask<>(callable);
        Executors.newCachedThreadPool().execute(futureTask);
        try {
            String res = futureTask.get();
            System.out.println("callable return: " + res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
