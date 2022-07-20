package com.funny.study.java.jvm;

import java.util.Random;

/**
 * 分支预测：
 *
 *
 * 排序前 全是随机数据 - 很难去预测
 * 排序后，前128很容易去预测。
 *
 * 非判断语句写法：int t = (data[c] - 128) >> 31;
 *              sum += ~t & data[c];
 *
 */
public class BranchPrecondition {
    public static void main(String[] args) {
        // Generate data
        int arraySize = 32768;
        int data[] = new int[arraySize];

        Random rnd = new Random(0);
        for (int c = 0; c < arraySize; ++c)
            data[c] = rnd.nextInt() % 256;

        // !!! With this, the next loop runs faster
        //Arrays.sort(data);

        // Test
        long start = System.nanoTime();
        long sum = 0;

        for (int i = 0; i < 100000; ++i)
        {
            // Primary loop
            for (int c = 0; c < arraySize; ++c)
            {
                if (data[c] >= 128)
                    sum += data[c]; // 罪魁祸首,
            }
        }

        System.out.println((System.nanoTime() - start) / 1000000000.0);
        System.out.println("sum = " + sum);
    }

}
