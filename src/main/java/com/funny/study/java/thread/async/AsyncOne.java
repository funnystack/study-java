package com.funny.study.java.thread.async;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这样写异步代码，除了更费电别无它用，还让代码变得更复杂.
 *
 */
public class AsyncOne {
    static ExecutorService executorService = Executors.newCachedThreadPool();
    static AsyncResult asyncResult = new AsyncResult(false);
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        String content1 = readFile("/Users/fangli/Downloads/阿里大淘宝技术2022技术年货.pdf");
        System.out.println("file 1:"+(System.currentTimeMillis()-start));

        long start2 = System.currentTimeMillis();
        readAsyncFile("/Users/fangli/Downloads/阿里大淘宝技术2022技术年货.pdf");
        int i = 0;
        for(;;){
            if(asyncResult.isReady()){
                System.out.println("file ready");
                System.out.println("file 2:"+(System.currentTimeMillis()-start2));
                break;
            }
            i++;
        }
        System.out.println("total :"+(System.currentTimeMillis()-start) + ",i="+i);
    }

    private static String readFile(String path) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(path));
        return new String(content);
    }

    private static AsyncResult readAsyncFile(String path) throws IOException {
        executorService.submit(()->{
            byte[] content = new byte[0];
            try {
                content = Files.readAllBytes(Paths.get(path));
                System.out.println("async read over");
            } catch (IOException e) {
                e.printStackTrace();
            }
            asyncResult.setReady(true);
        });
        return asyncResult;
    }
}
