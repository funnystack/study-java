package com.funny.study.java.exception;

public class TestException {


    public static void main(String[] args) {
        aa1();

    }


    private static void aa1() {

        try {
            bb();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("cc");
        }
    }

    private static void bb() throws Exception {
//        System.out.println("bb");
        throw new RuntimeException("ee");
    }
}
