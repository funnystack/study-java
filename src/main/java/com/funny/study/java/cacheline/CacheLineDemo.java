package com.funny.study.java.cacheline;

public class CacheLineDemo {

    private final static int   CACHE_LINE_LONG_NUM = 8;
    private final static int LINE_NUM = 1024 *1024;
    private final static int NUM_TEST_TIMES=10;
    // 构造能投填充‘Line_NUM个缓存行的数组
    private static final long[] values = new long[CACHE_LINE_LONG_NUM * LINE_NUM];
    // 进行顺序读取测试 期待在存起每个缓存行的第1个长整型时候，系统自动缓存整个缓存行，本行的后续读取都会命中缓存
    public static long runOneTestWithAlign(){
        final long start = System.nanoTime();
        for (int i = 0;i<CACHE_LINE_LONG_NUM*LINE_NUM;i++){
            values[i]=i;
        }
        return System.nanoTime() -start;
    }
    // 按照缓存行的步长进行跳跃读测试 期待每次读取一行中的一个元素，每次赌气都不会命中缓存
    public static long runOneTestWithOutAlign(){

        final long start = System.nanoTime();
        for(int i = 0 ;i <CACHE_LINE_LONG_NUM * LINE_NUM;i++){
            for(int j =0;j<LINE_NUM;j++){
                values[j*CACHE_LINE_LONG_NUM+i] = i*j;
            }
        }
        return System.nanoTime()-start;
    }
    public static boolean runOneCompare(){
        long t1= runOneTestWithAlign();
        long t2 = runOneTestWithOutAlign();

        System.out.println("sequential:"+t1);
        System.out.println("leap :"+t2);
        return t1<t2;
    }

    public static void runOneSuit(int tesNum) throws Exception{
        int exceptCount = 0;
        for(int i=0;i<tesNum;i++){
            if(runOneCompare()){
                exceptCount++;
            }
        }
        System.out.println("Radio (Sequential < Leap ):" + exceptCount*100D /tesNum +"%");
    }

    public static void main(String[] args) throws Exception {
        runOneSuit(NUM_TEST_TIMES                                                                                                                                                        );
    }
}
