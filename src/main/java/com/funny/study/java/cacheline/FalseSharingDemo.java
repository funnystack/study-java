package com.funny.study.java.cacheline;

public class FalseSharingDemo {
    // 测试用的线程数
    private final static int NUM_THREADS = 4;
    // 测试的次数
    private final static int NUM_TEST_TIMES = 10;

    // 无填充 无缓存行的对象类
    // 长整形8个字节、对象头8个字节  = 16字节
    static class PlainHotVariable {

        public volatile long value = 0L;
    }

    // 有填充 有缓存行对齐的对象类
    // 长整形 8 * 7 = 56字节 +  对象头8字节 = 64字节
    static final class AlignHotVariable extends PlainHotVariable {
        public long p1, p2, p3, p4, p5, p6;
    }

    static final class CompetitorThread extends Thread {
        private final static long ITERATIONS = 500L * 1000L * 1000L;
        private PlainHotVariable plainHotVariable;

        public CompetitorThread(final PlainHotVariable plainHotVariable) {
            this.plainHotVariable = plainHotVariable;
        }

        @Override
        public void run() {
            // 一个线程对一个变量进行大量的存取操作
            for (int i = 0; i < ITERATIONS; i++) {
                plainHotVariable.value = i;
            }
        }
    }

    public static long runOneTest(PlainHotVariable[] plainHotVariables) throws Exception {
        CompetitorThread[] competitorThreads = new CompetitorThread[plainHotVariables.length];
        for (int i = 0; i < plainHotVariables.length; i++) {
            competitorThreads[i] = new CompetitorThread(plainHotVariables[i]);
        }
        final long start = System.nanoTime();
        for (Thread t : competitorThreads) {
            t.start();
        }
        for (Thread t : competitorThreads) {
            t.join();
        }
        return System.nanoTime() - start;
    }

    public static boolean runOneCompare(int threadNum) throws Exception {
        PlainHotVariable[] plainHotVariables = new PlainHotVariable[threadNum];
        for (int i = 0; i < threadNum; i++) {
            plainHotVariables[i] = new PlainHotVariable();
        }
        // 进行无填充  无缓存行对齐的测试
        long t1 = runOneTest(plainHotVariables);
        AlignHotVariable[] alignHotVariables = new AlignHotVariable[threadNum];
        for (int i = 0; i < NUM_THREADS; i++) {
            alignHotVariables[i] = new AlignHotVariable();
        }

        // 进行有填充  有缓存行对齐的测试
        long t2 = runOneTest(alignHotVariables);

        System.out.println("plain:" + t1);
        System.out.println("align:" + t2);
        return t1 > t2;
    }

    public static void runOneSuit(int threadNum, int testNum) throws Exception {
        int exceptCount = 0;
        for (int i = 0; i < testNum; i++) {
            if (runOneCompare(threadNum)) {
                exceptCount++;
            }
        }

        System.out.println("Radio (Plain<Align):" + exceptCount * 100D / testNum + "%");
    }

    public static void main(String[] args) throws Exception {
        runOneSuit(NUM_THREADS, NUM_TEST_TIMES);
    }
}
