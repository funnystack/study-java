package com.funny.study.java.container.queue.seckill;

public class Result {

    private boolean success;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
