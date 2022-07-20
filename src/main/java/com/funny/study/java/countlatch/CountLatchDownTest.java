package com.funny.study.java.countlatch;

import java.util.concurrent.CountDownLatch;

public class CountLatchDownTest {


    private static final int PARLLEL_COUNT = 500;

    private static CountDownLatch countDownLatch = new CountDownLatch(PARLLEL_COUNT);


    public static void main(String[] args) {
        for (int i = 0; i < PARLLEL_COUNT; i++) {
            new Thread(new ParllelTest()).start();
            countDownLatch.countDown();
        }

    }


    static class ParllelTest implements Runnable {

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //todo
            System.out.println("this is test " + Thread.currentThread().getName());
        }
    }
}
