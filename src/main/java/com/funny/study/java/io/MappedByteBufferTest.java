package com.funny.study.java.io;

import com.google.common.base.Stopwatch;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();

        // 调用普通io
        testIO();
        System.out.println("fis处理完成，耗时 [{" + stopwatch.stop() + "}]");


        // 调用nio
        stopwatch.start();// 再次计时
        testNIO();
        System.out.println("nio处理完成，耗时 [{" + stopwatch.stop() + "}]");

        // 调用MappedByteBuffer
        stopwatch.start();// 再次计时
        testMappedByteBuffer();
        System.out.println("MappedByteBuffer处理完成，耗时 [{" + stopwatch.stop() + "}]");




        // 调用 testFileChannel()
        stopwatch.start();// 再次计时
        testFileChannel();
        System.out.println("FileChannel处理完成，耗时 [{" + stopwatch.stop() + "}]");



        // 调用 testFileChannelByteBuffer()
        stopwatch.start();// 再次计时
        testFileChannelByteBuffer();
        System.out.println("FileChannelByteBuffer处理完成，耗时 [{" + stopwatch.stop() + "}]");
    }
    /**
     * 测试0.9G文件，IO的效率
     *
     * @throws IOException
     */
    private static void testNIO() throws IOException {
        OutputStream outputStream = new FileOutputStream(new File("/Users/fangli/Pictures/小视频_nio.zip"));
        Files.copy(Paths.get("/Users/fangli/Pictures/小视频.zip"),outputStream);
    }

    /**
     * 测试0.9G文件，IO的效率
     *
     * @throws IOException
     */
    private static void testIO() throws IOException {
        File sourceFile = new File("/Users/fangli/Pictures/小视频.zip");
        byte[] bytes = new byte[1024];  // 和下面方式创建byte[]效率基本一样
//        byte[] bytes = new byte[(int) sourceFile.length()];
        FileInputStream fis = new FileInputStream(sourceFile);
        FileOutputStream fos = new FileOutputStream("/Users/fangli/Pictures/小视频_fis.zip");
        int len = -1;
        while ((len = fis.read(bytes)) != -1) {
            fos.write(bytes, 0, len); // 写入数据
        }
        fis.close();
        fos.close();
    }

    /**
     * 测试0.9G文件，MappedByteBuffer的效率
     *
     * @throws IOException
     */
    private static void testMappedByteBuffer() throws IOException {
        File sourceFile = new File("/Users/fangli/Pictures/小视频.zip");
//        byte[] bytes = new byte[1024];  // 和下面方式创建byte[]效率基本一样
        byte[] bytes = new byte[(int) sourceFile.length()];
        RandomAccessFile ra_read = new RandomAccessFile(sourceFile, "r");
        FileChannel fc = new RandomAccessFile("/Users/fangli/Pictures/小视频_map.zip", "rw").getChannel();
        MappedByteBuffer map = fc.map(FileChannel.MapMode.READ_WRITE, 0, sourceFile.length());
        int len = -1;
        while ((len = ra_read.read(bytes)) != -1) {
            map.put(bytes, 0, len); // 写入数据
        }
        ra_read.close();
        fc.close();
    }

    /**
     * 测试0.9G文件，FileChannel的效率
     *
     * @throws IOException
     */
    private static void testFileChannel() throws IOException {
        File sourceFile = new File("/Users/fangli/Pictures/小视频.zip");
        FileInputStream fis = new FileInputStream(sourceFile);
        FileChannel fisChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("/Users/fangli/Pictures/小视频_fc.zip");
        FileChannel fosChannel = fos.getChannel();
        fisChannel.transferTo(0, fisChannel.size(), fosChannel);
        fis.close();
        fos.close();
    }

    /**
     * 测试0.9G文件，FileChannel的效率
     *
     * @throws IOException
     */
    private static void testFileChannelByteBuffer() throws IOException {
        try (FileChannel from = new FileInputStream("/Users/fangli/Pictures/小视频.zip").getChannel();
             FileChannel to = new FileOutputStream("/Users/fangli/Pictures/小视频_fcbyte.zip").getChannel();
        ) {
            ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024);
            while (true) {
                int len = from.read(bb);
                if (len == -1) {
                    break;
                }
                bb.flip();  // 调用flip之后，读写指针指到缓存头部
                to.write(bb);
                bb.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
