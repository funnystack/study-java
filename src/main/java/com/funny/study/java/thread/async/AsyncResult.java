package com.funny.study.java.thread.async;

public class AsyncResult {
    private boolean ready;
    private String content;

    public AsyncResult(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
