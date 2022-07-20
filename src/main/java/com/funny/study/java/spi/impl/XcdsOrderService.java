package com.funny.study.java.spi.impl;

import com.funny.study.java.spi.OrderService;
import com.funny.study.java.spi.ext.SPI;

@SPI("xcds")
public class XcdsOrderService implements OrderService {

    @Override
    public void execute() {
        System.out.println("xcds execute");
    }
}
