package com.funny.study.java.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class JavaSPITest {

    public static void main(String[] args) {
        ServiceLoader<OrderService> load = ServiceLoader.load(OrderService.class);
        Iterator<OrderService> iterator = load.iterator();
        while(iterator.hasNext()) {
            OrderService ser = iterator.next();
            ser.execute();
        }
    }
}
