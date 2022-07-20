package com.funny.study.java.jvm;

/**
 * 局部变量表中 slot复用 对垃圾回收的影响
 * 运行时参数-verbose:gc
 * <p>
 * https://www.jianshu.com/p/a474dd20b08d
 */
public class LocalTableSlot {

    /**
     * 不会回收
     *
     * @param args
     */
    public static void main(String[] args) {
        byte[] placeholder = new byte[64 * 1024 * 1024];
        int b = 0;
        System.gc();
    }

    /**
     * 不会回收
     * 加入了花括号之后，placeholder的作用域被限制在花括号之内，
     * 从代码逻辑上讲，在执行System.gc()的时候，placeholder已经不可能再被访问了，
     * 但执行一下这段程序，会发现运行结果如上，这64MB的内存还是没有被回收
     *
     * @param args
     */
    public static void test(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        System.gc();
    }

    /**
     * 会被回收
     * placeholder能否被回收取决于：局部变量表中的Slot是否还存在关于placeholder数组对象的引用。
     * 第一次修改中，代码虽然已经离开了placeholder的作用域，但在此之后，没有任何对局部变量表的读写操作，
     * placeholder原本所占用的Slot还没有被其他变量所复用，
     * 所以作为GC Roots一部分的局部变量表仍然保持着对它的关联
     *
     * @param args
     */
    public static void test1(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int b = 0;
        System.gc();
    }

}
