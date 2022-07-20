package com.funny.study.java.reflection.generics;


import com.funny.study.java.reflection.bean.User;

/**
 * @author funnystack
 * @date 2021年03月28日 19:54
 */
public class UserDataInfo extends DataInfo<User> {
    @Override
    User getBean() {
        return User.random();
    }
}
