package com.funny.study.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class MMAPTest {

    public static void main(String[] args) throws FileNotFoundException {
        FileChannel fileChannel = new RandomAccessFile(new File("db.data"), "rw").getChannel();


//        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, filechannel.size();

    }
}
