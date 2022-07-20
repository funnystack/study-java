package com.funny.study.java.string;

import java.lang.reflect.Field;

/**
 * 通过反射改变不可变的类
 */
public class ChangeDemo {
    public static void change(String str) {
        if (null == str)
            return;
        try {
            Field f = String.class.getDeclaredField("value");
            f.setAccessible(true);
            char[] value_str = (char[]) f.get(str);
            value_str[0] = 'h';
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void change(Integer num) {
        if (null == num)
            return;
        try {
            Field f = Integer.class.getDeclaredField("value");
            f.setAccessible(true);
            int value = (int) f.get(num);
            f.set(num, value * 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String a = "cat";
        System.out.println("before:a=" + a);
        change(a);
        System.out.println("after   :a=" + a);
        Integer num = 12;
        System.out.println("before:num=" + num);
        change(num);
        System.out.println("after   :num=" + num);
    }
}

