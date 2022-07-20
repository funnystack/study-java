package com.funny.study.java.reflection.service;


import com.funny.study.java.reflection.bean.User;

/**
 * @author funnystack
 * @date 2021年03月28日 19:01
 */
public class UserService {
    public static User randomOne() {
        return User.random();
    }

    private static void privateStaticFunc() {
        System.out.println("Execute UserService#privateStaticFunc...");
    }

    public void printUser(final User user) {
        System.out.printf("User id: %s, username: %s, isFemale: %s", user.getId(), user.getUsername(), user.isFemale());
    }

    private void saveUser() {
        System.out.println("Execute UserService#saveUser...");
    }
}
