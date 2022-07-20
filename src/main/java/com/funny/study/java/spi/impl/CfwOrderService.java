package com.funny.study.java.spi.impl;

import com.funny.study.java.spi.OrderService;
import com.funny.study.java.spi.ext.SPI;

@SPI("cfw")
public class CfwOrderService implements OrderService {

    @Override
    public void execute() {
        System.out.println("cfw execute");
    }
}
