package com.funny.study.java.nio;

import java.nio.ByteBuffer;

/**
 * https://www.cnblogs.com/ruber/p/6857159.html
 * @author funny
 */
public class BufferTest {

    public static void main(String[] args) {
        ByteBuffer direct = ByteBuffer.allocateDirect(4000);
        direct.putInt(5);
        System.out.println(direct.remaining());
        direct.putChar('5');
        System.out.println(direct.remaining());

        ByteBuffer heap= ByteBuffer.allocate(4000);
        System.out.println(heap.remaining());
    }

}
