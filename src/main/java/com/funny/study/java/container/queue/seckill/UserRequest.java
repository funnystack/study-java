package com.funny.study.java.container.queue.seckill;

public class UserRequest {

    private Integer userId;

    private Integer count = 1;

    public UserRequest(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
