package com.funny.study.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 文件相关测试.
 *
 * @author skywalker
 */
public class FileChannelTest {
    public static void main(String[] args) throws FileNotFoundException {

        FileChannel fileChannel = new RandomAccessFile(new File("db.data"), "rw").getChannel();

    }

}
