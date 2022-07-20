package com.funny.study.java.queue.seckill;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SecKillDemo {
    private static Integer stock = 3;
    private static BlockingQueue<RequestPromise> queue = new LinkedBlockingQueue<>(9);

    public static void main(String[] args) throws InterruptedException {
        SecKillDemo secKillDemo = new SecKillDemo();
        secKillDemo.consume();


        List<Future<Result>> futureList = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            UserRequest request = new UserRequest(i);
            countDownLatch.countDown();
            Future<Result> future = executorService.submit(() -> {
                return secKillDemo.operate(request);
            });
            futureList.add(future);
        }

        futureList.forEach(t -> {
            try {
                Result result = t.get(300, TimeUnit.MILLISECONDS);
                System.out.println("请求客户端响应:" + result.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });

    }


    private Result operate(UserRequest userRequest) throws InterruptedException {
        RequestPromise promise = new RequestPromise(userRequest);

        boolean addQueue = queue.offer(promise, 200, TimeUnit.MILLISECONDS);
        if (!addQueue) {
            return new Result(false, "系统繁忙");
        }
        synchronized (promise) {
            try {
                promise.wait(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return new Result(false, "等待超时");
            }
        }
        return promise.getResult();
    }


    private void consume() {
        new Thread(() -> {
            List<RequestPromise> requestPromiseList = Lists.newArrayList();
            while (true) {
                if (queue.isEmpty()) {
                    try {
                        Thread.sleep(20);
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (queue.peek() != null) {
                    requestPromiseList.add(queue.poll());
                }
                Integer count = requestPromiseList.stream().mapToInt(t -> t.getUserRequest().getCount()).sum();
                System.out.println(Thread.currentThread().getName() + "合并扣减库存" + count);
                if (count <= stock) {
                    stock -= count;
                    requestPromiseList.forEach(t -> {
                        synchronized (t) {
                            t.setResult(new Result(true, "秒杀成功"));
                            t.notify();
                        }
                    });
                    requestPromiseList.clear();
                    continue;
                } else {
                    requestPromiseList.forEach(t -> {
                        stock -= t.getUserRequest().getCount();
                        if (stock < 0) {
                            t.setResult(new Result(true, "秒杀成功"));
                        } else {
                            t.setResult(new Result(false, "库存不足秒杀失败"));
                        }
                        synchronized (t) {
                            t.notify();
                        }
                    });
                    requestPromiseList.clear();
                    continue;
                }

            }
        }, "consume").start();
    }

}


