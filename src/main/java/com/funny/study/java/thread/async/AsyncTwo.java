package com.funny.study.java.thread.async;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AsyncTwo {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        String content1 = readFile("/Users/fangli/Downloads/数字人才发展体系：粮仓模型白皮书 V12.pdf");
        System.out.println("file 1:"+(System.currentTimeMillis()-start));

        long start2 = System.currentTimeMillis();
        String content2 = readFile("/Users/fangli/Downloads/阿里大淘宝技术2022技术年货.pdf");
        System.out.println("file 2:"+(System.currentTimeMillis()-start2));

        System.out.println("total :"+(System.currentTimeMillis()-start));

    }

    private static String readFile(String path) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(path));
        return new String(content);
    }

    private static AsyncResult readAsyncFile(String path) throws IOException {
        AsyncResult asyncResult = new AsyncResult(false);
        byte[] content = Files.readAllBytes(Paths.get(path));
        asyncResult.setContent(new String(content));
        return asyncResult;
    }
}
