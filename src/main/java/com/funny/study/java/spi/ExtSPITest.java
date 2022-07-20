package com.funny.study.java.spi;

import com.funny.study.java.spi.ext.OrderExtensionLoader;

public class ExtSPITest {

    public static void main(String[] args) {
        OrderService orderService =  OrderExtensionLoader.getExtensionLoader(OrderService.class).getExtension("cfw");

        orderService.execute();
    }
}
