package com.funny.study.java.container;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ForTest {

    public static void main(String[] args) {
        final long count = 100_00000;
        List<Long> list = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            list.add(i);
        }
        //=========传统方式进行外部迭代=========
        Instant begin = Instant.now();
        for (Long i : list) {
            System.out.print("");
        }
        System.out.println("--------------------------");
        Instant end = Instant.now();
        System.out.println("传统方式进行外部迭代" + count + "次,耗时(ms)：" + Duration.between(begin, end).toMillis());
        //=========java8内部迭代，用lambda处理=========
        begin = Instant.now();
        list.stream().forEach(i -> System.out.print(""));
        System.out.println("--------------------------");
        end = Instant.now();
        System.out.println("内部迭代forEach" + count + "次,耗时(ms)：" + Duration.between(begin, end).toMillis());
        //=========java8进行并行流处理后迭代（备注：并行流输出是没有顺序的 比如不再是1234顺序了）=========
        begin = Instant.now();
        list.stream().parallel().forEach(i -> System.out.print(""));
//        list.parallelStream().forEach(i -> System.out.print(""));

        System.out.println("--------------------------");
        end = Instant.now();
        System.out.println("内部迭代parallelStream" + count + "次,耗时(ms)：" + Duration.between(begin, end).toMillis());
    }

}
