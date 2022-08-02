package com.funny.study.java.string;

import java.lang.reflect.Field;

/**
 * 通过反射改变不可变的类
 */
public class ChangeDemo {
    public static void changeString(String str) {
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

    public static void changeNumber(Integer num) {
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
        changeString(a);
        // 输出cat，结论，通过反射不能修改string
        System.out.println("after:a=" + a);
        System.out.println(a.hashCode());
        Integer num = 12;
        System.out.println("before:num=" + num);
        changeNumber(num);
        System.out.println("after   :num=" + num);

        ChangeDemo changeDemo = new ChangeDemo();
        System.out.println(changeDemo.hashCode());
        System.out.println(System.identityHashCode(changeDemo));
    }
}

