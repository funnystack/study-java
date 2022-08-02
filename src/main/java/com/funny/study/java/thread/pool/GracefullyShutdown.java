package com.funny.study.java.thread.pool;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class GracefullyShutdown {


    protected void initGraceFullyShowdown(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                shutdownGracefully();
            }
        });
    }

    private void shutdownGracefully(){

//        shutdownThreadPool();
    }

    private void shutdownThreadPool(ExecutorService threadpool ,String alias){
        threadpool.shutdown();

        try {
            if(!threadpool.awaitTermination(60, TimeUnit.SECONDS)){
                threadpool.shutdownNow();
                if(!threadpool.awaitTermination(60, TimeUnit.SECONDS)){
                    throw new RuntimeException("stop thread pool error");

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            threadpool.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }
}
