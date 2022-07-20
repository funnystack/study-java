package com.funny.study.java.reflection.generics;


import com.funny.study.java.reflection.bean.Student;

/**
 * @author funnystack
 * @date 2021年03月28日 19:59
 */
public class StudentDataServiceImpl implements IDataService<Student> {
    private volatile int count = 0;

    private final static String CLASS_NAME = "StudentDataServiceImpl";

    @Override
    public void print(Student bean) {
        System.out.println("Execute StudentDataServiceImpl#print");
    }
}
