package com.funny.study.java.benchmark;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * IP工具类
 *
 * @author fangli
 */
@State(Scope.Benchmark) // 每个测试线程一个实例
@BenchmarkMode(Mode.AverageTime)// 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.MILLISECONDS)//
public class BenchMarkUtils {
    public static void main(String[] args) throws RunnerException {
        // 可以通过注解
//        Options opt = new OptionsBuilder()
//                .include(BenchMarkUtils.class.getSimpleName())
//                .warmupIterations(3) // 预热3次
//                .measurementIterations(5).measurementTime(TimeValue.valueOf("5s")) // 运行5次，每次10秒
//                .threads(10) // 10线程并发
//                .forks(2)
//                .build();
//
//        new Runner(opt).run();
        //8491  //1628
        //12175  6551
        //3085  6551
        //
        long start = System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
            getUUTraceId();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    @Benchmark
    public static String getRandomStringUtil(){
       return RandomStringUtils.random(12);
    }

    @Benchmark
    public static String getRandomString() {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 12; i++) {
            int number = random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

//    @Benchmark
    public static String getNewTraceId() {
        return LocalHostUtils.getIpFromString(LocalHostUtils.getLocalIp()) + System.currentTimeMillis() + getRandomString();
    }
//    @Benchmark
    public static String getUUTraceId() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }
}