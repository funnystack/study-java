package com.funny.study.java.jvm;

/**
 *
 * jinfo -flag PrintGC pid
 * jinfo -flag +PrintGC +PrintGCDetails pid
 * jinfo -flags 22144
 * jinfo -flag +PrintGC 22144
 * 智能调整跟踪类的参数，不能调整垃圾回收器。clear
 */
public class JinfoTest {

    public static void main(String[] args) throws InterruptedException {
        int count = 1;
        while (true) {
            System.gc();
            Thread.sleep(5000);
            System.out.printf("执行了%s 次gc\n", count++);
        }
    }
}
