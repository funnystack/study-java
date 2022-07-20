package com.funny.study.java.dynamicproxy.service.impl;


import com.funny.study.java.dynamicproxy.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void save() {
        System.out.println("save");
    }

    @Override
    public void delete() {
        System.out.println("delete");
    }

    @Override
    public void update() {
        System.out.println("update");
    }

    @Override
    public void query() {
        System.out.println("query");
    }
}
